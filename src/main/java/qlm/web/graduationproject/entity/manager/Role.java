package qlm.web.graduationproject.entity.manager;

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
@EqualsAndHashCode
public class Role implements GrantedAuthority , Serializable {
    /**
     * 物理结构
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL,})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

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

}
