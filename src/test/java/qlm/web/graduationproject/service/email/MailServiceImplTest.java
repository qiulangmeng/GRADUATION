package qlm.web.graduationproject.service.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qlm.web.graduationproject.GraduationProjectApplication;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qlm
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={GraduationProjectApplication.class})
 public class MailServiceImplTest {
    @Autowired
    private MailService mailService;
    @Test
    public void sayHello() {
        System.out.println("Hello world");
    }
    @Test
    public void sendMail(){
        mailService.sendMail("2573393471@qq.com","有机思想家","测试");
    }
}