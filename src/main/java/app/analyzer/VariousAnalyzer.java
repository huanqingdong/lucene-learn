package app.analyzer;

import app.uitl.AnalyzerUtil;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.util.Arrays;

/**
 * Lucene字典的各种分词器测试
 *
 * @author faith.huan 2019-08-18 21:39
 */
public class VariousAnalyzer {
    private static final String STR_CN = "中华人民共和国简称中国，是一个有13亿人口的国家";
    private static final String STR_EN = "Dogs can123 not notx achieve a place, eyes can reach; ";


    public static void main(String[] args) {

        /*
         * 标准分词器
         * 根据空格和符号来完成分词,
         * 可以支持过滤词表,用来代替StopAnalyzer实现过滤功能
         */
        AnalyzerUtil.analyzeStr(new StandardAnalyzer(), "标准分词", STR_CN);

        /*
         * 空格分词器
         * 使用空格分隔词汇,
         * 分词器不做词汇过滤,也不进行大小写转换
         */
        AnalyzerUtil.analyzeStr(new WhitespaceAnalyzer(), "空格分词", STR_CN);
        /*
         * 简单分词器
         * 具备基本西文字符词汇分析的分词器,以非字母字符作为分隔符号.
         * 分词器不做词汇过滤,进行小写转换.
         */
        AnalyzerUtil.analyzeStr(new SimpleAnalyzer(), "简单分词", STR_CN);

        /*
         * 二分法分词
         * 内部调用CJKTokenizer分词器,对中文进行分词,同时使用StopFilter过滤器完成过滤功能
         * 可以实现中文的多元切分和停用词过滤
         */
        AnalyzerUtil.analyzeStr(new CJKAnalyzer(), "二分法分词", STR_CN);
        /*
         * 关键词分词器
         * 把整个输入作为一个单独词汇单元
         */
        AnalyzerUtil.analyzeStr(new KeywordAnalyzer(), "关键字分词", STR_CN);
        /*
         * 停用词分词器
         * 能够过滤词汇中特定字符串和词汇,并完成大小写换换
         * note: 在分词后进行过滤, 例如你对 "中"进行过滤, 分词后不存在"中"这个term,则不会对分词结果有影响
         */
        AnalyzerUtil.analyzeStr(new StopAnalyzer(new CharArraySet(Arrays.asList("中", "国"), true)), "停用词分词", STR_CN);
        AnalyzerUtil.analyzeStr(new StopAnalyzer(new CharArraySet(Arrays.asList("not", "as"), true)), "停用词分词", STR_EN);

        /*
         * Lucene提供的中文分词器
         */
        AnalyzerUtil.analyzeStr(new SmartChineseAnalyzer(), "中文词分词", STR_CN);
        // AnalyzerUtil.analyzeStr(new IKAnalyzer(), "中文词分词", STR_CN);

    }

}
