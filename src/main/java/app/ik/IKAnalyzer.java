package app.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * @author faith.huan 2019-08-18 22:57
 */
public class IKAnalyzer extends Analyzer {

    private boolean useSmart;

    public IKAnalyzer() {
        this(false);
    }

    public IKAnalyzer(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new IKTokenizer(this.useSmart);

        return new TokenStreamComponents(tokenizer);
    }
}
