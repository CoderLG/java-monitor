package cn.com.geovis.monitor.monitor.task;

import cn.com.geovis.monitor.utils.TimeUtils;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: LG
 * date: 2020-05-14 14:05
 * desc:
 */
public class ChangeFileStoreTest {
    Map<String, String> changeFileStore = new ConcurrentHashMap<>();
    @Test
    public void run() {
        Long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        System.out.println(currentTime.toString().length());
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long currentTime2 = System.currentTimeMillis();
        System.out.println(currentTime2);


        System.out.println(TimeUtils.TimeSub(currentTime,currentTime2));
//        System.out.println(changeFileStore.get("ff"));
//        boolean isExist = changeFileStore.get("ff")!=null ? true:false ;
//        System.out.println(isExist);

//        while (true) {
//
//            try {
//                File file = new File("D:/listenDir/tt");
//                System.out.println(file.isDirectory());
//
//                FileChannel fileChannel=new FileOutputStream(file).getChannel();
//                FileLock fileLock = fileChannel.tryLock();
//
//                System.out.println("fileLock->" + fileLock);
//                Thread.sleep(1000 * 2);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                break;
//            }
//        }

    }
}