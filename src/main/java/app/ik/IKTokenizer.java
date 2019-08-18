package app.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * @author faith.huan 2019-08-18 22:37
 */
public class IKTokenizer extends Tokenizer {

    /**
     * IK分词器实现
     */
    private IKSegmenter ikSegmenter;

    /**
     * 词元文本属性
     */
    private final CharTermAttribute charTermAttribute;


    /**
     * 词元位移属性
     */
    private final OffsetAttribute offsetAttribute;


    /**
     * 词元分类属性
     * (该属性分类参考org.wltea.analyzer.core.Lexeme 中的分类常量)
     */
    private final TypeAttribute typeAttribute;

    /**
     * 记录最后一个词元结束的位置
     */
    private int endPosition;


    public IKTokenizer(boolean useSmart) {
        super();
        this.ikSegmenter = new IKSegmenter(input, useSmart);
        this.charTermAttribute = addAttribute(CharTermAttribute.class);
        this.offsetAttribute = addAttribute(OffsetAttribute.class);
        this.typeAttribute = addAttribute(TypeAttribute.class);
    }

    @Override
    public boolean incrementToken() throws IOException {
        // 清除所有的词元属性
        clearAttributes();
        // 词位，词素(最小的意义单位);
        Lexeme nextLexeme = ikSegmenter.next();
        if (nextLexeme != null) {
            // 将Lexeme转成Attributes
            // 设置词元文本,长度
            charTermAttribute.append(nextLexeme.getLexemeText());
            charTermAttribute.setLength(nextLexeme.getLength());
            // 设置词元位移
            offsetAttribute.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            // 记录分词的最后位置
            endPosition = nextLexeme.getEndPosition();
            // 设置词元分类
            typeAttribute.setType(nextLexeme.getLexemeTypeString());
            // 返回true,告知还有下个词元
            return true;
        }
        // 返回false,告知词元输出完毕
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        ikSegmenter.reset(input);
    }

    @Override
    public void end() throws IOException {
        //super.end();
        int finalOffset = correctOffset(this.endPosition);
        offsetAttribute.setOffset(finalOffset, finalOffset);
    }
}
