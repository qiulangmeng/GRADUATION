package qlm.web.graduationproject.entity.manager;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 2020.3.20
 */
@Getter
@Setter
@Entity
@Table(name = "role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Role implements GrantedAuthority , Serializable {
    /**
     * 物理结构
     */
    @JsonIgnoreProperties("roles")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL,})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

    @JsonIgnoreProperties("roles")
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name = "role_authority",joinColumns  = @JoinColumn(name = "role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id",referencedColumnName = "id"))
    private Set<Authority> authorities;

    /**
     * role的序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 所有字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "角色id不能为空")
    @Column(nullable = false )
    private Long id;
    @NotBlank(message = "角色名不能为空")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "角色描述能为空")
    private String des;
    @ColumnDefault("'/static/images/roleDefault.png'")
    private String imageUrl;

    /**
     * 权限重写方法
     * @return 返回权限名
     */
    @Override
    public String getAuthority() {
        return name;
    }

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
        Role other = (Role) obj;
        if (id == null) {
            return other.id == null;
        } else {return id.equals(other.id);}
    }

}
