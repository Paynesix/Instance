package com.spring.security.tools.email;

import com.spring.security.config.EmailConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class MailUtil {

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(EmailConfig.HOST);
        sender.setPort(EmailConfig.PORT);
        sender.setUsername(EmailConfig.USERNAME);
        sender.setPassword(EmailConfig.PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties props = new Properties();
        props.setProperty("mail.smtp.timeout", EmailConfig.TIME_OUT);
        props.setProperty("mail.smtp.auth", EmailConfig.AUTH);
        props.setProperty("mail.transport.protocol", EmailConfig.PROTOCOL);
        props.setProperty("mail.smtp.host", EmailConfig.HOST);
        sender.setJavaMailProperties(props);
        return sender;
    }

    public void sendMail(String to, Map<String,String> paramMap) throws Exception {
        // 1、连接邮件服务器的参数配置
        JavaMailSenderImpl MAIL_SENDER = this.createMailSender();
        // 2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(MAIL_SENDER.getJavaMailProperties());
        // 设置调试信息在控制台打印出来
        // session.setDebug(true);
        // 3、创建邮件的实例对象
        Message msg = getMimeMessage(session, to, paramMap);
        // 4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        // 设置发件人的账户名和密码
        transport.connect(EmailConfig.EMAIL_FORM, EmailConfig.PASSWORD);
        // 发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
        // 抄送人, 密送人
        transport.sendMessage(msg, msg.getAllRecipients());

        // 5、关闭邮件连接
        transport.close();
    }

    /**
     * 获得创建一封邮件的实例对象
     * 
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public MimeMessage getMimeMessage(Session session, String to, Map<String,String> paramMap)
            throws Exception {

        String path = MailUtil.class.getResource("/").getPath();

        // 1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        // 2.设置发件人地址
        msg.setFrom(new InternetAddress(EmailConfig.EMAIL_FORM));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送 MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));

        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 将图片数据添加到"节点"
        image.setDataHandler(new DataHandler(new FileDataSource(path + File.separator + "image" + File.separator
                + "hizpay_strand.jpg")));
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("hizpay_strand");
        // 5. 创建图片"节点"
        MimeBodyPart image2 = new MimeBodyPart();
        // 将图片数据添加到"节点"
        image2.setDataHandler(new DataHandler(new FileDataSource(path + "image" + File.separator + "hizpay_qr.jpg")));
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image2.setContentID("hizpay_qr");

        // 1. 链接1
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        if (!StringUtils.isBlank(paramMap.get("url"))){
            // 4.设置邮件主题
            msg.setSubject("Hizpay注册验证邮箱", "UTF-8");
            text.setContent(EmailTemplate.getEmailTemplate(paramMap.get("url")), "text/html;charset=UTF-8");
        }else {
            // 4.设置邮件主题
            msg.setSubject("Hizpay发送门店参数邮件", "UTF-8");
            text.setContent(ShopEmailTemplate.getEmailTemplate(paramMap), "text/html;charset=UTF-8");
        }
        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.addBodyPart(image2);
        mm.setSubType("related"); // 混合关系
        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        // 设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
