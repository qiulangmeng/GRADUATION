package qlm.web.graduationproject.service.email;

/**
 * @author qlm
 * @version 1.0 10:56 2020.4.9
 */
public interface MailService {
    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendMail(String to,String subject,String content);
}
