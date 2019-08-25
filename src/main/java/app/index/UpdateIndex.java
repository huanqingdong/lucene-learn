package app.index;

import app.uitl.AnalyzerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Lucene文档更新操作
 *
 * @author faith.huan 2019-08-25 17:08
 */
@Slf4j
public class UpdateIndex extends BaseIndex {

    public static void main(String[] args) {

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(AnalyzerUtil.getIkAnalyzer());

        try (Directory directory = FSDirectory.open(Paths.get("indexDir"))) {
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            Document doc = new Document();
            doc.add(new Field("id", "2", idType));
            doc.add(new Field("title", "北大迎4380名新生", titleType));
            doc.add(new Field("content", "昨天,北京大学迎来4370名来自全国各地及数十个国家的本科新生.其中," +
                    "农村学生工700余名,为近年最多...", contentType));
            long seq = indexWriter.updateDocument(new Term("id", "2"), doc);
            long commitSeq = indexWriter.commit();
            log.info("seq:{},commitSeq:{}", seq, commitSeq);
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
