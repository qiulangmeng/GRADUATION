package qlm.web.graduationproject.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import qlm.web.graduationproject.controller.LoginController;
import qlm.web.graduationproject.entity.address.Address;
import qlm.web.graduationproject.entity.manager.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author qlm
 * @version 1.0 1:05 2020.4.12
 */
@Component
public class RequestToUserUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RequestToUserUtil.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User requestToUser(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        //用户名默认为提交手机号
        String userName=StringUtils.isBlank(request.getParameter("userName"))?mobile:request.getParameter("userName");
        //密码默认为password+mobile
        String password=StringUtils.isBlank(request.getParameter("password"))?"password"+mobile:request.getParameter("password");

        User.Builder builder = new User.Builder(userName, mobile, passwordEncoder.encode(password));
        if(!StringUtils.isBlank(request.getParameter("email"))){
            builder.email(request.getParameter("email"));
        }
        Address address = new Address(request.getParameter("province"),
                request.getParameter("city"),
                request.getParameter("area"),
                request.getParameter("detailAddress"),
                mobile,
                mobile,
                true);
        Set<Address> addresses = new HashSet<>();
        boolean bool = addresses.add(address);
        if(!bool){
            LOG.error("加入地址集合错误");
        }
        builder.addresses(addresses);
        //构建user
        return builder.build();
    }
}
