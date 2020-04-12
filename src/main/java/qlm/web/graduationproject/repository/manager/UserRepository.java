package qlm.web.graduationproject.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.manager.User;

/**
 * @author qlm
 * @version 1.0 11:03 2020.3.21
 */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    User findByUserName(String username);

    /**
     * 通过手机号查找用户
     * @param mobile 手机号
     * @return 用户
     */
    User findByMobile(String mobile);
}
