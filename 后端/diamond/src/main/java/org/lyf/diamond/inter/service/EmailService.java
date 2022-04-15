package org.lyf.diamond.inter.service;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @program: diamon
 * @description: 邮件服务
 * @author: GG-lyf
 * @create: 2022-02-10 14:39:17
 */
@SuppressWarnings("all")
@Service
public class EmailService {
//  public static void main(String[] args){
//    String qq = "2083298549@qq.com";
//    String title = "sxsxs";
//    String context = "菜市场啥菜市场撒菜市场所错错错错错错错错错错错错错";
//    sendEmail(qq, title, context);
//  }

  public int sendEmail(String qq, String title, String context) {
    try {
      Properties pp = new Properties();
      pp.setProperty("mail.host", "smtp.qq.com");// 设置QQ邮件服务器
      pp.setProperty("mail.transport.protocol", "smtp");// 邮件发送协议
      pp.setProperty("mail.smpt.auth", "true");// 需要验证用户密码
      pp.put("mail.smtp.port", 465);// 端口号
      // 关于qq邮箱,还要设置ssl加密,
      pp.put("mail.smtp.ssl.enable", "true");
      MailSSLSocketFactory msf = new MailSSLSocketFactory();
      msf.setTrustAllHosts(true);
      pp.put("mail.smtp.ssl.socketFactory", msf);
      // 使用javamail发送邮件的步骤
      // 创建定义整个应用程序所需要的环境信息的Session对象
      Session session = Session.getDefaultInstance(pp, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(qq, "xgnyvukpzgwsbagi");
        }
      });
      // 开启session的debug模式,这样可以查看程序发送email的运行状态
      session.setDebug(true);
      // 2.通过session得到transport对象
      Transport t = session.getTransport();
      // 3.使用邮箱的用户名和授权码来连上邮件服务器
      t.connect("smtp.qq.com", qq, "xgnyvukpzgwsbagi");
      // 4.创建邮件
      // 创建邮件对象
      Message mimeMessage = new MimeMessage(session);
      // 指明邮件的发送人
      mimeMessage.setFrom(new InternetAddress(qq));
      // 指明邮件的接收人,(一个)
      mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("zefegg@163.com"));
      // 邮件的标题
      mimeMessage.setSubject(title);
      // 邮件的文本内容
      mimeMessage.setText(context);
      // 邮件的附件内容
      // mimeMessage.setContent("第一次啊,有点小激动","text/hmtl;charset=UTF-8");
      // 5.发送邮件
      t.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
      t.close();
      return 1;
    } catch (Exception e) {
      e.getStackTrace();
    }
    return 0;
  }
}
