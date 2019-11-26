package com.sunnet.code.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class CommonPageParser {

	private static VelocityEngine ve;
	// private static final String CONTENT_ENCODING = "UTF-8";
	private static final Log log = LogFactory.getLog(CommonPageParser.class);
	private static boolean isReplace = true;

	static {
		try {
			//模板路径 templateBasePath
			String templateBasePath = TestUtil.getProjectPath() + "/resources/code/template";
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "file");
			properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.path", templateBasePath);
			properties.setProperty("file.resource.loader.cache", "true");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
			properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
			properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
			properties.setProperty("directive.set.null.allowed", "true");
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init(properties);
			ve = velocityEngine;
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile) {

		File file;
		try {
			file = new File(fileDirPath + targetFile);
			System.out.println("生成路径：" + fileDirPath + targetFile);
			if (!(file.exists())) {
				new File(file.getParent()).mkdirs();
			} else if (isReplace) {
				log.info("替换文件:" + file.getAbsolutePath());
			}

			Template template = ve.getTemplate(templateName, "UTF-8");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			template.merge(context, writer);
			writer.flush();
			writer.close();
			fos.close();
			log.info("生成文件：" + file.getAbsolutePath());
		} catch (Exception e) {
			log.error(e);
			System.out.println(e);
		}
	}

	/**
	 * 自定义生成规则
	 * @param context 模板必须-》里面有指定参数已map的建值方式存储
	 * @param templateName 
	 * @param names
	 */
	public static void WriterTxt(VelocityContext context, String templateName, String names) {

		//生成 方式 解析：
		//其实就是替换 把模板里的值，替换成一个文本，自定义后缀
		
		File file;
		try {
			file = new File("C://port//" + names + ".txt");
			if (!(file.exists())) {
				new File(file.getParent()).mkdirs();
			} else if (isReplace) {
				log.info("替换文件:" + file.getAbsolutePath());
			}

			Template template = ve.getTemplate(templateName, "UTF-8");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			template.merge(context, writer);
			writer.flush();
			writer.close();
			fos.close();
			log.info("生成文件：" + file.getAbsolutePath());
		} catch (Exception e) {
			log.error(e);
			System.out.println(e);
		}
	}
	
	
	/**
	 * 自定义生成规则2 
	 * @param srcPaht 生成之后的路径
	 * @param context 参数值
	 * @param templateName 模板名称
	 * @param names 生成之后的名称
	 * @param suffix 后缀
	 */
	public static void Writer(String srcPaht,VelocityContext context, String templateName, String names,String suffix) {
		File file;
		try {
			// 生成路径 （绝对） + 生成名称+ 后缀
			file = new File(srcPaht+"//" + names + "."+suffix); 
			//如果文件夹不存在，自动先手新增一个文件夹
			if (!(file.exists())) {
				new File(file.getParent()).mkdirs();
			} else if (isReplace) {
				log.info("替换文件:" + file.getAbsolutePath());
			}
			//以下固定替换模板代码
			//velocity.jar 里自动封装好的内容
			Template template = ve.getTemplate(templateName, "UTF-8");
			//创建流
			FileOutputStream fos = new FileOutputStream(file);
			//相互替换
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			//模板里面的值替换 相当于把 ${XXX} 替换成 context里面的值
			template.merge(context, writer);
			writer.flush();
			writer.close();
			fos.close();
			log.info("生成文件：" + file.getAbsolutePath());
		} catch (Exception e) {
			log.error(e);
			System.out.println(e);
		}
	}
}