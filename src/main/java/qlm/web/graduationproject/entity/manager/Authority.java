package qlm.web.graduationproject.entity.manager;

import lombok.Data;
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
 * @version 1.0 13:50 2020.3.22
 */
@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority, Serializable {
    /**
     * 物理结构 一个权限对应对各角色
     */
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinTable(name = "role_authority",joinColumns  = @JoinColumn(name = "authority_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles;

    /**
     * authority的序列化id
     */
    private static final long serialVersionUID = 1L;


    /**
     * 所有字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "权限id不能为空")
    @Column(nullable = false)
    private Long id;
    @NotBlank(message = "权限名不能为空")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "权限描述能为空")
    private String des;
    @ColumnDefault("'/static/images/authorityDefault.png'")
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
