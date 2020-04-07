package qlm.web.graduationproject.entity.manager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import qlm.web.graduationproject.entity.address.Address;
import qlm.web.graduationproject.entity.car.Car;
import qlm.web.graduationproject.entity.order.Orders;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * 登录者实体类
 * 内含管理员，用户，游客
 *
 * @author qlm
 * @version 1.0 2020.3.19
 */
@Getter
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Serializable {
    /**
     * 物理结构
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL,})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
    /**
     * 地址关系
     */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Address> addresses;
    /**
     * 购物车关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Set<Car> cars;
    /**
     * 订单关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Set<Orders> orders;
    /**
     * 搜索记录关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Set<UserSearch> userSearches;
    /**
     * 用户登录关系
     */
    @Setter
    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    private UserAttempts userAttempts;

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * 所有字段
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Column(nullable = false)
    private String userName;

    private String sex;

    @NotBlank(message = "电话不能为空")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Column(nullable = false)
    private String email;
    @ColumnDefault("'/static/images/userDefault.png'")
    private String userImageUrl;

    @NotNull(message = "激活状态不能为空")
    @Column(nullable = false,columnDefinition = "tinyint comment '是否激活'")
    private Boolean isActive;

    @NotBlank(message = "激活码不能为空")
    @Column(nullable = false )
    private String activeCode;

    @NotNull(message = "是否锁定不能为空")
    @Column(nullable = false,columnDefinition = "tinyint default 1 comment '是否上架'")
    @Setter
    private Boolean accountNonLocked;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
    private Long defaultAddressId;
    /**
     * 重写方法
     * @return 权限集合
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auths = new HashSet<>();
        Set<Role> roles = this.getRoles();
        //获取角色
        for (Role role : roles) {
            //获取权限
            for (Authority authority : role.getAuthorities()) {
                auths.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    /**
     * 账号是否过期
     * @return 是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否锁定
     * @return 是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否过期
     * @return 是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
