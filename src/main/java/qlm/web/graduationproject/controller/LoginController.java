package qlm.web.graduationproject.controller;

import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qlm.web.graduationproject.entity.manager.User;
import qlm.web.graduationproject.io.Message;
import qlm.web.graduationproject.service.manager.UserService;
import qlm.web.graduationproject.utils.RedisUtil;
import qlm.web.graduationproject.utils.RequestToUserUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 21:14 2020.3.20
 * 登录页的控制层
 */

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RequestToUserUtil requestToUserUtil;

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
     *
     * @return 携带消息返回登录界面
     */
    @GetMapping("/login?error")
    public String errorMsg() {

        return "login";
    }

    /**
     * 登出跳转
     *
     * @return 首页
     */
    @GetMapping("/login?logout")
    public String logOut() {
        return "home";
    }

    /**
     * 注册
     *
     * @param request 请求
     * @return 消息类
     */
    @PostMapping("/register")
    @ResponseBody
    public Message register(HttpServletRequest request) {
        //第一步：判断验证请求字段是否足够
        Boolean bool = isValidRequest(request);
        if (!bool) {
            return Message.registMessage("参数错误", request);
        }
        bool = isValidSmsCode(request);
        if (!bool) {
            return Message.registMessage("验证码或手机号错误", request);
        }
        try {
            User user = requestToUserUtil.requestToUser(request);
            userService.saveOrUpdateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.registMessage("注册存储发生错误", request);
        }
        return Message.registMessage("注册成功", request);
    }


    /**
     * 根据不同用户权限跳转到不同用户所需界面
     */
    @GetMapping("/dispath")
    public String dispath() {
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities());
        String s;
        if (roles.contains(SUPERADMIN)) {
            s = "redirect:/superadmin";
        } else if (roles.contains(ADMIN)) {
            s = "redirect:/admin";
        } else {
            s = "redirect:/public/home";
        }
        return s;
    }

    @GetMapping("/checkMobile")
    @ResponseBody
    public Message checkMobile(String mobile) {
        UserDetails userDetails = userService.loadUserByMobile(mobile);
        Message message = new Message();
        message.setTitle("检查手机号");
        if (userDetails == null) {
            message.setJugde("true");
            return message;
        }
        message.setJugde("false");
        return message;
    }

    @GetMapping("/checkUsername")
    @ResponseBody
    public Message checkUsername(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Message message = new Message();
        message.setTitle("检查用户名");
        if (userDetails == null) {
            message.setJugde("true");
            return message;
        }
        message.setJugde("false");
        return message;
    }

    private Boolean isValidRequest(HttpServletRequest request) {

        return request.getParameter("mobile") != null &&
                request.getParameter("province") != null &&
                request.getParameter("city") != null &&
                request.getParameter("area") != null &&
                request.getParameter("detailAddress") != null &&
                request.getParameter("smsCode") != null;
    }

    private Boolean isValidSmsCode(HttpServletRequest request) {
        String redisName = "smsRegistCode";
        String mobile = request.getParameter("mobile");
        String smsCode = request.getParameter("smsCode");
        if (redisUtil.getExpire(redisName + mobile) <= 0) {
            return false;
        }
        Map<String, Object> redisSmsCodeMap = Objects.requireNonNull(redisUtil.hmget(redisName + mobile));
        String applyMobile = (String) redisSmsCodeMap.get("mobile");
        String applyCode = (String) redisSmsCodeMap.get("code");
        if (StringUtils.isBlank(smsCode) &&
                StringUtils.isBlank(mobile) &&
                StringUtils.isBlank(applyMobile) &&
                StringUtils.isBlank(applyCode)) {
            return false;
        } else if (!StringUtils.equals(mobile, applyMobile)) {
            return false;
        } else if (!StringUtils.equals(smsCode, applyCode)) {
            return false;
        }
        redisUtil.remove(redisName + mobile);
        return true;
    }


}
