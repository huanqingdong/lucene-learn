package app.analyzer;

import app.ik.IKAnalyzer;
import app.uitl.AnalyzerUtil;

/**
 * IK分词器自定义字典测试
 *
 * @author faith.huan 2019-08-18 23:17
 */
public class IKExtDicTest {
    private static final String STR_CN = "厉害了我的哥!中国环保部门即将发布治理北京雾霾的方法!";

    public static void main(String[] args) {

        /*
         * 不加词典
         *
         * 分词结果: 厉|害了|我|的哥|中国|环保部门|即将|发布|治理|北京|雾|霾|的|方法|
         *
         *
         * ext.dic中加入
         * 中国环保部门
         * 北京雾霾
         * 厉害了我的哥
         *
         * 分词结果: 厉害了我的哥|中国环保部门|即将|发布|治理|北京雾霾|的|方法|
         */
        AnalyzerUtil.analyzeStr(new IKAnalyzer(true), "IK分词", STR_CN);


    }

}
