package qlm.web.graduationproject.io;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qlm
 * @version 1.0 14:15 2020.3.20
 */
public class Message {
    private String title;
    private String content;
    private String extraInfo;
    private String attention;

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    private String jugde;

    public String getJugde() {
        return jugde;
    }

    public void setJugde(String jugde) {
        this.jugde = jugde;
    }

    public Message(String title, String content, String extraInfo) {
        this.title = title;
        this.content = content;
        this.extraInfo = extraInfo;
    }

    public Message() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
    public static Message registMessage(String content, HttpServletRequest request){
        return new Message("注册",content,request.getParameter("mobile"));
    }
}
