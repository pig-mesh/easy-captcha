package com.pig4cloud.captcha;

import com.googlecode.aviator.AviatorEvaluator;
import com.pig4cloud.captcha.base.ArithmeticCaptchaAbstract;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Description：最后一位计算时保证结果为正，避免负号输入 2位3位整数计算比较好,否则最后一位会数字会有点异常
 *
 * @author Li Haoran
 */
@Slf4j
public class SimpleArithmeticCaptcha extends ArithmeticCaptchaAbstract {

	public SimpleArithmeticCaptcha() {
	}

	public SimpleArithmeticCaptcha(int width, int height) {
		this();
		setWidth(width);
		setHeight(height);
	}

	public SimpleArithmeticCaptcha(int width, int height, int len) {
		this(width, height);
		setLen(len);
	}

	public SimpleArithmeticCaptcha(int width, int height, int len, Font font) {
		this(width, height, len);
		setFont(font);
	}

	public SimpleArithmeticCaptcha(int width, int height, int len, int font) throws IOException, FontFormatException {
		this(width, height, len);
		setFont(font, 32f);
	}

	/**
	 * 生成验证码
	 * @param out 输出流
	 * @return 是否成功
	 */
	@Override
	public boolean out(OutputStream out) {
		checkAlpha();
		return graphicsImage(getArithmeticString().toCharArray(), out);
	}

	@Override
	public String toBase64() {
		return toBase64("data:image/png;base64,");
	}

	@Override
	public String getContentType() {
		return "image/png";
	}

	/**
	 * 生成验证码图形
	 * @param strs 验证码
	 * @param out 输出流
	 * @return boolean
	 */
	private boolean graphicsImage(char[] strs, OutputStream out) {
		try {
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.getGraphics();
			// 填充背景
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, width, height);
			// 抗锯齿
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 画干扰圆
			drawOval(2, g2d);
			// 画字符串
			g2d.setFont(getFont());
			FontMetrics fontMetrics = g2d.getFontMetrics();
			int fW = width / strs.length; // 每一个字符所占的宽度
			int fSp = (fW - (int) fontMetrics.getStringBounds("8", g2d).getWidth()) / 2; // 字符的左右边距
			for (int i = 0; i < strs.length; i++) {
				g2d.setColor(color());
				int fY = height
						- ((height - (int) fontMetrics.getStringBounds(String.valueOf(strs[i]), g2d).getHeight()) >> 1); // 文字的纵坐标
				g2d.drawString(String.valueOf(strs[i]), i * fW + fSp + 3, fY - 3);
			}
			g2d.dispose();
			ImageIO.write(bi, "png", out);
			out.flush();
			return true;
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		finally {
			try {
				out.close();
			}
			catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return false;
	}

	/**
	 * 生成随机验证码 不包含乘法、没有负数
	 * @return 验证码字符数组
	 */
	@Override
	protected char[] alphas() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len - 1; i++) {
			sb.append(num(difficulty));
			if (i < len - 1) {
				int type = num(1, 3);
				if (type == 1) {
					sb.append("+");
				}
				else if (type == 2) {
					sb.append("-");
				}
			}
		}
		chars =  String.valueOf(AviatorEvaluator.execute(sb.toString()));

		int parseInt = Integer.parseInt(chars);
		if (parseInt < 0) {
			sb.append("+");
			sb.append(num(-parseInt, -parseInt + 11));
		}
		else {
			int type = num(1, 3);
			if (type == 1) {
				sb.append("+");
				sb.append(num(difficulty));
			}
			else if (type == 2) {
				sb.append("-");
				sb.append(num(0, parseInt + 1));
			}
		}
		chars = String.valueOf(AviatorEvaluator.execute(sb.toString()));
		sb.append("=?");
		setArithmeticString(sb.toString());
		return chars.toCharArray();
	}

}
