package io.springboot.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lengleng
 * @date 2020/7/31
 */
@Data
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * 默认长度，默认值： 4
     */
    private int len = 4;

    /**
     * 默认宽度，默认值： 130
     */
    private int width = 130;

    /**
     * 默认高度，默认值：48
     */
    private int height = 48;

}
