package app.index;

import app.ik.IKAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Lucene文档索引操作
 *
 * @author faith.huan 2019-08-25 13:48
 */
@Slf4j
public class CreateIndex extends BaseIndex{

    /**
     * 根据news获取document对象
     */
    private static Document getDocument(News news) {
        Document doc1 = new Document();
        doc1.add(new Field("id", String.valueOf(news.getId()), idType));
        doc1.add(new Field("title", news.getTitle(), titleType));
        doc1.add(new Field("content", news.getContent(), contentType));
        doc1.add(new IntPoint("reply", news.getReply()));
        doc1.add(new StoredField("reply_display", news.getReply()));
        return doc1;
    }

    public static void main(String[] args) {
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("习近平会见美国总统奥巴马,学习国外经验");
        news1.setContent("国家主席习近平9月3日在杭州西湖国宾馆会见前来出席二十国集团领导人" +
                "杭州峰会的美国总统奥巴马...");
        news1.setReply(672);

        News news2 = new News();
        news2.setId(2);
        news2.setTitle("北大迎4380名新生 农村学生700多人近年最多");
        news2.setContent("昨天,北京大学迎来4370名来自全国各地及数十个国家的本科新生.其中," +
                "农村学生工700余名,为近年最多...");
        news2.setReply(995);

        News news3 = new News();
        news3.setId(3);
        news3.setTitle("特朗普宣誓(Donald Trump)就任美国第45任总统");
        news3.setContent("当地时间1月20日,唐纳德,特朗普在美国国会宣誓就职,正式成为美国第" +
                "45任总统");
        news3.setReply(1872);

        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory directory = null;
        IndexWriter indexWriter = null;
        // 索引目录
        Path indexPath = Paths.get("indexDir");
        // 开始时间
        Date start = new Date();
        try {
            if (!Files.isReadable(indexPath)) {
                System.out.println("Document directory '" + indexPath + "' not exist or not readable ,please check the path ");
                System.exit(1);
            }

            directory = FSDirectory.open(indexPath);
            indexWriter = new IndexWriter(directory, indexWriterConfig);

            Document doc1 = getDocument(news1);
            Document doc2 = getDocument(news2);
            Document doc3 = getDocument(news3);

            long seq1 = indexWriter.addDocument(doc1);
            long seq2 = indexWriter.addDocument(doc2);
            long seq3 = indexWriter.addDocument(doc3);
            log.info("seq1:{},seq2:{},seq3:{}", seq1, seq2, seq3);
            long commitSeq = indexWriter.commit();
            log.info("commitSeq:{}", commitSeq);
            indexWriter.close();
            directory.close();
        } catch (Exception e) {
            log.error("发生异常:{}", e.getMessage(), e);
        }

        Date end = new Date();
        log.info("索引文档用时{}毫秒", end.getTime() - start.getTime());

    }
}
