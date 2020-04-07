package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import qlm.web.graduationproject.entity.car.Car;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 13:23 2020.3.24
 */
@Table(name = "spu")
@Entity
@Getter
@Setter
public class Spu implements Serializable {
    /**
     * 物理结构
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category1_id")
    private SpuCategory firstCategory;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category2_id")
    private SpuCategory secondCategory;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category3_id")
    private SpuCategory thirdCategory;
    /**
     * 品牌关系
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "brand_id")
    private SpuBrand brand;
    /**
     * 规格关系
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spu_id")
    private Set<SpuSpecification> spuSpecifications;
    /**
     * sku关系
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spu_id")
    private Set<Sku> skus;
    /**
     * 购物车关系
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sku_id")
    private Set<Car> cars;



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
    @Column(name = "spu_name",nullable = false,columnDefinition = "varchar(255) comment 'Standard Product Unit 标准化产品单元'")
    private String name;

    @ColumnDefault("0")
    @Column(name = "sales")
    private String sales;

    @ColumnDefault("0")
    @Column(name = "conmments")
    private String comments;

    @NotBlank(message = "商品详细介绍不能为空")
    @Column(name = "spu_des_detail",nullable = false)
    private String desDetail;

    @NotBlank(message = "商品包装介绍不能为空")
    @Column(name = "spu_des_pack",nullable = false)
    private String desPack;

    @NotBlank(message = "售后介绍不能为空")
    @Column(name = "spu_des_service",nullable = false)
    private String desService;




}
