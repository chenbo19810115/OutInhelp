package com.loiuschen.help.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.loiuschen.help.util.MailConfigReader;

@Service
public class MailService {
	private static final String HOST = MailConfigReader.host;
    private static final Integer PORT = MailConfigReader.port;
    private static final String USERNAME = MailConfigReader.userName;
    private static final String PASSWORD = MailConfigReader.passWord;
    private static final String emailForm = MailConfigReader.emailForm;
//    private static final String timeout = MailConfigReader.timeout;
    private static final String personal = MailConfigReader.personal;
    private static JavaMailSenderImpl mailSender = createMailSender();

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
	private static JavaMailSenderImpl createMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("UTF-8");
        
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.port", Integer.toString(PORT));//设置端口  
        p.setProperty("mail.smtp.socketFactory.port", Integer.toString(PORT));//设置ssl端口  
        p.setProperty("mail.smtp.socketFactory.fallback", "false");  
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param to 接受人
     * @param subject 主题
     * @param html 发送内容
     * @throws MessagingException 异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendMail(String to, String subject, String html) throws MessagingException,UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(emailForm, personal);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }
}
