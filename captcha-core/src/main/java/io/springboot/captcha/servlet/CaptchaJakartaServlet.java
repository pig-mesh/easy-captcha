package io.springboot.captcha.servlet;

import io.springboot.captcha.utils.CaptchaJakartaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 验证码 servlet
 *
 * @author FULaBUla
 * @since 2023-01-28 上午 10:08
 */
public class CaptchaJakartaServlet extends HttpServlet {
	private static final long serialVersionUID = -8576758193239867624L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		CaptchaJakartaUtil.out(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doGet(request, response);
	}

}
