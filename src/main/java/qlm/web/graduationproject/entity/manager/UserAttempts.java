package qlm.web.graduationproject.entity.manager;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qlm
 * @version 1.0 14:47 2020.4.3
 */
@Table(name = "user_attempts")
@Entity
@Getter
@Setter
public class UserAttempts implements Serializable {

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
    @NotBlank(message = "用户名不能为空")
    @Column(nullable = false)
    private String userName;

    @NotNull(message = "失败登录次数不能为空")
    @Column(nullable = false)
    private Integer attempts;

    public UserAttempts(@NotBlank(message = "用户名不能为空") String userName, @NotNull(message = "失败登录次数不能为空") Integer attempts, Date lastModified) {
        this.userName = userName;
        this.attempts = attempts;
        this.lastModified = lastModified;
    }

    @LastModifiedDate
    private Date lastModified;


    public UserAttempts() {
    }

    public UserAttempts(Long id, @NotBlank(message = "用户名不能为空") String userName, @NotBlank(message = "失败登录次数不能为空") Integer attempts, Date lastModified) {
        this.id = id;
        this.userName = userName;
        this.attempts = attempts;
        this.lastModified = lastModified;
    }
}
