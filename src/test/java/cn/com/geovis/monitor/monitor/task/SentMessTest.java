package cn.com.geovis.monitor.monitor.task;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * author: LG
 * date: 2020-05-15 12:05
 * desc:
 */
public class SentMessTest {

    @Test
    public void run() {

        File file = new File("D:\\listenDir");
        File file2 = new File("D:/listenDir\\big-data\\");
        List<String> strings = Arrays.asList(file.list());
        for (String string : strings) {
            System.out.println(string);

        }
    }
}