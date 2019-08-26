package app.queries;

import app.ik.IKAnalyzer;
import app.uitl.LuceneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;

import java.io.IOException;

/**
 * 高亮测试
 *
 * @author faith.huan 2019-08-26 22:04
 */
@Slf4j
public class HighlighterTest {
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
        String field = "title";
        // 注意:使用ikSmart时,"北大迎4380名新生"无法分词出北大,因为存在大迎(org/wltea/analyzer/dic/main2012.dic)这个词,然后IK是从右面分析的
        Analyzer ikMaxWord = new IKAnalyzer();
        IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
        QueryParser queryParser = new QueryParser(field, ikMaxWord);
        Query query = queryParser.parse("北大");

        QueryScorer scorer = new QueryScorer(query, field);
        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"color:red;\" >", "</span>");
        Highlighter highlighter = new Highlighter(htmlFormatter, scorer);

        TopDocs topDocs = indexSearcher.search(query, 10);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            log.info("DocId:{}", scoreDoc.doc);
            log.info("id:{}", doc.get("id"));
            log.info("title:{}", doc.get("title"));

            // 当title字段未设置保存TermVectors时使用
            TokenStream tokenStream1 = TokenSources.getTokenStream(field,null, doc.get(field), ikMaxWord,32);
           SimpleSpanFragmenter simpleSpanFragmenter = new SimpleSpanFragmenter(scorer);
            highlighter.setTextFragmenter(simpleSpanFragmenter);
            log.info("{}", doc.get(field));
            String bestFragment = highlighter.getBestFragment(tokenStream1, doc.get(field));
            log.info("高亮片段:{}", bestFragment);

            /*
             * 当title字段设置了setStoreTermVectors:true,setStoreTermVectorPositions:true,setStoreTermVectorOffsets:true时
             */
            TokenStream tokenStream = TokenSources.getTermVectorTokenStreamOrNull(field, indexSearcher.getIndexReader().getTermVectors(scoreDoc.doc), 0);
            bestFragment = highlighter.getBestFragment(tokenStream, doc.get(field));
            log.info("高亮片段:{}", bestFragment);
        }

    }
}
