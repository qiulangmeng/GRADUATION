package qlm.web.graduationproject.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qlm.web.graduationproject.entity.good.Sku;
import qlm.web.graduationproject.entity.manager.Role;
import qlm.web.graduationproject.entity.manager.User;
import qlm.web.graduationproject.io.Message;
import qlm.web.graduationproject.repository.good.SkuReposity;
import qlm.web.graduationproject.repository.manager.RoleRepository;
import qlm.web.graduationproject.service.manager.UserService;
import qlm.web.graduationproject.utils.RedisUtil;
import qlm.web.graduationproject.utils.RequestToUserUtil;

import javax.servlet.http.HttpServletRequest;
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
    private SkuReposity skuReposity;

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

//---------------------------登录---------------------------------------
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
//-------------------------------------------//登录------------------------------------------------

//-------------------------------------------注册----------------------------------------------------
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
        Boolean bool = isValidRegistRequest(request);
        if (!bool) {
            return Message.registMessage("参数错误", request);
        }
        bool = isValidRegistSmsCode(request);
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
     * 验证注册参数时候缺少
     * @param request 请求
     * @return 判断
     */
    private Boolean isValidRegistRequest(HttpServletRequest request) {

        return request.getParameter("mobile") != null &&
                request.getParameter("province") != null &&
                request.getParameter("city") != null &&
                request.getParameter("area") != null &&
                request.getParameter("detailAddress") != null &&
                request.getParameter("smsCode") != null;
    }

    /**
     * 验证注册验证码对不对
     * @param request 请求
     * @return 判断
     */
    private Boolean isValidRegistSmsCode(HttpServletRequest request) {
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

//-------------------------------------------//注册----------------------------------------------------

//-------------------------------------------修改密码----------------------------------------------------
@PostMapping("/resetByMobile")
@ResponseBody
public Message resetByMobile(HttpServletRequest request){
    //第一步：判断验证请求字段是否足够
    Boolean bool = isValidResetRequest(request);
    if (!bool) {
        return Message.resetMessage("参数错误", request);
    }
    bool = isValidResetSmsCode(request);
    if (!bool) {
        return Message.resetMessage("验证码或手机号错误", request);
    }
    try {
        User user=requestToUserUtil.resetUser(request);
        userService.saveOrUpdateUser(user);
    } catch (Exception e) {
        e.printStackTrace();
        return Message.resetMessage("重置或存储发生错误", request);
    }
    return Message.resetMessage("重置成功", request);
}
    /**
     * 验证重置参数时候缺少
     * @param request 请求
     * @return 判断
     */
    private Boolean isValidResetRequest(HttpServletRequest request) {

        return request.getParameter("mobile") != null &&
                request.getParameter("password") != null &&
                request.getParameter("smsCode") != null;
    }

    /**
     * 验证重置验证码对不对
     * @param request 请求
     * @return 判断
     */
    private Boolean isValidResetSmsCode(HttpServletRequest request) {
        String redisName = "smsResetCode";
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
//-------------------------------------------//修改密码----------------------------------------------------

//-------------------------------------------检查---------------------------------------------------

    /**
     * 检查手机号是否被注册
     * @param mobile 手机号
     * @return 判断结果
     */
    @GetMapping("/checkMobile")
    @ResponseBody
    public Message checkMobile(String mobile) {
        Message message = new Message();
        message.setTitle("检查手机号");
        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByMobile(mobile);
        } catch (BadCredentialsException e) {
            message.setJugde("false");
            return message;
        }
        message.setJugde("true");
        return message;
    }
    /**
     * 检查用户名是否被注册
     * @param username 用户名
     * @return 判断结果
     */
    @GetMapping("/checkUsername")
    @ResponseBody
    public Message checkUsername(String username) {
        Message message = new Message();
        message.setTitle("检查用户名");
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            message.setJugde("false");
            return message;
        }
        message.setJugde("true");
        return message;
    }

//-------------------------------------------//检查---------------------------------------------------

    @GetMapping("/myuser")
    @ResponseBody
    public Sku getUser(){
        return skuReposity.findById(1L).get();
    }

}
