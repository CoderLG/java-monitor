package cn.com.geovis.monitor.monitor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2020-05-15 17:28
 * desc:
 */
public class FileListenerExampleTest {


    @Test
    public void onStart() {
        File file = new File("D:/listenDir/big-data");
        System.out.println(file);
        System.out.println(file.getAbsolutePath());
        String replace = file.getAbsolutePath().replace("D:\\listenDir\\", "");
        System.out.println(replace);

        System.out.println(replace.split("\\" + File.separator)[0]);

    }
}