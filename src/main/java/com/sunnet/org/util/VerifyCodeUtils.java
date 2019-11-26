package com.sunnet.org.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 数字验证码
 * @author Administrator
 *
 */
public class VerifyCodeUtils {

	public String code = "";
	Random rand = new Random();

	/**
	 * 随机产生的加数和被加数
	 */
	/**
	 * 随机产生的计算方式,0表示加,1表示减
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VerifyCodeUtils code = new VerifyCodeUtils();
		JFrame jFrame = new JFrame();
		jFrame.setBounds(400, 400, 250, 250);
		// code.getVerificationCode2();
		ImageIcon img = new ImageIcon(code.getVerificationCode2());
		JLabel background = new JLabel(img);

		jFrame.add(background);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public BufferedImage getVerificationCode2() {
		int width = 110;
		int height = 50;
		int degree = 0;// 继续一共旋转的角度,方便最后的时候旋转回来
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景色
		Color background = getColor();
		g.setColor(background);
		g.fillRect(0, 0, width, height);
		// 画边框
		g.setColor(background);
		g.drawRect(0, 0, width - 1, height - 1);
		// 将认证码显示到图像中,如果要生成更多位的认证码
		char[] content = getDrawContent2();
		int[] xs = getRadonWidths(content.length);
		int[] ys = getRadomHeights(content.length);
		for (int i = 0; i < content.length; i++) {
			String s = content[i] + "";
			if (content[i] == '!')
				s = "";
			// 如果在画字之前旋转图片
			if (i != 2) {
				int maxDegree = rand.nextInt(2);
				if (maxDegree == 0)
					maxDegree = 0;
				else
					maxDegree = 305;
				degree = rand.nextInt(45) + maxDegree;
			} else
				degree = 0;
			g.setColor(new Color(255, 255, 255));
			if (i == 2)// 运算符号显示大一些
				g.setFont(new Font("Atlantic Inline", Font.PLAIN, 24));
			else
				g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
			RotateString(s, xs[i], ys[i], g, degree);
		}
		// 画干扰点
		CreateRandomPoint(width, height, 80, g);
		// 随机画几条线
		CreateRandomLine(width, height, 4, g);
		// 释放图形上下文
		g.dispose();
		return image;
	}

	/**
	 * 旋转并且画出指定字符串
	 * 
	 * @param s
	 *            需要旋转的字符串
	 * @param x
	 *            字符串的x坐标
	 * @param y
	 *            字符串的Y坐标
	 * @param g
	 *            画笔g
	 * @param degree
	 *            旋转的角度
	 */
	private void RotateString(String s, int x, int y, Graphics g, int degree) {
		Graphics2D g2d = (Graphics2D) g.create();
		// 平移原点到图形环境的中心 ,这个方法的作用实际上就是将字符串移动到某一个位置
		g2d.translate(x - 1, y + 3);
		// 旋转文本
		g2d.rotate(degree * Math.PI / 180);
		// 特别需要注意的是,这里的画笔已经具有了上次指定的一个位置,所以这里指定的其实是一个相对位置
		g2d.drawString(s, 0, 0);
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @param many
	 * @param g
	 */
	private void CreateRandomPoint(int width, int height, int many, Graphics g) { // 随机产生干扰点
		for (int i = 0; i < many; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			g.setColor(getColor());
			g.drawOval(x, y, 1, 1);
		}
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @param minMany
	 *            最少产生的数量
	 * @param g
	 */
	private void CreateRandomLine(int width, int height, int minMany, Graphics g) { // 随机产生干扰线条
		for (int i = 0; i < rand.nextInt(minMany) + 5; i++) {
			int x1 = rand.nextInt(width) % 15;
			int y1 = rand.nextInt(height);
			int x2 = (int) (rand.nextInt(width) % 40 + width * 0.7);
			int y2 = rand.nextInt(height);
			g.setColor(getColor());
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/***
	 * @return 随机返回一种颜色
	 */
	private Color getColor() {
		int R = (int) (Math.random() * 255);
		int G = (int) (Math.random() * 255);
		int B = (int) (Math.random() * 255);
		return new Color(R, G, B);
	}

	/**
	 * 
	 * @return 返回getVerificationCode2需要画出的内容:两位数加减法字符数组
	 */
	private char[] getDrawContent2() {
		code = RandomUtil.getNoLength(4);
		char[] temp = code.toCharArray();
		return temp;
	}

	/**
	 * 对图片选择,这里保留以方便以后使用
	 * 
	 * @param bufferedimage
	 * @param degree
	 * @return 一张旋转后的图片
	 */
	public BufferedImage rolateImage(BufferedImage bufferedimage, int degree, Color backGround) {
		BufferedImage img;
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		int type = BufferedImage.TYPE_INT_RGB;
		Graphics2D graphics2d;
		graphics2d = (img = new BufferedImage(w, h, type)).createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		graphics2d.drawImage(bufferedimage, null, null);
		return img;
	}

	/**
	 * 得到验证码getVerificationCode2,计算出来的结果
	 */
	public String getResult2() {
		return code;
	}

	/**
	 * 
	 * @param many
	 * @return 画图的时候随机的高度的数组
	 */
	private int[] getRadomHeights(int many) {
		int[] temp = new int[many];
		for (int i = 0; i < many; i++) {
			temp[i] = getRadomHeight();
		}
		return temp;
	}

	/**
	 * 
	 * @param many
	 * @return 画图的时候起始x坐标的数组
	 */
	private int[] getRadonWidths(int many) {
		int[] temp = new int[many];
		for (int i = 0; i < many; i++) {
			if (i == 0)
				temp[i] = getRadonWidth(0);
			else
				temp[i] = getRadonWidth(temp[i - 1]);
		}
		return temp;
	}

	private int getRadomHeight() {
		int fullHeight = 70;
		return (int) (Math.random() * fullHeight) % 35 + 15;
	}

	private int getRadonWidth(int minWidth) {
		int maxWidth = 150;
		int minJianju = maxWidth / 9;
		int maxJianju = maxWidth / 6;
		int temp = maxJianju - minJianju;
		// 在的规定的范围内产生一个随机数
		return (int) (Math.random() * temp) + minWidth + minJianju;
	}

}