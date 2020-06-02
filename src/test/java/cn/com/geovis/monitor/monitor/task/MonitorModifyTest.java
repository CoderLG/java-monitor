package cn.com.geovis.monitor.monitor.task;

import cn.com.geovis.monitor.domain.MonitorFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;

/**
 * author: LG
 * date: 2020-05-15 11:34
 * desc:
 */
@Slf4j
public class MonitorModifyTest {
    private String monitorDir = "D:\\listenDir";

    @Test
    public void run() {
        Date date = null;
        MonitorFileEntity changeFile = null;
        log.info("文件修改操作，开始监听");
        try {
            //获取文件系统的WatchService 对象
            WatchService watchService = FileSystems.getDefault().newWatchService();
            //为指定目录路径注册所要监听的事件
            Paths.get(this.monitorDir).register(watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_CREATE);

            //获取下一个文件变化事件
            while(true){
                date = new Date();
                try {
                    //监听阻塞
                    WatchKey key = watchService.take();


                    for (WatchEvent<?> event : key.pollEvents()) {
                        String fileName = event.context().toString();
                        System.out.println(fileName+"  文件发生了"+event.kind()+"事件");
                        //可以自定义规则，是否是一个.ok文件，是则做相关处理
                        if(event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)){
                            System.out.println("111111111");
                        }
                    }

                    // 重设WatchKey
                    boolean valid = key.reset();
                    // 如果重设失败，退出监听
                    if (!valid)
                    {
                        log.error("监听修改文件出错，重启");
                        break;
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    log.error("监听修改文件出错，重启");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("监听修改文件出错，重启");
            e.printStackTrace();
        }
    }
}