package qlm.web.graduationproject.entity.car;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import qlm.web.graduationproject.entity.good.Sku;
import qlm.web.graduationproject.entity.manager.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qlm
 * @version 1.0 10.:44 2020.3.25
 */
@Table(name = "car")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Car implements Serializable {
    /**
     * 物理结构用户关系
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 商品关系
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
    @NotBlank(message = "购物车种商品名不能为空")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "商品数量不能为空")
    @Column(nullable = false)
    private Integer number;
    @NotNull(message = "商品总价不能为空")
    @Column(nullable = false)
    private Long totalPrice;
    @NotNull(message = "商品状态不能为空")
    @Column(nullable = false,columnDefinition = "int(2) comment '记录状态。1：正常，0：禁用，-1：删除'")
    private Integer status;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
}
