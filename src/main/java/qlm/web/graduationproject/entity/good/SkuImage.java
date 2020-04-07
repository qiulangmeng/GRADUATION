package qlm.web.graduationproject.entity.good;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author qlm
 * @version 1.0 13:23 2020.3.24
 */
@Table(name = "sku_image")
@Entity
@Getter
@Setter
public class SkuImage implements Serializable {
    /**
     * 物理结构
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "sku_id")
    private Sku sku;

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
    @NotBlank(message = "图片url不能为空")
    @Column(nullable = false)
    private String imageUrl;
}

