package email;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

import javax.mail.*;
import java.util.Properties;

/**
 * @Author ming.jin
 * @Date 2018/6/26
 */
public class GetEmailUtil {
    public static void main(String[] args) {
        String receivingServer = "";
        String mailProtocol = "";
        String email = "";
        String password = "";
        String port = "";
        String isSsl = "";
        // Calling checkMail method to check received emails
        checkMail(receivingServer, port,mailProtocol, email, password,isSsl);
    }

    public static void checkMail(String receivingServer, String port,String mailProtocol, String email,String password,String isSsl)
    {
        if ("imap".equals(mailProtocol)) {
            processImapEmail(receivingServer, port, mailProtocol, email, password, isSsl);
        } else if ("pop3".equals(mailProtocol)) {
            processPop3Email(receivingServer, port, mailProtocol, email, password, isSsl);
        }
    }

    private static void processImapEmail(String receivingServer, String port,
                                         String mailProtocol, String email,String password,String isSsl){
        Store storeObj;
        Session emailSessionObj;
        Properties propvals = new Properties();
        try {
            // 连接参数
            propvals.put("mail.imap.host", receivingServer);
            propvals.put("mail.imap.port", port);
            propvals.put("mail.store.protocol", mailProtocol);
            propvals.setProperty("mail.imap.ssl.enable", isSsl);
            emailSessionObj = Session.getDefaultInstance(propvals);
            // 获取对象，连接到服务器
            storeObj = emailSessionObj.getStore(mailProtocol);
            storeObj.connect(receivingServer, email, password);
            // 创建folder对象，打开方式有只读与读写
            IMAPFolder emailFolderObj =  (IMAPFolder)storeObj.getFolder("INBOX");
            emailFolderObj.open(Folder.READ_ONLY);
            // 获取下一个uid值，UID对于POP3是字符串类型，IMAP是数值类型且递增，
            long uidNext = emailFolderObj.getUIDNext();
            int messageCount = emailFolderObj.getMessageCount();
            // 邮箱邮件的总数量，不计入已删除的
            System.out.println("邮件总数量："+ messageCount);
            Message[] messages = emailFolderObj.getMessages();
            // FetchProfile类提供邮件协议提供者特有可选参数，其目的是更有效地实现邮件组成成分的预提取,可增加以下三种属性，
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.CONTENT_INFO);
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            fp.add("X-mailer");
            emailFolderObj.fetch(messages, fp);
            // 遍历messages时，提取其内容相当耗时，这时用FetchProfile预提取可以起到一定的效果
            for (int i = messages.length-1; i >=0; i--) {
                Message indvidualmsg = messages[i];
                long uid = emailFolderObj.getUID(indvidualmsg);
                indvidualmsg.getSubject();
                indvidualmsg.getSentDate();
                indvidualmsg.isMimeType("multipart/*");
                indvidualmsg.getContentType().contains("multipart");
                indvidualmsg.getContent();
            }
            emailFolderObj.close(false);
            storeObj.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private static void processPop3Email(String receivingServer, String port,
                                         String mailProtocol, String email,String password,String isSsl){
        Store storeObj;
        Session emailSessionObj;
        Properties propvals = new Properties();
        try {
            // 连接参数
            propvals.put("mail.pop3.host", receivingServer);
            propvals.put("mail.pop3.port", port);
            propvals.put("mail.store.protocol", mailProtocol);
            propvals.setProperty("mail.pop3.ssl.enable", isSsl);

            emailSessionObj = Session.getDefaultInstance(propvals);
            // 获取对象，连接到服务器
            storeObj = emailSessionObj.getStore(mailProtocol);
            storeObj.connect(receivingServer, email, password);
            // 创建folder对象，打开方式有只读与读写
            POP3Folder emailFolderObj =  (POP3Folder)storeObj.getFolder("INBOX");
            emailFolderObj.open(Folder.READ_ONLY);
            Message[] messages = emailFolderObj.getMessages();
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.CONTENT_INFO);
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            fp.add("X-mailer");
            emailFolderObj.fetch(messages, fp);
            for (int i = messages.length-1; i >=0; i--) {
                Message indvidualmsg = messages[i];
                 indvidualmsg.getSubject();
                indvidualmsg.getSentDate();
                indvidualmsg.isMimeType("multipart/*");
                indvidualmsg.getContentType().contains("multipart");
                indvidualmsg.getContent();
            }
            emailFolderObj.close(false);
            storeObj.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}