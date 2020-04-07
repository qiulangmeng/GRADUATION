package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 13:42 2020.3.24
 */
@Table(name = "spu_category")
@Entity
@Getter
@Setter
public class SpuCategory implements Serializable {
    /**
     * 物理结构
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category1_id")
    private Set<Spu> firstSups;
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category2_id")
    private Set<Spu> secondSups;
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category3_id")
    private Set<Spu> thirdSups;
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
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
    private Integer id;
    @NotBlank(message = "类别名称不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '目录名'")
    private String name;

    private String parentCategory;


}
