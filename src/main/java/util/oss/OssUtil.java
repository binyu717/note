package util.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 阿里云OSS工具类
 *
 * @Author ming.jin
 * @Date 2017/8/2
 */
public class OssUtil {
    static final Logger log = LoggerFactory.getLogger(OssUtil.class);

    private String accessKeyId;
    private String accessKeySecret;
    private String endPoint;
    private String bucketName;
    private Long defaultLimitSize;
    private static String defaultAvatar;
    private static String imageOssHost;

    private OSSClient ossClient;


    public OssUtil() {
    }

    public OssUtil(String accessKeyId, String accessKeySecret, String endPoint, String bucketName,
                   String defaultAvatar, String imageOssHost) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.endPoint = endPoint;
        this.bucketName = bucketName;
        OssUtil.defaultAvatar = defaultAvatar;
        OssUtil.imageOssHost = imageOssHost;
        ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        this.defaultLimitSize = 30L;
    }

    public OssUtil(String accessKeyId, String accessKeySecret, String endPoint, String bucketName,
                   String defaultAvatar, String imageOssHost, Long defaultLimitSize) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.endPoint = endPoint;
        this.bucketName = bucketName;
        OssUtil.defaultAvatar = defaultAvatar;
        OssUtil.imageOssHost = imageOssHost;
        this.defaultLimitSize = defaultLimitSize;
        ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }

    public static String imageOssHost() {
        return imageOssHost;
    }

    /**
     * 上传文件
     *
     * @param resouce  文件输入流
     * @param fileName 文件名(相对路径，不能以/开头)。名称在使用UTF-8编码后长度必须在 1-1023字节之间，而且不能包含回车、换行、以及xml1.0不支持的字符，同时也不能以“/”或者“\”开头。
     */
    public boolean uploadFile(InputStream resouce, String fileName) {
        ObjectMetadata meta = new ObjectMetadata();
        try {
            meta.setContentLength(resouce.available());
            PutObjectResult result = ossClient.putObject(bucketName, fileName, resouce, meta);
            log.debug(result.getETag());
            return true;
        } catch (Exception e) {
            log.error("上传文件到OSS失败", e);
            return false;
        }
    }

    /**
     * 遍历prefix指定的前缀下的所有的key，按照时间倒叙排序
     *
     * @param prefix:前缀，通常指阿里云服务器上的文件目录
     * @return
     */
    public List<String> listKeys(String prefix) {
        if (StringUtils.isEmpty(prefix))
            return null;
        List<String> keys = new ArrayList<>();
        ObjectListing objectListing = this.ossClient.listObjects(bucketName, prefix);
        List<OSSObjectSummary> list = objectListing.getObjectSummaries();

        Collections.sort(list, new Comparator<OSSObjectSummary>() {

            @Override
            public int compare(OSSObjectSummary o1, OSSObjectSummary o2) {
                return o2.getLastModified().compareTo(o1.getLastModified());
            }

        });
        for (OSSObjectSummary summary : list) {
            keys.add(summary.getKey());
        }

        return keys;
    }

    public boolean doesFileExist(String ossName) {
        try {
            OSSObject obj = ossClient.getObject(bucketName, ossName);
            if (obj == null) {
                return false;
            } else {
                try {
                    obj.getObjectContent().close();
                } catch (IOException e) {
                    //关闭异常，不处理
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean moveFile(String fromOssName, String toOssName) {
        try {
            ossClient.copyObject(bucketName, fromOssName, bucketName, toOssName);
            ossClient.deleteObject(bucketName, fromOssName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 在阿里云上跨bucket复制文件
     *
     * @param fromOssName
     * @param toOssName
     * @return
     */
    public boolean copyFile(String fromOssName,String fromBucketName, String toOssName, String toBucketName) {
        try {
            ossClient.copyObject(fromBucketName, fromOssName, toBucketName, toOssName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 在阿里云上复制图片
     *
     * @param fromOssName
     * @param toOssName
     * @return
     */
    public boolean copyFile(String fromOssName, String toOssName) {
        try {
            ossClient.copyObject(bucketName, fromOssName, bucketName, toOssName);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 从oss上删除文件
     *
     * @param fileName
     * @return
     */
    public void deleteFile(String fileName) {
        ossClient.deleteObject(bucketName, fileName);
    }

    /**
     * 根据文件名获取文件详细信息
     *
     * @param ossName
     * @return
     */
    public ObjectMetadata getFileMeta(String ossName) {
        ObjectMetadata obj = ossClient.getObjectMetadata(bucketName, ossName);
        if (obj == null) {
            return null;
        }
        return obj;
    }

    /**
     * 根据文件名获取文件输入流（使用完成后请关闭输入流）
     *
     * @param fileName
     * @return
     */
    public InputStream getFile(String fileName) {
        log.info("--------------------------------------fileName----------------------------------fileName----------------------------"+fileName);
        OSSObject obj = ossClient.getObject(bucketName, fileName);
        if (obj == null) {
            return null;
        }
        InputStream result = obj.getObjectContent();
        return result;
    }
    public String getBucketName(){
        return bucketName;
    }
    public void setBucketName(String bucketName){
        this.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String  getEndPoint() {
        return endPoint;
    }

    public void  setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public static String getDefaultAvatar() {
        return defaultAvatar;
    }

    public void setDefaultAvatar(String defaultAvatar) {
        OssUtil.defaultAvatar = defaultAvatar;
    }

    public static String getImageOssHost() {
        return imageOssHost;
    }

    public void setImageOssHost(String imageOssHost) {
        OssUtil.imageOssHost = imageOssHost;
    }

    /**
     * 加密生成OSS文件路径
     *
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encodeFileName(String fileName) throws UnsupportedEncodingException {
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
//		String encodeName = new sun.misc.BASE64Encoder().encode(name.getBytes("UTF-8"));
//		// mime协议的规定:base64.encodestring返回的字符串默认结尾带"\n"，
//		// 而且产生的base64编码字符串每76个字符就会用"\n"隔开，所以最安全的方法是用replace去掉其中所有的\n。且不影响decde
//		encodeName = encodeName.replaceAll("\n", "");
        String encodeName = URLEncoder.encode(name, "UTF-8");
        // 为了防止同一家公司两个HR同事对人才上传了同名的附件，导致人才简历附件串掉，采用原始文件名拼接上当前时间拼3位随机数
        encodeName = encodeName + "_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);
        log.debug("文件[" + fileName + "]转换后的文件名:" + encodeName + postfix);
        return encodeName + postfix;
    }

    /**
     * 解密OSS文件路径, 返回文件原名
     *
     * @return
     * @throws Exception
     */
    public String decodeFileName(String filePath) throws Exception {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        String name = filePath.substring(0, filePath.lastIndexOf("."));
        String postfix = filePath.substring(filePath.lastIndexOf("."), filePath.length());
        // 去除首部模块
        String decodeName = name.substring(name.indexOf("/") + 1, name.length());
        // 去除首部人才ID
        decodeName = decodeName.substring(decodeName.indexOf("/") + 1, decodeName.length());
        // 去除尾部随机数
        decodeName = decodeName.substring(0, decodeName.lastIndexOf("_"));
        // 取出尾部时间戳
        decodeName = decodeName.substring(0, decodeName.lastIndexOf("_"));
        // base64转码
//		decodeName = new String(new sun.misc.BASE64Decoder().decodeBuffer(decodeName), "UTF-8");
        // urlencode转码
        decodeName = URLDecoder.decode(decodeName, "UTF-8");
        log.debug("文件地址[" + filePath + "]转换得到的原始文件名:" + decodeName + postfix);
        return decodeName + postfix;
    }

    public boolean isImage(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                || fileName.endsWith(".png") || fileName.endsWith(".bmp");
    }

    public String convertFileSize(int bytes) {
        if (bytes < 0) {
            return "0B";
        }
        if (bytes < 1024) {
            return bytes + "B";
        }
        if (bytes < 1024 * 1024) {
            BigDecimal decimal = new BigDecimal(bytes / 1024d);
            decimal = decimal.setScale(1, BigDecimal.ROUND_HALF_UP);
            return decimal.toString() + "K";
        }
        if (bytes < 1024 * 1024 * 1024) {
            BigDecimal decimal = new BigDecimal(bytes / (1024d * 1024d));
            decimal = decimal.setScale(1, BigDecimal.ROUND_HALF_UP);
            return decimal.toString() + "M";
        }
        if (bytes < 1024 * 1024 * 1024 * 1024) {
            BigDecimal decimal = new BigDecimal(bytes / (1024d * 1024d * 1024d));
            decimal = decimal.setScale(1, BigDecimal.ROUND_HALF_UP);
            return decimal.toString() + "G";
        }
        if (bytes < 1024 * 1024 * 1024 * 1024 * 1024) {
            BigDecimal decimal = new BigDecimal(bytes / (1024d * 1024d * 1024d * 1024d));
            decimal = decimal.setScale(1, BigDecimal.ROUND_HALF_UP);
            return decimal.toString() + "LunarCalendar";
        }
        return "unLimited";
    }

    @Override
    public String toString() {
        return "OssUtil [accessKeyId=" + accessKeyId + ", accessKeySecret="
                + accessKeySecret + ", endPoint=" + endPoint + ", bucketName="
                + bucketName + "]";
    }

    public Long getDefaultLimitSize() {
        return defaultLimitSize;
    }

    public void setDefaultLimitSize(Long defaultLimitSize) {
        this.defaultLimitSize = defaultLimitSize;
    }
}
