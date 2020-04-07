package qlm.web.graduationproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qlm
 * @version 1.0 15:46 2020.3.20
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/public/home").setViewName("home");
        registry.addViewController("/admin").setViewName("admin/home");
        registry.addViewController("/superadmin").setViewName("superadmin/home");

    }
}
