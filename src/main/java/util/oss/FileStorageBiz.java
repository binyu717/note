package util.oss;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Author ming.jin
 * @Date 2017/8/2
 */
@Component
public class FileStorageBiz {

    @Value("#{propertiesReader['aliyun.oss.access.keyid']}")
    private String accessKeyId;
    @Value("#{propertiesReader['aliyun.oss.access.keysecret']}")
    private String accessKeySecret;
    @Value("#{propertiesReader['aliyun.oss.endpoint']}")
    private String endPoint;
    @Value("#{propertiesReader['aliyun.oss.bucketname']}")
    private String bucketName;
    @Value("#{propertiesReader['aliyun.oss.defaultavatar']}")
    private String defaultAvatar;
    @Value("#{propertiesReader['aliyun.oss.imageOssHost']}")
    private String imageOssHost;

 
    public String uploadFile(InputStream is,String extension) {
        OssUtil util = new OssUtil(accessKeyId,accessKeySecret,endPoint,bucketName,defaultAvatar,imageOssHost);
        String fileName = UUID.randomUUID().toString()+"."+extension;
        util.uploadFile(is,fileName);
        return imageOssHost+fileName;
    }

 
    public String uploadFileSetFileName(InputStream is, String extension, String fileName) {
        OssUtil util = new OssUtil(accessKeyId,accessKeySecret,endPoint,bucketName,defaultAvatar,imageOssHost);
        String upFileName = (StringUtils.isNotBlank(fileName)?(fileName+"_"):"")+UUID.randomUUID().toString()+"."+extension;
        util.uploadFile(is,upFileName);
        return imageOssHost+upFileName;
    }

 
    public void deleteFile(String fileName) {
        OssUtil util = new OssUtil(accessKeyId,accessKeySecret,endPoint,bucketName,defaultAvatar,imageOssHost);
        util.deleteFile(fileName);
    }

 
    public String uploadFileSpecifyName(InputStream is, String fileName) {
        OssUtil util = new OssUtil(accessKeyId,accessKeySecret,endPoint,bucketName,defaultAvatar,imageOssHost);
        util.uploadFile(is,fileName);
        return imageOssHost+fileName;
    }

 
    public void downloadFile(String fileName, OutputStream out) {
        OssUtil util = new OssUtil(accessKeyId,accessKeySecret,endPoint,bucketName,defaultAvatar,imageOssHost);
        InputStream in = util.getFile(fileName);
        try {
            if (in != null) {
                int len = 0;
                byte[] buf = new byte[2048];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }catch (Exception IOException){

        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

 
    public void deleteFileByFullUrl(String url) {
        OssUtil util = new OssUtil(accessKeyId,accessKeySecret,endPoint,bucketName,defaultAvatar,imageOssHost);
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.delete(0,imageOssHost.length());
        util.deleteFile(stringBuilder.toString());
    }
}
