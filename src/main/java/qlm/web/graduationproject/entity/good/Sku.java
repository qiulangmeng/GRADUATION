package qlm.web.graduationproject.entity.good;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import qlm.web.graduationproject.entity.car.Car;
import qlm.web.graduationproject.entity.order.OrderItem;
import qlm.web.graduationproject.entity.order.Orders;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 13:22 2020.3.24
 */
@Table(name = "sku")
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Sku implements Serializable {
    /**
     * 购物车关系
     */
    @JsonIgnoreProperties("sku")
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<Car> cars;
    /**
     * 规格信息关系
     */
    @JsonIgnoreProperties("sku")
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<SkuSpecifications> skuSpecifications;
    /**
     * 图片关系
     */
    @JsonIgnoreProperties("sku")
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<SkuImage> skuImages;
    /**
     * 品牌关系
     */
    @JsonIgnoreProperties("skus")
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "spu_id")
    private Spu spu;
    /**
     * 订单关系
     */
    @JsonIgnoreProperties("sku")
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<Orders> orders;
    /**
     * 促销关系
     */
    @JsonIgnoreProperties("skus")
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "promotion_id")
    private SkuPromotion skuPromotion;

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "商品名称不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment 'stock keeping unit 库存量单位'")
    private String name;

    @NotBlank(message = "商品副标题不能为空")
    @Column(nullable = false)
    private String caption;

    @NotNull(message = "商品价格不能为空")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "进价不能为空")
    @Column(nullable = false)
    private Double costPrice;

    private Integer marketPrice;

    @NotNull(message = "库存不能为空")
    @Column(nullable = false)
    private Double stock;

    @NotNull(message = "销量不能为空")
    @Column(nullable = false)
    private Long sales;

    @NotNull(message = "评论量不能为空")
    @Column(nullable = false)
    private Long comments;

    @NotNull(message = "是否上架不能为空")
    @Column(nullable = false,columnDefinition = "tinyint comment '是否上架'")
    private Boolean isLaunched;

    @NotBlank(message = "默认图片不能为空不能为空")
    @Column(nullable = false)
    private String defaultImageUrl;

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
        Sku other = (Sku) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }




}
