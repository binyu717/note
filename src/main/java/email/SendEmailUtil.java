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

}
