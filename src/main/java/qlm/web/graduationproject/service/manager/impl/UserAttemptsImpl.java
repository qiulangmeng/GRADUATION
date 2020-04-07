package qlm.web.graduationproject.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import qlm.web.graduationproject.entity.manager.User;
import qlm.web.graduationproject.entity.manager.UserAttempts;
import qlm.web.graduationproject.repository.manager.UserAttemptsRepository;
import qlm.web.graduationproject.repository.manager.UserRepository;
import qlm.web.graduationproject.service.manager.UserAttemptsService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author qlm
 * @version 1.0 12:11 2020.4.3
 */
@Service
public class UserAttemptsImpl implements UserAttemptsService {

    private static final Integer MAX_ATTEMPTS = 5;

    @Autowired
    UserAttemptsRepository userAttemptsRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * 用户必须存在
     * 用户记录不存在：新增
     * 用户记录存在：添加记录并更新日期
     * 记录超过阈值：锁定
     * @param userName 用户名
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateFailAttempts(String userName) {
        User user = Objects.requireNonNull(userRepository.findByUserName(userName),"用户不存在");
        UserAttempts userAttempts = getUserAttempts(userName);
        //未找到记录
        if(userAttempts==null){
            //新增
            user.setUserAttempts(new UserAttempts(userName, 1, new Date()));
            //保存到数据库
            userRepository.save(user);

        }else {
            if(userAttempts.getAttempts()+1>MAX_ATTEMPTS){
                //抛出锁定异常，方便做出对应操作
                user.setAccountNonLocked(false);
                userRepository.save(user);
            }
            //更新
            userAttemptsRepository.save(new UserAttempts(userAttempts.getId(),userName,userAttempts.getAttempts()+1,new Date()));

        }
    }
    /**
     * 用户记录必须存在
     * 未锁定是成功登录后，记录设为0，修改日期为空
     * @param userName 用户名
     */
    @Override
    public void resetFailAttempts(String userName) {
        UserAttempts userAttempts = Objects.requireNonNull(userAttemptsRepository.findByUserName(userName), "用户登录记录不存在");
        userAttemptsRepository.save(new UserAttempts(userAttempts.getId(),userName,0,null));
    }

    /**
     * 用户记录必须存在
     * 解开锁定，记录设为0，修改日期为空
     * @param userName 用户名
     */
    @Override
    public void unlock(String userName) {
        User user = Objects.requireNonNull(userRepository.findByUserName(userName),"用户不存在");
        UserAttempts userAttempts = Objects.requireNonNull(userAttemptsRepository.findByUserName(userName), "用户登录记录不存在");
        user.setUserAttempts(userAttempts);
        userRepository.save(user);
    }

    /**
     * 查找用户记录
     * @param userName 用户名
     * @return 用户记录
     */
    @Override
    public UserAttempts getUserAttempts(String userName) {
        return userAttemptsRepository.findByUserName(userName);
    }

    @Override
    public long overTime(UserAttempts userAttempts) {
        return Duration.between(date2LocalDateTime(userAttempts.getLastModified()),LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")))).toMinutes();
    }

    /**
     * Date转换为LocalDateTime
     * @param date java.util.Date
     * @return LocalDateTime
     */
    public LocalDateTime date2LocalDateTime(Date date){
        //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        Instant instant = date.toInstant();
        //A time-zone ID, such as {@code Europe/Paris}.(时区)
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

}
