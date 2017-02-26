package chestnut.utilsX;

import android.util.Log;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;


/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2017年2月26日
 *     desc  : 发送邮件相关
 *     thanks To :
 *     dependent on:
 * </pre>
 */

public class EmailUtils {

    /**
     *  发送邮件：阻塞
     *  因为是需要访问网络，所以要在子线程中调用。
     *
     * @param host                  SMTP 服务器 ， 例如：smtp.163.com
     * @param port                  SMTP 服务器的端口
     * @param account               你的账号
     * @param authorizationCode     授权码
     * @param to                    发给谁，填的是邮箱地址，例如，你的qq邮箱
     * @param subject               邮件的主题
     * @param content               邮件的内容
     * @return  是否发送成功。
     */
    public static boolean sendEmail(String host, String port, final String account, final String authorizationCode, String to, String subject, String content) {
        try {
            Multipart multiPart;
            String finalString = "";
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", account);
            props.put("mail.smtp.password", authorizationCode);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            Log.i("Check", "done pops");
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication () {
                    return new javax.mail.PasswordAuthentication(account, authorizationCode);
                }
            });
            DataHandler handler = new DataHandler(new ByteArrayDataSource(finalString.getBytes(), "text/plain"));
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setDataHandler(handler);
            Log.i("Check", "done sessions");
            multiPart = new MimeMultipart();
            InternetAddress toAddress;
            toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            Log.i("Check", "added recipient");
            message.setSubject(subject);
            message.setContent(multiPart);
            message.setText(content);
            Log.i("check", "transport");
            Transport transport = session.getTransport("smtp");
            Log.i("check", "connecting");
            transport.connect(host, account, authorizationCode);
            Log.i("check", "wana send");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            Log.i("check", "sent");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*

                String host = "smtp.163.com";
                String address = "xxxx@163.com";
                String from = address;
                String psw = "xxxx";
                String to = "xxxx@qq.com";
                String port = "25";

                Observable.just(from)
                        .observeOn(Schedulers.newThread())
                        .subscribe(s -> {
                            try {
                                SendEmail(host,port,address,psw,to,"android测试","这是内容");
                            } catch (Exception e) {
                                e.printStackTrace();
                                LogUtils.e(OpenLog,TAG,"error:"+e.getMessage());
                            }
                        });

    */
}
