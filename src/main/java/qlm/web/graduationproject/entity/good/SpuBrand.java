package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 13:42 2020.3.34
 */
@Table(name = "spu_brand")
@Entity
@Getter
@Setter
public class SpuBrand implements Serializable {
    /**
     * 物理结构
     */
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "brand_id")
    private Set<Spu> spus;
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "品牌名不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '产品名'")
    private String name;

    @ColumnDefault("'/static/images/brandDefault.png'")
    private String logo;

}
