package qlm.web.graduationproject.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import qlm.web.graduationproject.entity.manager.User;
import qlm.web.graduationproject.entity.manager.UserAttempts;
import qlm.web.graduationproject.repository.manager.UserAttemptsRepository;
import qlm.web.graduationproject.repository.manager.UserRepository;
import qlm.web.graduationproject.service.manager.UserAttemptsService;
import qlm.web.graduationproject.service.manager.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

/**
 * @author qlm
 * @version 1.0 14:29 2020.3.20
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttemptsService userAttemptsService;

    @Autowired
    private UserAttemptsRepository userAttemptsRepository;

    /**
     * 通过用户名得到用户
     * @param s 用户名
     * @return 用户对象
     * @throws UsernameNotFoundException 对象不存在
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{//取出用户信息
        User user = userRepository.findByUserName(s);
        if(user==null){
            throw new BadCredentialsException("用户不存在!");
        }
        if(!user.getAccountNonLocked()){
            //锁定时间5分钟
            long overTime = userAttemptsService.overTime(user.getUserAttempts());
            if(overTime>=5){
                userAttemptsService.resetFailAttempts(user.getUsername());
            }
            else {
                throw new LockedException("用户账号已被锁定，"+(5-overTime)+"分钟后重试");
            }
        }else {
            //有失败记录但是未锁定的用户每分钟回复一次登录机会
            if(user.getUserAttempts()!=null&&user.getUserAttempts().getLastModified()!=null){
                UserAttempts userAttempts = user.getUserAttempts();
                long overTime = userAttemptsService.overTime(userAttempts);
                long addTimes = overTime>userAttempts.getAttempts()?0:userAttempts.getAttempts()-overTime;
                userAttemptsRepository.save(new UserAttempts(userAttempts.getId(),userAttempts.getUserName(),(int)addTimes,new Date()));
            }
        }

        return user;
    }
    @Override
    public UserDetails loadUserByMobile(String mobile) {
        User user = userRepository.findByMobile(mobile);
        if(user==null){
            throw new BadCredentialsException("用户不存在!");
        }
        if(!user.getAccountNonLocked()){
            //锁定时间5分钟
            long overTime = userAttemptsService.overTime(user.getUserAttempts());
            if(overTime>=5){
                userAttemptsService.resetFailAttempts(user.getUsername());
            }
            else {
                throw new LockedException("用户账号已被锁定，"+(5-overTime)+"分钟后重试");
            }
        }else {
            //有失败记录但是未锁定的用户每分钟回复一次登录机会
            if(user.getUserAttempts()!=null&&user.getUserAttempts().getLastModified()!=null){
                UserAttempts userAttempts = user.getUserAttempts();
                long overTime = userAttemptsService.overTime(userAttempts);
                long addTimes = overTime>userAttempts.getAttempts()?0:userAttempts.getAttempts()-overTime;
                userAttemptsRepository.save(new UserAttempts(userAttempts.getId(),userAttempts.getUserName(),(int)addTimes,new Date()));
            }
        }

        return user;
    }

    /**
     * 找出所有对象
     * @return 对象的列表
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 通过id找用户
     * @param id 用户id
     * @return 存储的用户||未初始化的用户对象
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public User findById(Long id) {
        Optional<User> opt = userRepository.findById(id);
        return opt.orElseGet(User::new);
    }

    /**
     * 用户对象
     * @param user 用户对象
     * @return 保存成功的用户
     * @throw 存储用户失败
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public User saveOrUpdateUser(@Valid User user) {

        try {
            userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Add User Error: "+e.getMessage());
        }
        return user;
    }

    /**
     * 删除用户
     * @param id 用户id
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete User Error"+ e.getMessage());
        }
    }

}
