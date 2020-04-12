package qlm.web.graduationproject.controller;

import com.google.code.kaptcha.Producer;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qlm.web.graduationproject.service.sms.SmsService;
import qlm.web.graduationproject.service.sms.SmsServiceImpl;
import qlm.web.graduationproject.utils.RedisUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qlm
 * @version 1.0 13:27 2020.4.6
 */
@Controller
@RequestMapping("/code")
public class CodeController {
    private static final Logger LOG = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Producer kaptchaProducer;
//    @Autowired
//    private SmsService smsService;
    /**
     * 获取图片验证码
     * @param response 请求
     * @param session 回复
     */
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha", text);

        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            LOG.info("响应验证码失败:" + e.getMessage());
        }
    }

    /**
     * 向指定手机发送验证短信，验证码发送并使用请求方指定缓存键缓存到redis中
     * 并向session内写入验证手机号，验证码，验证短信是否发送成功
     * @param mobile 手机号
     * @param redisname 缓存名
     */
    @GetMapping("/sms")
    @ResponseBody
    public boolean sendSms(@RequestParam String mobile,@RequestParam String redisname){
//        String code = SmsServiceImpl.getRandCode(6);
//        boolean isOk = smsService.sendSms(mobile, code);
        Map<String,Object> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("code","123456");
//        map.put("code",code);
        map.put("isOk",true);
        redisUtil.hmset(redisname+mobile,map,600);
        return true;
    }
}
