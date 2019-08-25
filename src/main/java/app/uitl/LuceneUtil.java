package app.uitl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lucene工具类
 *
 * @author faith.huan 2019-08-25 21:48
 */
@Slf4j
public class LuceneUtil {


    private static volatile IndexSearcher indexSearcher;

    private static Map<Integer, String> cacheMap = new ConcurrentHashMap<>();

    /**
     * 获取IndexSearcher对象
     *
     * @return
     * @throws IOException
     */
    public static IndexSearcher getIndexSearcher() throws IOException {

        if (indexSearcher == null) {
            synchronized (LuceneUtil.class) {
                if (indexSearcher == null) {
                    Path indexPath = Paths.get("indexDir");
                    IndexReader indexReader = DirectoryReader.open(FSDirectory.open(indexPath));
                    indexSearcher = new IndexSearcher(indexReader);
                }
            }
        }
        return indexSearcher;
    }

    public static void query(Query query) throws IOException {

        log.info("{}{}{}", getHyphen(40), query.getClass().getSimpleName(), getHyphen(40));
        log.info("query:{}", query);
        TopDocs topDocs = getIndexSearcher().search(query, 10);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = getIndexSearcher().doc(scoreDoc.doc);
            log.info("DocId:{}", scoreDoc.doc);
            log.info("id:{}", doc.get("id"));
            log.info("reply:{}", doc.get("reply_display"));
            log.info("title:{}", doc.get("title"));
            log.info("content:{}", doc.get("content"));
            log.info("文档评分:{}", scoreDoc.score);
        }


    }

    /**
     * 获得连字符
     */
    private static String getHyphen(Integer count) {
        if (!cacheMap.containsKey(count)) {
            cacheMap.put(count, StringUtils.repeat("-", count));
        }
        return cacheMap.get(count);
    }

}
