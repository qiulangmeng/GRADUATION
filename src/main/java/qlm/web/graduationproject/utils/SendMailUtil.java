package qlm.web.graduationproject.utils;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 发送邮件
 * @author qlm
 * @version 1.0 17:26 2020.3.26
 */
public class SendMailUtil {

    public static void sendMail(String email, String subject, String emailMsg)
            throws AddressException, MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");

    //如果是其他邮箱就把qq改为相应的邮箱后缀
        props.setProperty("mail.host", "smtp.qq.com");

    // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");

        // 创建验证器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {

    //发邮件的账号和密码，此处需要到QQ邮箱官网开启SMTP,P0P3,IMAP服务，然后获取授权码作为第三方登录密码
                return new PasswordAuthentication("2573393471@qq.com", "ccrlhrhtzctfdihg");
            }
        };

        //不同与request的session
        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
        // 设置发送者
        message.setFrom(new InternetAddress("2573393471@qq.com"));
        // 设置发送方式与接收者
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        //主题
        message.setSubject(subject);
        //内容
        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送

        Transport.send(message);
    }

    /**
     * 邮件发送的方法
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param smtp 协议
     * @param host 发送服务器服务器
     * @param from 邮件发送人
     * @param sendPort 邮件发送人端口
     * @param userName 邮件发送人名
     * @param userPwd 邮件发送人密码
     * @return 成功或失败
     */
    public static boolean send(String to, String subject, String content, String smtp, String host,
                               String from, String sendPort, String userName, String userPwd) {

        // 第一步：创建Session
        Properties props = new Properties();
        // 指定邮件的传输协议，smtp(Simple Mail Transfer Protocol 简单的邮件传输协议)
        props.put("mail.transport.protocol", smtp);
        // 指定邮件发送服务器服务器 "smtp.qq.com"
        props.put("mail.host", host);
        // 指定邮件的发送人(您用来发送邮件的服务器，比如您的163\sina等邮箱)
        props.put("mail.from", from);
        if (true) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.socketFactory.port", sendPort);
        }
        Session session = Session.getDefaultInstance(props);

        // 开启调试模式
        session.setDebug(true);
        try {
            // 第二步：获取邮件发送对象
            Transport transport = session.getTransport();
            // 连接邮件服务器，链接您的163、sina邮箱，用户名（不带@163.com，登录邮箱的邮箱账号，不是邮箱地址）、密码
            transport.connect(userName, userPwd);
            Address toAddress = new  InternetAddress(to);

            // 第三步：创建邮件消息体
            MimeMessage message = new MimeMessage(session);
            //设置自定义发件人昵称
            String nick="";
            try {
                nick=javax.mail.internet.MimeUtility.encodeText("网上超市邮件系统");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            message.setFrom(new InternetAddress(nick+" <"+from+">"));
            //设置发信人
            // message.setFrom(new InternetAddress(from));

            // 邮件的主题
            message.setSubject(subject);
            //收件人
            message.addRecipient(Message.RecipientType.TO, toAddress);
            /*//抄送人
            Address ccAddress = new InternetAddress("first.lady@whitehouse.gov");
            message.addRecipient(Message.RecipientType.CC, ccAddress);*/
            // 邮件的内容
            message.setContent(content, "text/html;charset=utf-8");
            // 邮件发送时间
            message.setSentDate(new Date());

            // 第四步：发送邮件
            // 第一个参数：邮件的消息体
            // 第二个参数：邮件的接收人，多个接收人用逗号隔开（test1@163.com,test2@sina.com）
            transport.sendMessage(message, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        SendMailUtil.send("1259301484@qq.com", "graduation问题报告", "您在配置文件的数据库中的商品表中缺少id为1的商品行", "smtp", "smtp.qq.com", "2573393471@qq.com", "25", "2573393471", "ccrlhrhtzctfdihg");
//
//    }

}