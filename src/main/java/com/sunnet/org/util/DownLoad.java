package com.sunnet.org.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载工具类
 * 
 * @author 强强
 *
 *         时间: 2017年4月6日
 */
public class DownLoad {

	public static void zipFile(List<File> files, ZipOutputStream outputStream)
			throws IOException, ServletException {
		try {
			int size = files.size();
			// 压缩列表中的文件
			for (int i = 0; i < size; i++) {
				File file = (File) files.get(i);
				zipFile(file, outputStream);
			}
		} catch (IOException e) {
			// throw e;
		}
	}

	public static void zipFile(List<File> files, ZipOutputStream outputStream,
			File fileZip, HttpServletResponse response, boolean isDelete)
			throws IOException, ServletException {
		try {
			int size = files.size();
			// 压缩列表中的文件
			for (int i = 0; i < size; i++) {
				File file = (File) files.get(i);
				zipFile(file, outputStream);
			}
			downloadFile(fileZip, response, true);
		} catch (IOException e) {
			throw e;
		}
	}

	public static void zipFile(File inputFile, ZipOutputStream outputstream)
			throws IOException, ServletException {
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(
							inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);

					final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); // Closes the current ZIP entry
					// and positions the stream for
					// writing the next entry
					bInStream.close(); // 关闭
					inStream.close();
				}
			} else {
				throw new ServletException("文件不存在！");
			}
			System.out.println("压缩完成！"+ inputFile.getName());
			 if (inputFile.exists() && inputFile.isFile()) {
		            if (inputFile.delete()) {
		                System.out.println("删除单个文件" + inputFile.getName() + "成功！");
		            }
			 }
		} catch (IOException e) {
			throw e;
		}
	}

	public static void downloadFile(File file, HttpServletResponse response,
			boolean isDelete) {
		try {
			Long start = System.currentTimeMillis();
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			Long end = System.currentTimeMillis();
			System.out.println("复制用了" + (end - start) + "毫秒");
			// 清空response
			response.reset();
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename="
							+ new String(file.getName().getBytes("UTF-8"),
									"ISO-8859-1"));
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			if (isDelete) {
				file.delete(); // 是否将生成的服务器端文件删除
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Long start = System.currentTimeMillis();
//		DownLoad.downloadFile(new File("E:/image/upload/纪录-风光_1514106333289MQ64J0_9_向阳红_18999515100.jpg"),null,false);
		List<File> files = new ArrayList<File>();
		files.add(new File("E:/image/upload/纪录-风光_1514106333289MQ64J0_9_向阳红_18999515100.jpg"));
		File fileZip = new File("E:/image/upload/1.zip");
		FileOutputStream outStream = new FileOutputStream(fileZip);
		ZipOutputStream toClient = new ZipOutputStream(new FileOutputStream(fileZip));
		DownLoad.zipFile(files, toClient);
		toClient.close();
		outStream.close();
		Long end = System.currentTimeMillis();
		System.out.println("压缩"+files.get(0).length()+"用了" + (end - start) + "毫秒"); //4.27 M
//		(4484764/4) 得到1m的字节大小 (4484764/4) = 278/4  毫秒 
	}

}
