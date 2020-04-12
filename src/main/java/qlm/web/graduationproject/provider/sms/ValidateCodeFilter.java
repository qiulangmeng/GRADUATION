package qlm.web.graduationproject.provider.sms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import qlm.web.graduationproject.utils.RedisUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 校验验证码过滤器
 * @author qlm
 * @version 1.0 15:41 2020.4.8
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeFilter.class.getName());

    /**
     * 需要校验短信验证码的请求
     */
    private List<String> smsCodeUrls = new ArrayList<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /**
         * 如果需要校验短信验证码的请求集合中，包含当前请求，则进行短信验证码校验
         */
        if (smsCodeUrls.contains(request.getRequestURI())) {
            if (smsCodeValid(request, response)) {

                // 校验通过，继续向后执行
                filterChain.doFilter(request, response);
            }

        }

        // 其它请求，直接放过
        else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    protected void initFilterBean() throws ServletException {

        // 初始化添加需要校验的请求到集合中，可由配置文件中配置，此处为了简洁，直接添加
        smsCodeUrls.add("/sms/login");
    }

    /**
     * 短信验证码是否有效
     *
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 有效，返回true；无效，返回false
     * @throws ServletRequestBindingException
     */
    private boolean smsCodeValid(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        String smsCode = ServletRequestUtils.getStringParameter(request, "smsCode");
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        if(redisUtil.getExpire("smsCode"+mobile)<=0){
            write(response,"验证码过期");
        }
        Map<String, Object> redisSmsCodeMap = redisUtil.hmget("smsCode"+mobile);
        String applyMobile= null;
        String applyCode= null;
        try {
            if(!((Boolean) redisSmsCodeMap.get("isOk"))){
                write(response,"您还未请求验证码未发送成功！,请检查手机号");
            }
            applyMobile = (String)redisSmsCodeMap.get("mobile");
            applyCode = (String)redisSmsCodeMap.get("code");
        } catch (NullPointerException e) {
            write(response,"您还未请求验证码！");
    }
        if (StringUtils.isBlank(smsCode)&&
                StringUtils.isBlank(mobile)&&
                StringUtils.isBlank(applyMobile)&&
                StringUtils.isBlank(applyCode)) {
            write(response, "验证信息存在空值！");
            return false;
        } else if (!StringUtils.equals(mobile,applyMobile)) {
            write(response, "发送手机和验证手机不一致！");
            return false;
        } else if (!StringUtils.equals(smsCode,applyCode)) {
            write(response, "验证码不正确！");
            return false;
        }

        // 验证成功，移除Session中验证码
        request.getSession().removeAttribute("smsCode");
        return true;
    }

    /**
     * 发送HTTP响应信息
     *
     * @param response HTTP响应对象
     * @param message 信息内容
     */
    private void write(HttpServletResponse response, String message) {
        response.setContentType("text/html; charset=UTF-8");

        try (
                PrintWriter writer = response.getWriter();
        ) {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
