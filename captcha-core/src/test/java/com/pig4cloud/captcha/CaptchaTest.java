package com.pig4cloud.captcha;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * 测试类 Created by 王帆 on 2018-07-27 上午 10:08.
 */
@Slf4j
public class CaptchaTest {

	@Test
	public void test() throws Exception {
		for (int i = 0; i < 1; i++) {
			SpecCaptcha specCaptcha = new SpecCaptcha();
			specCaptcha.setLen(4);
			specCaptcha.setFont(i, 32f);
			log.info(specCaptcha.text());
			specCaptcha.out(new FileOutputStream(getPath(+i + "1.png")));
		}
	}

	@Test
	public void testGIf() throws Exception {
		for (int i = 0; i < 1; i++) {
			GifCaptcha gifCaptcha = new GifCaptcha();
			gifCaptcha.setLen(5);
			gifCaptcha.setFont(i, 32f);
			log.info(gifCaptcha.text());
			gifCaptcha.out(new FileOutputStream(getPath(+i + "2.gif")));
		}
	}

	@Test
	public void testHan() throws Exception {
		for (int i = 0; i < 1; i++) {
			ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
			log.info(chineseCaptcha.text());
			chineseCaptcha.out(new FileOutputStream(getPath(+i + "3.png")));
		}
	}

	@Test
	public void testGifHan() throws Exception {
		for (int i = 0; i < 1; i++) {
			ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha();
			log.info(chineseGifCaptcha.text());
			chineseGifCaptcha.out(new FileOutputStream(getPath(+i + "4.gif")));
		}
	}

	@Test
	public void testArit() throws Exception {
		for (int i = 0; i < 1; i++) {
			ArithmeticCaptcha specCaptcha = new ArithmeticCaptcha();
			specCaptcha.setLen(3);
			specCaptcha.supportAlgorithmSign(2);
			specCaptcha.setDifficulty(50);
			specCaptcha.setFont(i, 28f);
			log.info(specCaptcha.getArithmeticString() + " " + specCaptcha.text());
			specCaptcha.out(new FileOutputStream(getPath(+i + "5.png")));
		}
	}

	@Test
	public void testBase64() throws Exception {
		GifCaptcha specCaptcha = new GifCaptcha();
		log.info(specCaptcha.toBase64(""));
	}

	private static String getPath(String name) {
		URL resource = CaptchaTest.class.getResource("");
		try {
			return Paths.get(resource.toURI())
					.toFile()
					.getAbsolutePath()
					.split("test-classes")[0]
					+ name;
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
