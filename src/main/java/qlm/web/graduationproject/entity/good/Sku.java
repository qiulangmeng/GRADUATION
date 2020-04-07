package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import qlm.web.graduationproject.entity.order.Orders;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
public class Sku implements Serializable {
    /**
     * 物理关系 category
     */
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private SpuCategory spuCategory;
    /**
     * 规格信息关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<SkuSpecifications> skuSpecifications;
    /**
     * 图片关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<SkuImage> skuImages;
    /**
     * 品牌关系
     */
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "spu_id")
    private Spu spu;
    /**
     * 订单关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<Orders> orders;
    /**
     * 促销关系
     */
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "promotion_id")
    private skuPromotion skuPromotion;

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






}
