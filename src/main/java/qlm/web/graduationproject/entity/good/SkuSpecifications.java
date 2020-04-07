package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author qlm
 * @version 1.0 13:41 2020.3.24
 */
@Table(name = "sku_specification")
@Entity
@Getter
@Setter
public class SkuSpecifications implements Serializable {
    /**
     * 物理关系 sku
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "sku_id")
    private Sku sku;
    /**
     * 规格关系
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "spec_id")
    private SpuSpecification spuSpecification;
    /**
     * 规格选项关系
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "option_id")
    private Options options;

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
}
