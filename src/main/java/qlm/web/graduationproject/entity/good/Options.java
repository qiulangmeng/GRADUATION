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
 * @version 1.0 13:41 2020.3.24
 */
@Table(name = "options")
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Options implements Serializable {
    /**
     * 物理结构
     */
    @JsonIgnoreProperties("optionses")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "spec_id")
    private SpuSpecification spuSpecification;
    /**
     * 规格信息关系
     */
    @JsonIgnoreProperties("options")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "option_id")
    private Set<SkuSpecifications> skuSpecifications;

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
    @NotBlank(message = "规格值不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '规格值'")
    private String value;



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
        Options other = (Options) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }
}
