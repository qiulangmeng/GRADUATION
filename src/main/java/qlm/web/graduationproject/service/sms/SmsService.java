package qlm.web.graduationproject.service.sms;

/**
 * @author qlm
 * @version 1.0 10:42 2020.4.6
 */
public interface SmsService {
    boolean sendSms(String iphoneNumber);
    boolean sendSms(String iphoneNumber,String code);
}
