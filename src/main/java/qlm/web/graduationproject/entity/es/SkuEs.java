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
        //一级分类+二级分类+三级分类+品牌名+描述+食用方法+商品名+副标题
        this.context = sku.getSpu().getFirstCategory().getName()+"-"+
                sku.getSpu().getSecondCategory().getName()+"-"+
                sku.getSpu().getThirdCategory().getName()+"-"+
                sku.getSpu().getName()+"-"+
                sku.getSpu().getDesDetail()+"-"+
                sku.getSpu().getDesUse()+"-"+
                sku.getName()+"-"+
                sku.getCaption();
        this.price = sku.getPrice();
}

    public SkuEs() {
    }

    public SkuEs(Long id, String context,Double price) {
        this.id = id;
        this.context = context;
        this.price = price;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //result=prime * result+((id==null)?0:id.hashCode());  
        //使哈希值与total属性值关联  
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
//重写equals方法  
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SkuEs other = (SkuEs) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }
}
