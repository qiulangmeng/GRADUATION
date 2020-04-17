package qlm.web.graduationproject.entity.good;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Spu implements Serializable {
    /**
     * 物理结构
     */
    @JsonIgnoreProperties("firstSups")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category1_id")
    private SpuCategory firstCategory;
    @JsonIgnoreProperties("secondSups")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category2_id")
    private SpuCategory secondCategory;
    @JsonIgnoreProperties("thirdSups")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category3_id")
    private SpuCategory thirdCategory;
    /**
     * 品牌关系
     */
    @JsonIgnoreProperties("spus")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "brand_id")
    private SpuBrand brand;
    /**
     * 规格关系
     */
    @JsonIgnoreProperties("spu")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spu_id")
    private Set<SpuSpecification> spuSpecifications;
    /**
     * sku关系
     */
    @JsonIgnoreProperties("spu")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spu_id")
    private Set<Sku> skus;
    /**
     * 购物车关系
     */
    @JsonIgnoreProperties("spu")
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
    @Column(name = "spu_des_use",nullable = false)
    private String desUse;

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
        Spu other = (Spu) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }


}
