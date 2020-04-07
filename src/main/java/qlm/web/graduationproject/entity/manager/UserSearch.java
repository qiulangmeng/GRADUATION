package qlm.web.graduationproject.entity.manager;

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
public class UserSearch implements Serializable {
    /**
     * 用户关系
     */
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

}
