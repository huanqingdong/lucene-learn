package app.analyzer;

import app.ik.IKAnalyzer;
import app.uitl.AnalyzerUtil;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;

/**
 * 对比IK分词器与SmartChinese分词器
 *
 * @author faith.huan 2019-08-18 23:04
 */
public class IKVsSmartCn {

    private static final String STR1 = "公路局正在治理解放大道路面积水问题";

    private static final String STR2 = "IKAnalyzer是一个开源的,基于java语言开发的轻量级的中文分词工具包.";

    public static void main(String[] args) {

        System.out.println("句子一" + STR1);
        AnalyzerUtil.analyzeStr(new IKAnalyzer(true), "IKAnalyzer分词器", STR1);
        AnalyzerUtil.analyzeStr(new SmartChineseAnalyzer(), "SmartChineseAnalyzer分词器", STR1);

        System.out.println("句子二" + STR2);
        AnalyzerUtil.analyzeStr(new IKAnalyzer(true), "IKAnalyzer分词器", STR2);
        AnalyzerUtil.analyzeStr(new SmartChineseAnalyzer(), "SmartChineseAnalyzer分词器", STR2);

    }


}
