package com.lzb.demo.common.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 控制台打印 <br/>
 * Created on : 2022-05-05 21:10
 *
 * @author lizebin
 */
public class SystemOutUtils {

    public static final String PREFIX = "test_prefix_";

    public static final String LINE = "\n";

    /**
     * 打印
     *
     * @param text
     */
    public static void println(String text) {
        System.out.println(PREFIX + text);
    }

    /**
     * 验证
     *
     * @param output
     */
    public static ByteArrayOutputStream convert(ByteArrayOutputStream output) {
        ByteArrayOutputStream newOutput = new ByteArrayOutputStream();
        for (String item : output.toString().split(LINE)) {
            if (item.startsWith(PREFIX)) {
                byte[] bytes = item.getBytes();
                try {
                    newOutput.write(bytes);
                    newOutput.write(LINE.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newOutput;
    }

}
