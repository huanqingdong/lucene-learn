package app.analyzer;

import app.uitl.AnalyzerUtil;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.io.IOException;

/**
 * 标准分词器测试
 *
 * @author faith.huan 2019-08-18 21:21
 */
public class StdAnalyzer {

    private static final String STR_CN = "中华人民共和国简称中国，是一个有13亿人口的国家";

    private static final String STR_EN = "Dogs can1 not achieve a place, eyes can reach; ";


    public static void main(String[] args) throws IOException {

        AnalyzerUtil.analyzeStr(new StandardAnalyzer(), "StandardAnalyzer对中文分词", STR_CN);
        AnalyzerUtil.analyzeStr(new StandardAnalyzer(), "StandardAnalyzer对英文分词", STR_EN);

    }

}
