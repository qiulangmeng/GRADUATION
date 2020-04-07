package qlm.web.graduationproject.entity.es;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import qlm.web.graduationproject.entity.good.Sku;

import javax.persistence.Id;

/**
 * @author qlm
 * @version 1.0 16:21 2020.3.26
 */
@Getter
@Setter
@Document(indexName = "graduation",type = "sku_es")
public class SkuEs {
    /**
     * 两个字段
     * id Sku的id
     * search 用于检索
     */
    @Id
    private Long id;
    /**
     * 副标题
     */
    @Field(type = FieldType.Text)
    private String context;
    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    private Double price;

    public SkuEs(Sku sku) {
        this.id = sku.getId();
        this.context = sku.getSpuCategory().getName()+"-"+sku.getSpu().getName()+"-"+sku.getName()+"-"+sku.getCaption();
        this.price = sku.getPrice();
}

    public SkuEs() {
    }

    public SkuEs(Long id, String context,Double price) {
        this.id = id;
        this.context = context;
        this.price = price;
    }
}
