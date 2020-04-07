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
 * @version 1.0 13:35 2020.3.24
 */
@Table(name = "spu_specifications")
@Entity
@Getter
@Setter
public class SpuSpecification implements Serializable {
    /**
     * 物理结构 spu关系
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "spu_id")
    private Spu spu;
    /**
     * sku规格信息关系
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spec_id")
    private Set<SkuSpecifications> skuSpecifications;
    /**
     * 规格选项关系
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spec_id")
    private Set<Options> options;
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
    @NotBlank(message = "规格名称不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '规格名'")
    private String name;

}
