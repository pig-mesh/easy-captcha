package com.pig4cloud.captcha.utils;

/**
 * 图形验证码工具抽象父类
 *
 * @author FULaBUla
 * @since 2023-01-28 上午 10:08
 */
public abstract class AbstractCaptchaUtil {

	/**
	 * session 键名
	 */
	protected static final String SESSION_KEY = "captcha";

	/**
	 * 默认长度
	 */
	protected static final int DEFAULT_LEN = 4;

	/**
	 * 默认宽度
	 */
	protected static final int DEFAULT_WIDTH = 130;

	/**
	 * 默认高度
	 */
	protected static final int DEFAULT_HEIGHT = 48;


}
