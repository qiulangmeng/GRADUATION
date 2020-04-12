package qlm.web.graduationproject.entity.address;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import qlm.web.graduationproject.entity.manager.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author qlm
 * @version 1.0 15:44 2020.3.23
 */
@Getter
@Setter
@Entity
@Table(name = "address")
@EqualsAndHashCode
public class Address implements Serializable {
    //序列化
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "省份不能为空")
    @Column(nullable = false)
    private String province;

    @NotBlank(message = "城市不能为空")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "地区不能为空")
    @Column(nullable = false)
    private String area;

    @NotBlank(message = "详细地址不能为空")
    @Column(nullable = false)
    private String detailAddress;

    @NotBlank(message = "联系人不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '联系人名称'")
    private String theContact;

    @NotBlank(message = "联系人电话不能为空")
    @Column(nullable = false,columnDefinition = "varchar(255) comment '联系人电话'")
    private String contactPhone;

    @NotNull(message = "是否默认不能为空")
    @Column(nullable = false,columnDefinition = "tinyint comment '是否默认'")
    private Boolean isDefault;


    public Address() {
    }

    public Address(@NotBlank(message = "省份不能为空") String province, @NotBlank(message = "城市不能为空") String city, @NotBlank(message = "地区不能为空") String area, @NotBlank(message = "详细地址不能为空") String detailAddress, @NotBlank(message = "联系人不能为空") String theContact, @NotBlank(message = "联系人电话不能为空") String contactPhone, @NotNull(message = "是否默认不能为空") Boolean isDefault) {
        this.province = province;
        this.city = city;
        this.area = area;
        this.detailAddress = detailAddress;
        this.theContact = theContact;
        this.contactPhone = contactPhone;
        this.isDefault = isDefault;
    }
}
