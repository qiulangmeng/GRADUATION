package qlm.web.graduationproject.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import qlm.web.graduationproject.repository.manager.UserRepository;
import qlm.web.graduationproject.service.manager.UserAttemptsService;

/**
 * 自定义登录授权类
 */
@Component
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder(){
        //使用BCrypt加密
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttemptsService userAttemptsService;

    @Autowired
    private void setJdbcUserDetailsService() {
        setUserDetailsService(userDetailsService);
    }


    /**
     * 增加授权检查
     * @param userDetails 用户明细
     * @param authentication 授权信息
     * @throws AuthenticationException 授权异常
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("验证失败:没有提供凭据");

            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("验证失败:密码与存储值不匹配");

            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误:剩余尝试次数"+(5-userAttemptsService.getUserAttempts(userDetails.getUsername()).getAttempts())));
        }
    }

    /**
     * 在原有的验证过程上增加自定义登录
     * 通过，重置用户失败登录记录为零
     * 未通过，失败次数attempts+1,五次后锁定（account_non_locked置为false)
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //调用上层验证逻辑
        try {
            Authentication auth = super.authenticate(authentication);
            userAttemptsService.resetFailAttempts(authentication.getName());
            return auth;
        } catch (BadCredentialsException  e) {
            //如果验证不通过，则更新尝试次数，当超过次数以后抛出账号锁定异常
            userAttemptsService.updateFailAttempts(authentication.getName());
            throw e;
        }
    }
}
