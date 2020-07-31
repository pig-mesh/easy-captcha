package com.pig4cloud.captcha.config;

import com.pig4cloud.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dreamlu.mica.auto.annotation.AutoIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lengleng
 * @date 2020/7/31
 */
@AutoIgnore
@Controller
@RequiredArgsConstructor
public class CaptchaEndpoint {

	private final CaptchaProperties properties;

	/**
	 * 生成验证码
	 *
	 * @param response 响应流
	 */
	@SneakyThrows
	@GetMapping("${captcha.create.path:/create}")
	public void create(HttpServletResponse response) {
		ArithmeticCaptcha captcha = new ArithmeticCaptcha(properties.getWidth(), properties.getHeight());
		String result = captcha.text();
		// 设置响应头
		response.setContentType(captcha.getContentType());
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 转换流信息写出
		captcha.out(response.getOutputStream());
	}

}
