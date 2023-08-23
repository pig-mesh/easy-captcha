package com.pig4cloud.captcha.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 解决自定义字体读取时，产生.tmp临时文件耗磁盘的问题。
 *
 * 解决思路:
 * Font类的createFont有个重载方法–>java.awt.Font#createFont(int, java.io.File),
 * 不产生临时文件获取字体代码实现
 * <code>
 *  URL url = FontLoader.class.getResource("font/SourceHanSansCN-Regular.otf");
 *  String pathString = url.getFile();
 *  Font selfFont = Font.createFont(Font.TRUETYPE_FONT, new File(pathString));
 * </code>
 * 上面的解决方案会导致另一个问题，字体文件在生产环境是在jar包里，部分操作系统环境下，直接读取读取不到，只能通过流的方式获取。
 *
 * 因此，本方案采用的办法是把jar包中的字体文件复制到java.io.tmpdir临时文件夹中
 * ，再采用<code>java.awt.Font#createFont(int, java.io.File)</code>的方式产生字体，既解决了临时文件tmp消耗磁盘的问题，也解决了
 * 部分操作系统下读不到文件的问题。
 *
 * @author zrh 455741807@qq.com
 * @date 2022-05-07
 *
 */
public class FontsUtil {

    /**
     * 手动复制字体文件到临时目录. 调用传文件的构造方法创建字体
     * @param fontName 字体文件名称
     * @return
     */
    public static Font getFont(String fontName, int style, float size) {
        Font font = null;

        String path = System.getProperty("java.io.tmpdir");

        File tempFontFile = new File(path + fontName);
        if(!tempFontFile.exists()){
            //临时文件不存在
            copyTempFontFile(fontName, tempFontFile);
        }
        if(tempFontFile.exists()) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, tempFontFile).deriveFont(style, size);;
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                tempFontFile.delete();
            }
        }
        return font;
    }

    /**
     * 复制字体文件到临时文件目录
     * @param fontName
     * @param tempFontFile
     */
    private static synchronized void copyTempFontFile(String fontName, File tempFontFile){
        try(InputStream is = FontsUtil.class.getResourceAsStream("/" + fontName)){
            FileUtil.copyToFile(is, tempFontFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
