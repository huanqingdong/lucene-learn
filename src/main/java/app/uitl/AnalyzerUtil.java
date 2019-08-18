package app.uitl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 工具类,输出分词结果
 *
 * @author faith.huan 2019-08-18 23:06
 */
public class AnalyzerUtil {


    public static void analyzeStr(Analyzer analyzer, String analyzerType, String content) {
        try {

            TokenStream tokenStream = analyzer.tokenStream("fieldOne", content);
            tokenStream.reset();

            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);

            System.out.println(analyzerType + ":\n" + analyzer.getClass());

            while (tokenStream.incrementToken()) {
                System.out.print(charTermAttribute.toString() + "|");
            }
            // 两个换行
            System.out.println("\n");

        } catch (Exception e) {
            System.out.println("分词异常" + e.getMessage());
        }
    }

}
