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

    private static final String[] STR_ARRAY = {"公路局正在治理解放大道路面积水问题",
            "IKAnalyzer是一个开源的,基于java语言开发的轻量级的中文分词工具包.",
            "农村学生",
            "当地时间1月20日,唐纳德,特朗普在美国国会宣誓就职,正式成为美国第45任总统",
            "习近平会见美国总统奥巴马,学习国外经验",
            "北大迎4380名新生 农村学生700多人近年最多"};

    public static void main(String[] args) {

        for (int i = 0; i < STR_ARRAY.length; i++) {
            String str = STR_ARRAY[i];
            System.out.println("句子" + (i + 1) + ":" + str);

            AnalyzerUtil.analyzeStr(new IKAnalyzer(true), "IK-SMART分词器", str);
            AnalyzerUtil.analyzeStr(new IKAnalyzer(false), "IK-MAX-WORD分词器", str);
            AnalyzerUtil.analyzeStr(new SmartChineseAnalyzer(), "SmartChineseAnalyzer分词器", str);
            System.out.println("---------------------------------------------------------------------\n");
        }


    }


}
