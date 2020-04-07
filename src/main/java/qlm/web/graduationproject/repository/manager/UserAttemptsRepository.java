package qlm.web.graduationproject.repository.manager;

import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.manager.UserAttempts;

/**
 * @author qlm
 * @version 1.0 12:07 2020.4.3
 */
public interface UserAttemptsRepository extends JpaRepository<UserAttempts,Long> {
    /**
     * 通过用户名查找
     * @param userName 用户名
     * @return 查找结果
     */
    UserAttempts findByUserName(String userName);


}

