package util.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * zip工具
 * Created by zhili.yin on 2016/12/8.
 */
public class ZipUtils {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 创建ZIP文件
     *
     * @param sourcePath 文件或文件夹路径
     * @param zipPath    生成的zip文件存在路径（包括文件名）
     */
    public static void createZip(String sourcePath, String zipPath) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 写入ZIP
     *
     * @param file
     * @param parentPath
     * @param zos
     */
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                for (File f : files) {
                    writeZip(f, parentPath, zos);
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /***
     * 解压缩文件
     * @param zipFilePath
     * @param targetPath
     * @return 解压缩目录
     */
    public static String unzip(String zipFilePath, String targetPath) {
        OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;
        String directoryPath = null;
        try {
            zipFile = new ZipFile(zipFilePath, Charset.forName("GBK"));

            if (StringUtils.isBlank(targetPath)) {
                directoryPath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
            } else {
                directoryPath = targetPath + File.separator + zipFilePath.substring(zipFilePath.lastIndexOf("\\") + 1, zipFilePath.lastIndexOf("."));
            }
            Enumeration<?> entryEnum = zipFile.entries();
            if (null != entryEnum) {
                ZipEntry zipEntry = null;
                while (entryEnum.hasMoreElements()) {
                    zipEntry = (ZipEntry) entryEnum.nextElement();
                    File parentFile = new File(directoryPath);
                    if (!parentFile.exists()) {
                        parentFile.mkdir();
                    }
                    if (zipEntry.isDirectory()) {
                        continue;
                    }
                    if (zipEntry.getSize() > 0) {
                        String fileName = zipEntry.getName().substring(zipEntry.getName().lastIndexOf("/") + 1);
                        // 文件
                        File targetFile = new File(directoryPath + File.separator + fileName);
                        os = new BufferedOutputStream(new FileOutputStream(targetFile));
                        is = zipFile.getInputStream(zipEntry);
                        byte[] buffer = new byte[4096];
                        int readLen = 0;
                        while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
                            os.write(buffer, 0, readLen);
                            os.flush();
                        }
                        is.close();
                        os.close();
                    }

                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (null != zipFile) {
                    zipFile.close();
                    zipFile = null;
                }
                if (null != is) {
                    is.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件流出错", e);
            }
        }
        return directoryPath;
    }

    public static void main(String[] args) {
        System.out.println("结果：" + unzip("D:\\测试\\20151222ordercreate_110081855010-test.zip", null));
    }
}
