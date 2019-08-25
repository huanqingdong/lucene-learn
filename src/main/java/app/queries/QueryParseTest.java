package app.queries;

import app.ik.IKAnalyzer;
import app.uitl.LuceneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.io.IOException;

/**
 * @author faith.huan 2019-08-25 21:19
 */
@Slf4j
public class QueryParseTest {

    private static Analyzer ikSmart = new IKAnalyzer(true);

    public static void main(String[] args) throws ParseException, IOException {

        LuceneUtil.query(query());

        LuceneUtil.query(multiFieldQuery());

        LuceneUtil.query(termQuery());

        LuceneUtil.query(booleanQuery());

        LuceneUtil.query(rangeQuery());

        LuceneUtil.query(prefixQuery());

        LuceneUtil.query(phraseQuery());

        LuceneUtil.query(fuzzyQuery());

        LuceneUtil.query(wildcardQuery());
    }

    /**
     * 搜索入门
     */
    private static Query query() throws ParseException {
        QueryParser queryParser = new QueryParser("title", ikSmart);
        // 默认Operator为OR
        queryParser.setDefaultOperator(QueryParser.Operator.AND);
        return queryParser.parse("农村学生");
    }

    /**
     * 多域搜索
     */
    private static Query multiFieldQuery() throws ParseException {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"title", "content"}, ikSmart);
        return parser.parse("美国");
    }

    /**
     * 词项搜索
     */
    private static Query termQuery() {
        Term term = new Term("title", "美国");
        return new TermQuery(term);
    }

    /**
     * 布尔搜索
     */
    private static Query booleanQuery() {
        // 标题中包含美国,内容中不包括特朗普.
        // 注意: 需要在ext.dic中添加特朗普分词,添加分词后重新执行CreateIndex.main
        TermQuery query1 = new TermQuery(new Term("title", "美国"));
        TermQuery query2 = new TermQuery(new Term("content", "特朗普"));
        BooleanClause clause1 = new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause clause2 = new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
        return new BooleanQuery.Builder().add(clause1).add(clause2).build();
    }

    /**
     * 范围搜索
     */
    private static Query rangeQuery() {
        return IntPoint.newRangeQuery("reply", 500, 1000);
    }

    /**
     * 前缀搜索
     */
    private static Query prefixQuery() {
        Term term = new Term("title", "习");
        return new PrefixQuery(term);
    }

    /**
     * 多关键字搜索
     */
    private static Query phraseQuery() {
        // position之间差的是字数
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        builder.add(new Term("title", "习近平"), 0);
        builder.add(new Term("title", "美国总统"), 3);
        return builder.build();
    }


    /**
     * 模糊搜索
     * <p>
     * fuzzy:发贼
     */
    private static Query fuzzyQuery() {
        Term term = new Term("title", "Tramp");
        return new FuzzyQuery(term);
    }

    /**
     * 通配符搜索
     */
    private static Query wildcardQuery() {
        // 注意: 需要在ext.dic中添加习近平分词,添加分词后重新执行CreateIndex.main
        Term term = new Term("title", "习??");
        return new WildcardQuery(term);
    }

}
