package qlm.web.graduationproject.provider.sms;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qlm
 * @version 1.0 17:13 2020.4..8
 */
@Component
public class myAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        // 设置默认跳转页面，当没有重定向页面时（例如：直接访问登录页面），此配置生效
        super.setDefaultTargetUrl("/dispath");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
