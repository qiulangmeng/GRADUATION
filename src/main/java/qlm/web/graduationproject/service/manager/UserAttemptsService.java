package qlm.web.graduationproject.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import qlm.web.graduationproject.entity.manager.UserAttempts;
import qlm.web.graduationproject.repository.manager.UserAttemptsRepository;

/**
 * @author qlm
 * @version 1.0 12:08 2020.4.3
 */
@Repository
public interface UserAttemptsService {
    void updateFailAttempts(String userName);
    void resetFailAttempts(String userName);
    void unlock(String userName);
    UserAttempts getUserAttempts(String userName);
    long overTime(UserAttempts userAttempts);
}
