package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 用于存储商品促销信息
 * @author qlm
 * @version 1.0 12:52 2020.3.31
 */
@Table(name = "sku_promotion")
@Entity
@Getter
@Setter
public class skuPromotion implements Serializable {
    /**
     * 物理关系
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "promotion_id")
    private Set<Sku> skus;

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
    @NotBlank(message = "促销名不能为空")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "促销类不能为空")
    @Column(nullable = false)
    private String promotionClass;
    @NotBlank(message = "促销图片不能为空")
    @Column(nullable = false)
    private String imageUrl;




}
