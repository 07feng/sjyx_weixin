package com.sunnet.org.api;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunnet.framework.controller.BaseController;
import com.sunnet.org.util.RandomUtil;
import com.sunnet.org.util.UserUtil;

@Controller
@RequestMapping(value = "/img")
public class ImgController extends BaseController
{
	

	@RequestMapping(value = "/captcha")
	public void image(String captchaId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		//宽度
		int width = 150;
		//高度
		int height = 50;
		//长度
		int size = 4;
//	 	int size = 0;
		//字体大小
		int fontSize = 28;
		//干扰线
		int line = 6;
		//点
		int confusion = 150;

		//创建一张图片
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//设置颜色
		g.setColor(new Color(0xFAFAFA));
		g.fillRect(0, 0, width, height);
		//画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		//创建随机字母
		Random rdm = new Random();
		String randCode = RandomUtil.getNoLength(4);
		System.out.println(randCode);  //
		String capstr = randCode.substring(0, size);
		//创建点
		for (int i = 0; i < confusion; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		//画干扰线
		for (int i = 0; i < line; i++) {
			g.drawLine(rdm.nextInt(width), rdm.nextInt(height),
					rdm.nextInt(width), rdm.nextInt(height));
		}
		//将生成的验证码存入session
		UserUtil.removeSessionCode(request.getSession());
		UserUtil.setSessionCode(request, capstr);
		g.setColor(new Color(109, 26, 152));
		g.setFont(new Font("Candara", Font.BOLD, fontSize));
		g.drawString(capstr, (width - fontSize / 2 * size) / 2, (height+fontSize/2)/2);
		g.dispose();
		//输出图片
		response.setContentType("image/jpeg");
		OutputStream strm = response.getOutputStream();
		ImageIO.write(image, "jpeg", strm);
		strm.close();
	}
	
}
