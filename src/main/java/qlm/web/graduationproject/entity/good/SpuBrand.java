package qlm.web.graduationproject.entity.good;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
//json化字段未null解决办法
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class SpuBrand implements Serializable {
    /**
     * 物理结构
     */
    @JsonIgnoreProperties("brand")
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
        SpuBrand other = (SpuBrand) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }

}
