package file;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class FileUtils {
	// 资源束
	private ResourceBundle fileDirBundle;
	private String fileName;
	private static FileUtils fileUtils = null;
	
	private FileUtils(String fileName){
		this.fileName = fileName;
		this.fileDirBundle = ResourceBundle.getBundle(fileName);
	}
	
	public static synchronized FileUtils getInstance(String fileName){
		if(fileUtils == null){
			fileUtils = new FileUtils(fileName);
		}
		return fileUtils;
	}
	
	/**
	 * 根据key获取资源文件值
	 * @param key
	 * @return
	 */
	public String getResourceBundle(String key) {
		String bundleVale = fileDirBundle.getString(key);
		if (StringUtils.isBlank(bundleVale)) {	// 为空时重新加载资源文件
			fileDirBundle = ResourceBundle.getBundle(fileName);
			bundleVale = fileDirBundle.getString(key);
		}
		return bundleVale;
	}

	/**
	 * 创建文件，如果这个文件存在，直接返回这个文件
	 * 
	 * @param fullFilePath
	 *            文件的全路径，使用POSIX风格
	 * @return 文件，若路径为null，返回null
	 * @throws IOException
	 */
	public static File touch(String fullFilePath) throws IOException {
		if (fullFilePath == null) {
			return null;
		}
		File file = new File(fullFilePath);

		file.getParentFile().mkdirs();
		if (!file.exists())
			file.createNewFile();
		return file;
	}

	/**
	 * 将String写入文件，追加模式
	 * 
	 * @param content
	 *            写入的内容
	 * @param path
	 *            文件路径
	 * @param charset
	 *            字符集
	 * @throws IOException
	 */
	public static void appendString(String content, String path, String charset) throws IOException {
		PrintWriter writer = null;
		try {
			writer = getPrintWriter(path, charset, true);
			writer.print(content);
		} finally {
			close(writer);
		}
	}

	/**
	 * 将列表写入文件，追加模式
	 * 
	 * @param list
	 *            列表
	 * @param path
	 *            绝对路径
	 * @param charset
	 *            字符集
	 * @throws IOException
	 */
	public static <T> void appendLines(String content, String path, String charset) throws IOException {
		List<String> contents = new ArrayList<String>();
		contents.add(content);
		writeLines(contents, path, charset, true);
	}

	/**
	 * 将列表写入文件，追加模式
	 * 
	 * @param list
	 *            列表
	 * @param path
	 *            绝对路径
	 * @param charset
	 *            字符集
	 * @throws IOException
	 */
	public static <T> void appendLines(Collection<T> list, String path, String charset) throws IOException {
		writeLines(list, path, charset, true);
	}

	/**
	 * 将列表写入文件
	 * 
	 * @param list
	 *            列表
	 * @param path
	 *            绝对路径
	 * @param charset
	 *            字符集
	 * @param isAppend
	 *            是否追加
	 * @throws IOException
	 */
	public static <T> void writeLines(Collection<T> list, String path, String charset, boolean isAppend)
			throws IOException {
		PrintWriter writer = null;
		try {
			writer = getPrintWriter(path, charset, isAppend);
			for (T t : list) {
				if (t != null) {
					writer.println(t.toString());
				}
			}
		} finally {
			close(writer);
		}
	}

	/**
	 * 获得一个打印写入对象，可以有print
	 * 
	 * @param path
	 *            输出路径，绝对路径
	 * @param charset
	 *            字符集
	 * @param isAppend
	 *            是否追加
	 * @return 打印对象
	 * @throws IOException
	 */
	public static PrintWriter getPrintWriter(String path, String charset, boolean isAppend) throws IOException {
		return new PrintWriter(getBufferedWriter(path, charset, isAppend));
	}

	/**
	 * 获得一个带缓存的写入对象
	 * 
	 * @param path
	 *            输出路径，绝对路径
	 * @param charset
	 *            字符集
	 * @param isAppend
	 *            是否追加
	 * @return BufferedReader对象
	 * @throws IOException
	 */
	public static BufferedWriter getBufferedWriter(String path, String charset, boolean isAppend) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(touch(path), isAppend), charset));
	}

	/**
	 * 关闭
	 * 
	 * @param closeable
	 *            被关闭的对象
	 */
	public static void close(Closeable closeable) {
		if (closeable == null)
			return;
		try {
			closeable.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 写入数据到文件
	 * 
	 * @param dest
	 *            目标文件
	 * @param data
	 *            数据
	 * @param off
	 * @param len
	 * @param append
	 * @throws IOException
	 */
	public static void writeBytes(File dest, byte[] data, int off, int len, boolean append) throws IOException {
		if (dest.exists() == true) {
			if (dest.isFile() == false) {
				throw new IOException("Not a file: " + dest);
			}
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(dest, append);
			out.write(data, off, len);
		} finally {
			close(out);
		}
	}

	/**
	 * 从流中读取内容
	 *
	 * @param in
	 *            输入流
	 * @param charset
	 *            字符集
	 * @param collection
	 *            返回集合
	 * @return 内容
	 * @throws IOException
	 */
	public static <T extends Collection<String>> T getLines(InputStream in, String charset, T collection)
			throws IOException {
		// 从返回的内容中读取所需内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line = null;
		while ((line = reader.readLine()) != null) {
			collection.add(line);
		}

		return collection;
	}

	/**
	 * 删除目录及目录下的文件
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

    /**
     * 从URL读取图片转换为Base64字符串
     *
     * @param imageURL
     * @return
     */
    public static String readImageURLAsBase64(String imageURL) {
        URL url;
        InputStream in;
        HttpURLConnection httpUrl;
        byte[] data;
        try {
            url = new URL(imageURL);
            httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.setConnectTimeout(10000);
            httpUrl.connect();
            httpUrl.getInputStream();
            in = httpUrl.getInputStream();
            data = new byte[in.available()];
            in.read(data);
            in.close();
            Base64 base64 = new Base64();
            return base64.encodeAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
