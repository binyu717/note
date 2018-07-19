package email;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author ming.jin
 * @Date 2018/6/26
 */
public class GetEmailUtil {

    public static void main(String[] args) {

        //Set mail properties and configure accordingly
//        String receivingServer = "imap.mxhichina.com";
//        String mailProtocol = "imap";
//        String email = "bin.yu@workbrother.com";
//        String password = "yubin150!!!";
//        String port = "143";
//        String isSsl = "false";

        String receivingServer = "pop.163.com";
        String mailProtocol = "pop3";
        String email = "yubin129@163.com";
        String password = "yubin100";
        String port = "110";
        // ssl连接 端口995,
        String isSsl = "false";
        // Calling checkMail method to check received emails
        checkMail(receivingServer, port,mailProtocol, email, password,isSsl);
    }

    private static InputStream checkMail(String receivingServer, String port,String mailProtocol, String email,String password,String isSsl)
    {
        InputStream inputStream = null;
        if ("imap".equals(mailProtocol)) {
            inputStream = processImapEmail(receivingServer, port, mailProtocol, email, password, isSsl);
        } else if ("pop3".equals(mailProtocol)) {
            inputStream = processPop3Email(receivingServer, port, mailProtocol, email, password, isSsl);
        }
        return inputStream;
    }

    private static InputStream processImapEmail(String receivingServer, String port,String mailProtocol, String email,String password,String isSsl){
        InputStream inputStream = null;
        Store storeObj;
        Session emailSessionObj;
        Properties propvals = new Properties();
        try {
            //Set property values
            propvals.put("mail.imap.host", receivingServer);
            propvals.put("mail.imap.port", port);
            propvals.put("mail.store.protocol", mailProtocol);
            propvals.setProperty("mail.imap.ssl.enable", isSsl);
            emailSessionObj = Session.getDefaultInstance(propvals);
            //Create POP3 store object and connect with the server
            storeObj = emailSessionObj.getStore("imap");
            storeObj.connect(receivingServer, email, password);
            //Create folder object and open it in read-only mode
            IMAPFolder emailFolderObj =  (IMAPFolder)storeObj.getFolder("INBOX");
            emailFolderObj.open(Folder.READ_ONLY);
            // 获取下一个uid值
            long uidNext = emailFolderObj.getUIDNext();
            System.out.println("uidNext:"+uidNext);
            // TODO: 2018/6/28 查找最大的已同步的最大UID
            long maxUid = 745;
            long count = uidNext - maxUid-1;
            System.out.println("count:"+count);
            //Fetch messages from the folder and print in a loop
            int messageCount = emailFolderObj.getMessageCount();
            System.out.println("邮件总数量："+ messageCount);
            Message[] messages = emailFolderObj.getMessages((int)(messageCount-count+1),messageCount);
            System.out.println("message count:"+messages.length);
            for (int i = messages.length-1; i >=0; i--) {

                Message indvidualmsg = messages[i];
                long uid = emailFolderObj.getUID(indvidualmsg);
                if (i == messages.length - 1) {
                    // TODO: 2018/6/28 新增到数据库
                }
                if (uid <= maxUid) {
                    break;
                }
                inputStream = getEmailInputStream(indvidualmsg);
            }
            emailFolderObj.close(false);
            storeObj.close();

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return inputStream;
    }



    private static InputStream processPop3Email(String receivingServer, String port,String mailProtocol, String email,String password,String isSsl){
        InputStream inputStream = null;
        Store storeObj;
        Session emailSessionObj;
        Properties propvals = new Properties();
        try {
            //Set property values
            propvals.put("mail.pop3.host", receivingServer);
            propvals.put("mail.pop3.port", port);
            propvals.put("mail.store.protocol", mailProtocol);
            propvals.setProperty("mail.pop3.ssl.enable", isSsl);
//          propvals.put("mail.pop3.starttls.enable", "true");

            emailSessionObj = Session.getDefaultInstance(propvals);
            //Create POP3 store object and connect with the server
            storeObj = emailSessionObj.getStore("pop3");
            storeObj.connect(receivingServer, email, password);
            //Create folder object and open it in read-only mode
            POP3Folder emailFolderObj =  (POP3Folder)storeObj.getFolder("INBOX");
            emailFolderObj.open(Folder.READ_ONLY);
            // TODO: 2018/6/28   获取已同步的所有uids
            List<String> uids = new ArrayList<>();
            Message[] messages = emailFolderObj.getMessages();
            System.out.println(messages.length);
//            ArrayList<Object> insertList = new ArrayList<>();
            for (int i = messages.length-1; i >=0; i--) {

                Message indvidualmsg = messages[i];
                String uid = emailFolderObj.getUID(indvidualmsg);
                if (uids.contains(uid)) {
                    // TODO: 2018/6/28 批量新增 insertList
                    break;
                } else {
                    // TODO: 2018/6/28  存放在insertList里面
                    break;
                }
//                inputStream = getEmailInputStream(indvidualmsg);
            }
            emailFolderObj.close(false);
            storeObj.close();

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return inputStream;
    }


    private static InputStream getEmailInputStream(Message indvidualmsg) {
        InputStream inputStream = null;
        try {
            if (indvidualmsg.isMimeType("multipart/*")){
                Multipart multiPart = (Multipart) indvidualmsg.getContent();
                for (int t = 0; t < multiPart.getCount(); t++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(t);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())
                            ||Part.INLINE.equalsIgnoreCase(part.getDisposition())) {
                        String filename = MimeUtility.decodeText(part.getFileName());
                        String suffix  = filename.substring(filename.lastIndexOf(".")+1);
                        if (suffix.equals("doc")||suffix.equals("pdf")||suffix.equals("docx")) {
                              inputStream = part.getInputStream();
                        }
                    }
                }
            }
        }catch (Exception exp) {
            exp.printStackTrace();
        }
        return inputStream;
    }
}
