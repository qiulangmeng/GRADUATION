package qlm.web.graduationproject.controller;

import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qlm.web.graduationproject.entity.manager.User;
import qlm.web.graduationproject.io.Message;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 21:14 2020.3.20
 * 登录页的控制层
 */

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);




    private static final String SUPERADMIN = "ROLE_SUPERADMIN";
    private static final String ADMIN = "ROLE_ADMIN";
    /**
     * @return 请求登录和根目录访问都挑战到登录页
     */

//    @GetMapping({"/login","/"})
//    public String loginPage(){
//        return "login";
//    }
    /**
     * 发生登录错误的时候
     * @return 携带消息返回登录界面
     */
    @GetMapping("/login?error")
    public String errorMsg(){
        return "login";
    }

    /**
     * 登出跳转
     * @return 首页
     */
    @GetMapping("/login?logout")
    public String logOut(){
        return "home";
    }

    /**
     * 注册
     * @param request 请求
     * @param response 回复
     * @return 消息类
     */
    @PostMapping("/register")
    @ResponseBody
    public Message register(HttpServletRequest request, HttpServletResponse response){
        String rightCode = (String) request.getSession().getAttribute("kaptcha");
        String tryCode = request.getParameter("tryCode");
        //如果验证码不通过直接返回验证码错误信息
        if (!rightCode.equals(tryCode)) {
            return new Message("注册","验证码错误",request.getParameter("userName"));
        }
        //用户名重复
        if(true){
            return new Message("注册","用户名重复",request.getParameter("userName"));
        }
        //邮箱重复
        if(true){
            return new Message("注册","邮箱重复",request.getParameter("userName"));
        }
        //电话重复
        if(true){
            return new Message("注册","电话重复",request.getParameter("userName"));
        }
        //try..catch增加用户，向邮箱发送激活链接

        try {
            return new Message("注册","注册成功",request.getParameter("userName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据不同用户权限跳转到不同用户所需界面
     */
    @GetMapping("/dispath")
    public String dispath(){
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities());
        String s;
        if(roles.contains(SUPERADMIN)){
            s = "redirect:/superadmin";
        }
        else if(roles.contains(ADMIN)){
            s = "redirect:/admin";
        }else {
            s = "redirect:/public/home";
        }
        return s;
    }

}
