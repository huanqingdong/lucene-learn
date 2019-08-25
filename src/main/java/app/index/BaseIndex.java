package app.index;

import lombok.Data;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * 字段类型设置,CreateIndex,UpdateIndex使用
 *
 * @author faith.huan 2019-08-25 17:34
 */
@Data
public class BaseIndex {

    static FieldType idType;

    static FieldType titleType;

    static FieldType contentType;

    /*
     * 初始化id,title,content字段类型
     */
    static {
        // 设置新闻ID索引并存储
        idType = new FieldType();
        idType.setIndexOptions(IndexOptions.DOCS);
        idType.setStored(true);
        // 设置新闻标题索引文档,此项频率,位移信息,偏移量,存储并词条华
        titleType = new FieldType();
        titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        titleType.setStored(true);
        titleType.setTokenized(true);
        // 内容字段
        contentType = new FieldType();
        contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        contentType.setStored(true);
        contentType.setTokenized(true);
        contentType.setStoreTermVectors(true);
        contentType.setStoreTermVectorPositions(true);
        contentType.setStoreTermVectorOffsets(true);
        contentType.setStoreTermVectorPayloads(true);
    }

}
