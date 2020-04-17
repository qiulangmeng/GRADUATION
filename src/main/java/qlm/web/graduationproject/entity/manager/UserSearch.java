package qlm.web.graduationproject.entity.manager;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author qlm
 * @version 1.0 16:16 2020.3.27
 */
@Table(name = "user_search")
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class UserSearch implements Serializable {
    /**
     * 用户关系
     */
    @JsonIgnoreProperties("userSearches")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
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
    @NotBlank(message = "搜索关键字不能为空")
    @Column(nullable = false)
    private String keyWord;

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
        UserSearch other = (UserSearch) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }
}
