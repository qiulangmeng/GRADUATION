package qlm.web.graduationproject.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import qlm.web.graduationproject.entity.good.Sku;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qlm
 * @version 1.0 10:45 2020.3.225
 */
@Table(name = "order_item")
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class OrderItem implements Serializable {
    /**
     * 物理结构
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "sku_id")
    private Sku sku;
    /**
     * 订单关系
     */
    @JsonIgnoreProperties("orderItems")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Orders orders;
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字段啊
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "状态信息不能为空")
    @Column(nullable = false,columnDefinition = "int(2) comment '记录状态。1：正常，0：禁用，-1：删除'")
    private Integer status;

    @NotNull(message = "商品数量不能为空")
    @Column(nullable = false,columnDefinition = "int(11) comment '商品数量'")
    private Integer number;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;

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
        OrderItem other = (OrderItem) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }

}
