package qlm.web.graduationproject.entity.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import qlm.web.graduationproject.entity.address.Address;
import qlm.web.graduationproject.entity.manager.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 10:45 2020.3.25
 */
@Table(name = "orders")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Orders implements Serializable {
    /**
     * 物理结构 用户关系
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 订单详情关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id")
    private Set<OrderItem> orderItems;
    /**
     * 地址
     */
    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "address_id")
    private Address address;
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
    @NotBlank(message = "订单号不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '唯一订单号'")
    private String orderNumber;

    @NotNull(message = "实际支付不能为空")
    @Column(nullable = false,columnDefinition = "bigint(20) comment '实际支付金额'")
    private Long payMent;


    private String postNumber;
    private Integer postFee;

    @NotNull(message = "订单状态不能为空")
    @Column(nullable = false,columnDefinition = "int(2) comment '记录状态。1：正常，0：禁用，-1：删除'")
    private Integer status;

    @NotNull(message = "支付状态不能为空")
    @Column(nullable = false,columnDefinition = "tinyint(4) comment '是否已经支付。1：完成支付，0：未支付'")
    private Boolean isPay;
    private Date payTime;

    @NotNull(message = "发货状态不能为空")
    @Column(nullable = false,columnDefinition = "tinyint(4) comment '是否已经发货。1：完成发货，0：未发货'")
    private Boolean isDelivery;
    private Date deliveryTime;

    @NotNull(message = "收货状态不能为空")
    @Column(nullable = false,columnDefinition = "tinyint(4) comment '是否已经收货。1：完成收货，0：未收货'")
    private Boolean isReceived;
    private Date ReceiveTime;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
}
