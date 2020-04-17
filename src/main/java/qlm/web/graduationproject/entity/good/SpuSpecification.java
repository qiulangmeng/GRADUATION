package qlm.web.graduationproject.entity.good;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class SpuSpecification implements Serializable {
    /**
     * 物理结构 spu关系
     */
    @JsonIgnoreProperties("spuSpecifications")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "spu_id")
    private Spu spu;
    /**
     * sku规格信息关系
     */
    @JsonIgnoreProperties("spuSpecification")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spec_id")
    private Set<SkuSpecifications> skuSpecifications;
    /**
     * 规格选项关系
     */
    @JsonIgnoreProperties("spuSpecification")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "spec_id")
    private Set<Options> optionses;
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
        SpuSpecification other = (SpuSpecification) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }

}
