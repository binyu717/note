package email;

import org.apache.commons.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class SendEmailUtil {

    private static final Logger log = LoggerFactory.getLogger(SendEmailUtil.class);

    /**
     * 验证邮箱服务器是否可用.
     *
     * @param config the sender
     * @return the boolean
     */
    public static boolean validSMTPServer(EmailSMTPConfig config){
        try {
            HtmlEmail email = createEmail(config);
            email.getMailSession().getTransport().connect();
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
    }

    /**
     * 发送文本邮件.
     *
     * @param config  the config
     * @param to      the to
     * @param subject the subject
     * @param msg     the msg
     * @throws EmailException the email exception
     */
    public static void sendTextEmail(EmailSMTPConfig config,String to,String subject,String msg) throws EmailException {
        HtmlEmail email = createEmail(config);
        email.setSubject(subject);
        email.setMsg(msg);
        email.addTo(to);
        email.send();
    }

    /**
     * 发送html邮件.
     *
     * @param config  the config
     * @param to      the to
     * @param subject the subject
     * @param msg     the msg
     * @throws EmailException the email exception
     */
    public static void sendHtmlEmail(EmailSMTPConfig config,
                                     String to,String subject,String msg)
            throws EmailException {
        HtmlEmail email = createEmail(config);
        email.setSubject(subject);
        email.setHtmlMsg(msg);
        email.addTo(to);
        email.send();
    }

    /**
     * 发送带附件的html邮件.
     *
     * @param config         the config
     * @param to             the to
     * @param subject        the subject
     * @param msg            the msg 一般使用FreeMarkerTemplateUtils创建内容
     * @param attachments the attachments
     * @throws EmailException        the email exception
     * @throws MalformedURLException the malformed url exception
     */
    public static void sendHtmlEmailWithAttachment(EmailSMTPConfig config,
                                                   String to,String subject,String msg,
                                                   List<EmailAttachmentModel> attachments)
            throws EmailException, MalformedURLException {
        HtmlEmail email = createEmail(config);
        email.setSubject(subject);
        email.setHtmlMsg(msg);
        email.addTo(to);

        for(EmailAttachmentModel attachmentModel:attachments){
            EmailAttachment attachment = new EmailAttachment();
            attachment.setURL(new URL(attachmentModel.getUrl()));
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription(attachmentModel.getName());
            attachment.setName(attachmentModel.getName());

            email.attach(attachment);
        }

        email.send();
    }

    private static HtmlEmail createEmail(EmailSMTPConfig config) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(config.getSmtp());
        if (config.isSSL()){
            email.setSSLOnConnect(true);
            email.setSslSmtpPort(config.getPort() == null ? "465":config.getPort().toString());

        }else{
            email.setSmtpPort(config.getPort() == null ? 25:config.getPort());
        }
        email.setAuthentication(config.getUser(), config.getPassword());
        email.setFrom(config.getUser(),config.getUserName());
        email.setCharset("utf-8");
        return email;
    }

    public static void main(String[] args) {
        SimpleEmail email = new SimpleEmail();

        //通过Gmail Server 发送邮件
        email.setHostName("smtp.gmail.com"); //设定smtp服务器
        email.setSSL(Boolean.TRUE);          //设定是否使用SSL
        email.setSslSmtpPort("465");         //设定SSL端口
        email.setAuthentication("bin.yu@kuailework.com", "yuBin150"); //设定smtp服务器的认证资料信息

        try {
            email.addTo("reciever@gmail.com", "reciever"); //设定收件人
            email.setCharset("UTF-8");//设定内容的语言集
            email.setFrom("478494772@qq.com");//设定发件人
            email.setSubject("Hello");//设定主题
            email.setMsg("中国 ");//设定邮件内容
            email.send();//发送邮件

        } catch (Exception e) {
            new RuntimeException("bb");
        }
        System.out.println("aaaaaa");

    }

}
