package org.eocencle.magnet.core.util;

import org.eocencle.magnet.core.mapping.InfoParam;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件通用类
 * @author: huan
 * @Date: 2020-03-21
 * @Description:
 */
public class EmailUtil {
    /**
     * 发送邮件
     * @Author huan
     * @Date 2020-03-21
     * @Param [content, params]
     * @Return void
     * @Exception
     * @Description
     **/
    public static void sendEmail(String content, StrictMap<InfoParam> params) {
        try {
            // 创建邮件配置
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", params.get(CoreTag.OUTPUT_EMAIL_HOST).getValue()); // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
            props.setProperty("mail.smtp.ssl.enable", "true");// 开启ssl
            // 根据邮件配置创建会话，注意session别导错包
            Session session = Session.getDefaultInstance(props);
            // 开启debug模式，可以看到更多详细的输入日志
            // session.setDebug(true);

            // --- 创建邮件 ---
            // 根据会话创建邮件
            MimeMessage msg = new MimeMessage(session);
            // address邮件地址, personal邮件昵称, charset编码方式
            InternetAddress fromAddress = new InternetAddress(params.get(CoreTag.OUTPUT_EMAIL_ACCOUT).getValue(), params.get(CoreTag.OUTPUT_EMAIL_PERSONAL).getValue(), "utf-8");
            // 设置发送邮件方
            msg.setFrom(fromAddress);
            // 单个可以直接这样创建
            // InternetAddress receiveAddress = new InternetAddress();
            // 设置邮件接收方
            Address[] internetAddressTo = new InternetAddress().parse(params.get(CoreTag.OUTPUT_EMAIL_USERS).getValue());
            //type:
            //要被设置为 TO, CC 或者 BCC，这里 CC 代表抄送、BCC 代表秘密抄送。举例：Message.RecipientType.TO
            msg.setRecipients(MimeMessage.RecipientType.TO,  internetAddressTo);
            // 设置邮件标题
            msg.setSubject(params.get(CoreTag.OUTPUT_EMAIL_SUBJECT).getValue(), "utf-8");
            msg.setText(content);
            // 设置显示的发件时间
            msg.setSentDate(new Date());
            // 保存设置
            msg.saveChanges();
            // --- 创建邮件 ---

            //创建邮件
            MimeMessage message = msg;   //将用户和内容传递过来
            //获取传输通道
            Transport transport = session.getTransport();
            transport.connect(params.get(CoreTag.OUTPUT_EMAIL_HOST).getValue(), params.get(CoreTag.OUTPUT_EMAIL_ACCOUT).getValue(), params.get(CoreTag.OUTPUT_EMAIL_PWD).getValue());
            //连接，并发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
