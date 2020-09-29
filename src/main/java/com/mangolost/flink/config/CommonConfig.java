package com.mangolost.flink.config;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class CommonConfig {

    public static Properties prop;

    public static String runmode;

    /**
     *
     */
    public static void init() {

        //读取日志文件
        prop = new Properties();
        InputStream in = null;
        InputStream in2 = null;
        try {
            //读取属性文件
            in = new BufferedInputStream(new FileInputStream("src/main/resources/application.properties"));
            prop.load(in);     ///加载属性列表
            String profileActive = prop.getProperty("profiles.active");
            if (StringUtils.isNotBlank(profileActive)) {
                profileActive = "dev";
            }
            in.close();
            prop.clear();

            in2 = new BufferedInputStream(new FileInputStream("src/main/resources/application-" + profileActive + ".properties"));
            prop.load(in2);     ///加载属性列表
            runmode = prop.getProperty("system.runmode");
            in2.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    in = null;
                }
            }
            if (in2 != null) {
                try {
                    in2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    in2 = null;
                }
            }
        }
    }
}
