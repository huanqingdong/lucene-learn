package app.index;

import app.ik.IKAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Lucene 文档删除操作
 *
 * @author faith.huan 2019-08-25 16:54
 */
@Slf4j
public class DeleteIndex {

    private static void deleteDoc(String field, String key) {
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path indexPath = Paths.get("indexDir");
        try {
            FSDirectory directory = FSDirectory.open(indexPath);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            long seq = indexWriter.deleteDocuments(new Term(field, key));
            long commitSeq = indexWriter.commit();
            log.info("seq:{},commitSeq:{}", seq, commitSeq);
            indexWriter.close();
            directory.close();
        } catch (IOException e) {
            log.error("删除文档发生异常,field:{},key:{}", field, key, e);
        }
    }

    public static void main(String[] args) {
        //删除title中包含美国关键字的文档
        deleteDoc("title", "美国");
    }

}
