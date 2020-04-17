package qlm.web.graduationproject.entity.good;

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
 * @version 1.0 13:42 2020.3.24
 */
@Table(name = "spu_category")
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class SpuCategory implements Serializable {
    /**
     * 物理结构
     */
    @JsonIgnoreProperties("firstCategory")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category1_id")
    private Set<Spu> firstSups;
    @JsonIgnoreProperties("secondCategory")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category2_id")
    private Set<Spu> secondSups;
    @JsonIgnoreProperties("thirdCategory")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category3_id")
    private Set<Spu> thirdSups;

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "类别名称不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '目录名'")
    private String name;

    private Integer parentCategory;


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
        SpuCategory other = (SpuCategory) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }


}
