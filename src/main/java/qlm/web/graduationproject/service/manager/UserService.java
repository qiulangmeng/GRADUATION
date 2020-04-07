package qlm.web.graduationproject.service.manager;
import org.springframework.stereotype.Repository;
import qlm.web.graduationproject.entity.manager.User;

import java.util.List;

/**
 * @author qlm
 * @version 1.0 14:24 2020.3.20
 */
@Repository
public interface UserService {
    /**
     * 获取所有User
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 通过id找User
     * @param id 用户id
     * @return 用户对象
     */
    User findById(Long id);

    /**
     * 增加用户
     * @param user 用户对象
     * @return 用户对象
     */
    User saveOrUpdateUser(User user);

    /**
     * 删除用户
     * @param id 用户id
     */
    void deleteById(Long id);
}
