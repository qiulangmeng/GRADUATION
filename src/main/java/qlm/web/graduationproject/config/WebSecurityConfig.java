package qlm.web.graduationproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import qlm.web.graduationproject.provider.LoginAuthenticationProvider;
import qlm.web.graduationproject.provider.sms.SmsCodeAuthenticationProvider;
import qlm.web.graduationproject.provider.sms.ValidateCodeFilter;

/**
 * @author qlm
 * @version 1.0 16:18 2020.3.20
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);


    private static final String KEY = "web";


    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

    /**
     * 配置地址栏不能识别 // 的情况
     * @return 防火墙
     */
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        //此处可添加别的规则,目前只设置 允许双 //
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    /**
     * http请求的安全的权限管理
     * @param http http安全
     * @throws Exception 配置异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .apply(smsCodeAuthenticationSecurityConfig)

                // 设置验证码过滤器到过滤器链中，在UsernamePasswordAuthenticationFilter之前执行
                .and().addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)

                //自定义表单登录界面
                .formLogin().loginPage("/login")

                // 设置登录验证请求地址为自定义登录页配置action （"/account/login"）
                .loginProcessingUrl("/account/login")

                //设置登录页，成功后访问的页面和访问错误页，dispath将根据角色分配不同页面
                .defaultSuccessUrl("/dispath")

                //失败界面
                .failureUrl("/login?error=true")

                //授权请求设置
                .and().authorizeRequests()

                //访问/superadmin控制台需要superadmin角色
                .antMatchers("/superadmin/**").hasRole("SUPERADMIN")

                //访问/admin控制台需要admin角色
                .antMatchers("/admin/**").hasAnyRole("SUPERADMIN", "ADMIN")

                //访问/user控制台需要user角色
                .antMatchers("/user/**").hasAnyRole("SUPERADMIN", "ADMIN", "USER")

                //静态资源，登录资源，公共资源，控制台资源所有请求都可以访问
                .antMatchers("/static/**", "login.html", "/public/**","/code/**", "/h2-console/**").permitAll()

                //remember-me设置
                .and().rememberMe().key(KEY)

                // 【记住我功能】有效期为两周
                .tokenValiditySeconds(3600 * 24 * 14)

                //跨域访问安全禁止
                .and().csrf().disable()

                //自定义登出请求
                .logout().logoutUrl("/logout")

                //成功
                .logoutSuccessUrl("/login?logout=true")

                //请求
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))

                //删除缓存
                .deleteCookies("JSESSIONID")

                //删除session
                .invalidateHttpSession(true)


                //账号密码错误进入403界面
                .and().exceptionHandling().accessDeniedPage("/403");

        //禁用h2控制台的csrf防护
        http.csrf().ignoringAntMatchers("/h2-console/**");
        //允许来自同一来源的h2控制台的请求
        http.headers().frameOptions().sameOrigin();
    }

    /**
     * 初始化授权管理构建器
     * @param auth 传递的未初始化的构建起
     * @throws Exception 构建异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置权限管理提供者
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(smsCodeAuthenticationProvider);
    }

}


