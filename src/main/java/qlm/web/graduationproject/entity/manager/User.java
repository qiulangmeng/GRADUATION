package qlm.web.graduationproject.entity.manager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.UniqueElements;
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
    @Column(nullable = false,unique = true)
    private String userName;

    private String sex;

    @NotBlank(message = "电话不能为空")
    @Column(nullable = false,unique = true)
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @Column(nullable = false)
    @ColumnTransformer()
    private String password;

    private String email;

    @ColumnDefault("'/static/images/userDefault.png'")
    private String userImageUrl;

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

    private User(Builder builder) {
        roles = builder.roles;
        addresses = builder.addresses;
        cars = builder.cars;
        orders = builder.orders;
        userSearches = builder.userSearches;
        setUserAttempts(builder.userAttempts);
        id = builder.id;
        userName = builder.userName;
        sex = builder.sex;
        mobile = builder.mobile;
        password = builder.password;
        email = builder.email;
        userImageUrl = builder.userImageUrl;
        setAccountNonLocked(builder.accountNonLocked);
        createTime = builder.createTime;
        updateTime = builder.updateTime;
    }

    public User() {
    }

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

    /**
     * {@code User} builder static inner class.
     */
    public static final class Builder {
        private Set<Role> roles;
        private Set<Address> addresses;
        private Set<Car> cars;
        private Set<Orders> orders;
        private Set<UserSearch> userSearches;
        private UserAttempts userAttempts;
        private Long id;
        private @NotBlank(message = "用户名不能为空") @UniqueElements String userName;
        private String sex="";
        private @NotBlank(message = "电话不能为空") @UniqueElements String mobile;
        private @NotBlank(message = "密码不能为空") String password;
        private String email="";
        private String userImageUrl="/static/images/userDefault.png";
        private @NotNull(message = "是否锁定不能为空") Boolean accountNonLocked=true;
        private Date createTime=new Date();
        private Date updateTime=new Date();

        public Builder() {
        }
        public Builder(@NotBlank(message = "用户名不能为空") @UniqueElements String userName,
                       @NotBlank(message = "电话不能为空") @UniqueElements String mobile,
                       @NotBlank(message = "密码不能为空") String password){
            this.userName=userName;
            this.mobile=mobile;
            this.password=password;
        }

        /**
         * Sets the {@code roles} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code roles} to set
         * @return a reference to this Builder
         */
        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }

        /**
         * Sets the {@code addresses} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code addresses} to set
         * @return a reference to this Builder
         */
        public Builder addresses(Set<Address> val) {
            addresses = val;
            return this;
        }

        /**
         * Sets the {@code cars} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code cars} to set
         * @return a reference to this Builder
         */
        public Builder cars(Set<Car> val) {
            cars = val;
            return this;
        }

        /**
         * Sets the {@code orders} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code orders} to set
         * @return a reference to this Builder
         */
        public Builder orders(Set<Orders> val) {
            orders = val;
            return this;
        }

        /**
         * Sets the {@code userSearches} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code userSearches} to set
         * @return a reference to this Builder
         */
        public Builder userSearches(Set<UserSearch> val) {
            userSearches = val;
            return this;
        }

        /**
         * Sets the {@code userAttempts} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code userAttempts} to set
         * @return a reference to this Builder
         */
        public Builder userAttempts(UserAttempts val) {
            userAttempts = val;
            return this;
        }

        /**
         * Sets the {@code id} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code id} to set
         * @return a reference to this Builder
         */
        public Builder id(Long val) {
            id = val;
            return this;
        }

        /**
         * Sets the {@code userName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code userName} to set
         * @return a reference to this Builder
         */
        public Builder userName(@NotBlank(message = "用户名不能为空") @UniqueElements String val) {
            userName = val;
            return this;
        }

        /**
         * Sets the {@code sex} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code sex} to set
         * @return a reference to this Builder
         */
        public Builder sex(String val) {
            sex = val;
            return this;
        }

        /**
         * Sets the {@code mobile} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code mobile} to set
         * @return a reference to this Builder
         */
        public Builder mobile(@NotBlank(message = "电话不能为空") @UniqueElements String val) {
            mobile = val;
            return this;
        }

        /**
         * Sets the {@code password} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code password} to set
         * @return a reference to this Builder
         */
        public Builder password(@NotBlank(message = "密码不能为空") String val) {
            password = val;
            return this;
        }

        /**
         * Sets the {@code email} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code email} to set
         * @return a reference to this Builder
         */
        public Builder email(String val) {
            email = val;
            return this;
        }

        /**
         * Sets the {@code userImageUrl} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code userImageUrl} to set
         * @return a reference to this Builder
         */
        public Builder userImageUrl(String val) {
            userImageUrl = val;
            return this;
        }

        /**
         * Sets the {@code accountNonLocked} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code accountNonLocked} to set
         * @return a reference to this Builder
         */
        public Builder accountNonLocked(@NotNull(message = "是否锁定不能为空") Boolean val) {
            accountNonLocked = val;
            return this;
        }

        /**
         * Sets the {@code createTime} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code createTime} to set
         * @return a reference to this Builder
         */
        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        /**
         * Sets the {@code updateTime} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code updateTime} to set
         * @return a reference to this Builder
         */
        public Builder updateTime(Date val) {
            updateTime = val;
            return this;
        }

        /**
         * Returns a {@code User} built from the parameters previously set.
         *
         * @return a {@code User} built with parameters of this {@code User.Builder}
         */
        public User build() {
            return new User(this);
        }
    }

    /**
     * {@code User} builder static inner class.
     */


//构建器



}
