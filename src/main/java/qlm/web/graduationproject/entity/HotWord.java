package qlm.web.graduationproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用于搜索时显示热搜词汇
 * @author qlm
 * @version 1.0 16:22 2020.3.27
 */
@Table(name = "host_word")
@Entity
@Getter
@Setter
public class HotWord implements Serializable {
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
    @NotBlank(message = "热搜词汇不能为空")
    @Column(nullable = false)
    private String hotWord;
}
