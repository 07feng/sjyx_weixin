package com.sunnet.org.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载
 * <p>
 * Title: Download
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 强强
 * @date 2017年6月14日 下午1:52:59
 */
public class DownloadFile {

	/**
	 * 抓取网上的图片 [延伸]居然都支持下载网页了
	 * 
	 * @param imgSrc
	 * @param filePath
	 */
	public static void downloadImgByNet(String imgSrc, String filePath,
			String fileName) {
		try {
			URL url = new URL(imgSrc);
			URLConnection conn = url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 输出流
			InputStream str = conn.getInputStream();

			// 控制流的大小为1k
			byte[] bs = new byte[1024];

			// 读取到的长度
			int len = 0;

			// 是否需要创建文件夹
			File saveDir = new File(filePath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			File file = new File(saveDir + File.separator + fileName);

			// 实例输出一个对象
			FileOutputStream out = new FileOutputStream(file);
			// 循环判断，如果读取的个数b为空了，则is.read()方法返回-1，具体请参考InputStream的read();
			while ((len = str.read(bs)) != -1) {
				// 将对象写入到对应的文件中
				out.write(bs, 0, len);
			}

			// 刷新流
			out.flush();
			// 关闭流
			out.close();
			str.close();

			System.out.println("下载成功->"+fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static File downloadImgBy(String imgSrc, String filePath,
			String fileName) {
		File file = null;
		try {
			URL url = new URL(imgSrc);
			URLConnection conn = url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 输出流
			InputStream str = conn.getInputStream();
			
			// 控制流的大小为1k
			byte[] bs = new byte[1024];
			
			// 读取到的长度
			int len = 0;
			
			// 是否需要创建文件夹
			File saveDir = new File(filePath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			file = new File(saveDir + File.separator + fileName);
			
			// 实例输出一个对象
			FileOutputStream out = new FileOutputStream(file);
			// 循环判断，如果读取的个数b为空了，则is.read()方法返回-1，具体请参考InputStream的read();
			while ((len = str.read(bs)) != -1) {
				// 将对象写入到对应的文件中
				out.write(bs, 0, len);
			}
			
			// 刷新流
			out.flush();
			// 关闭流
			out.close();
			str.close();
			
			System.out.println("下载成功->"+fileName);
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 替换
	 * 
	 * @param from
	 * @param to
	 * @param source
	 * @return
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer bf = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			bf.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		bf.append(source);
		return bf.toString();
	}

	public static String replaceAll(String sb, String oldStr, String newStr) {
		StringBuffer str = new StringBuffer();
		str.append(sb);
		int i = sb.indexOf(oldStr);
		int oldLen = oldStr.length();
		int newLen = newStr.length();
		while (i > -1) {
			str.delete(i, i + oldLen);
			str.insert(i, newStr);
			i = sb.indexOf(oldStr, i + newLen);
		}
		return str.toString();
	}

	public static String getlast(String as) {
		return as.substring(as.lastIndexOf("."), as.length());
	}

	/**
	 * 特定规则截取路径
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> getConten(String content) {
		List<String> list = new ArrayList<String>();
		String[] a = content.split("src='");
		if (a.length > 1) {
			for (int i = 0; i < a.length; i++) {
				if (i > 0) {
					String string = a[i];
					String s = string.substring(0, string.indexOf("'/"));
					list.add(s);
				}
			}
		}
		return list;
	}

	/**
	 * 删除空目录
	 * 
	 * @param dir
	 *            将要删除的目录路径
	 */
	public static void doDeleteEmptyDir(String dir) {
		boolean success = (new File(dir)).delete();
		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
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
        // 目录此时为空，可以删除
        return dir.delete();
    }

	public static void main(String[] args) {
		// 下载图片
		downloadImgByNet(
				"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=594559231,2167829292&fm=27&gp=0.jpg",
				"D:/", "缅甸.jpg");

		// 下载网页
		// downloadImgByNet("http://manyou.189.cn/country/country.do?idCode=md276","d:/","缅甸.html");
	}

}
