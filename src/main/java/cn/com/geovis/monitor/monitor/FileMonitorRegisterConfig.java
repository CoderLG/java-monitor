package cn.com.geovis.monitor.monitor;

import cn.com.geovis.monitor.dao.MonitorFileEntityRepository;
import cn.com.geovis.monitor.domain.MonitorFileEntity;
import cn.com.geovis.monitor.monitor.task.MonitorFileStatus;
import cn.com.geovis.monitor.service.impl.UserServiceimpl;
import cn.com.geovis.monitor.monitor.task.SentMess;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * author: LG
 * date: 2020-05-15 17:01
 * desc:
 */

@Slf4j
@Component
public class FileMonitorRegisterConfig {
    @Resource
    private FileListenerMonitorUtils fileListenerMonitorUtils ;

    @Autowired
    private MonitorFileEntityRepository monitorFileDao;

    @Autowired
    private UserServiceimpl userServiceimpl;

    @Value("${dm.monitor.dir}")
    private String monitorDir ;

    private static ExecutorService monitorThreadPool = ThreadUtil.newExecutor(2);


    @PostConstruct
    private void register() {
        File directory = new File(monitorDir);
        if (!directory.exists()) {
            throw new NullPointerException("目录不存在启动文件观察者失败:" + directory);
        }
        Long intervalSeconds = 5L;
        String prefix = "test_";
        String suffix = ".json";
        Date date = new Date();

        try {

            /**
             * 启动恢复现场
             * 查库，监听文件夹下是否还有测文件  没有修改状态 remove
             *     有 error,uploading  修改时间
             *
             * 监听文件夹下 多出来的文件没有入库的 入库uoloading
             *
             */

            String[] fileNames= directory.list();
            HashSet<String> namesList = new HashSet<>(Arrays.asList(fileNames));

            List<MonitorFileEntity> monitorFileEntities = monitorFileDao.findAll();
            monitorFileEntities.forEach(entity ->{
                if(namesList.contains(entity.getFileName())){
                    namesList.remove(entity.getFileName());
                    if (entity.getStatus().equals(MonitorFileStatus.ERROR.name()) || entity.getStatus().equals(MonitorFileStatus.UPLOADING.name())) {
                        entity.setLastTime(date);
                        entity.setStatus(MonitorFileStatus.UPLOADING.name());
                        String fileDir = entity.getFileDir();
                        File file = new File(fileDir);
                        entity.setLength(file.length());
                        monitorFileDao.save(entity);
                        log.info("初始化 "+ entity.getFileName()+" 更新时间库续命 ");
                    }
                }else{
                    entity.setLastTime(date);
                    entity.setStatus(MonitorFileStatus.REMOVE.name());
                    monitorFileDao.save(entity);
                    log.info("初始化 "+entity.getFileName()+" 已被删除");
                }

            });
            for (String name : namesList) {
                File file = new File(monitorDir + File.separator + name);

                MonitorFileEntity monitorFileEntity = new MonitorFileEntity();
                monitorFileEntity.setLength(file.length());
                monitorFileEntity.setFileDir(file.getAbsolutePath());
                monitorFileEntity.setCreateTime(date);
                monitorFileEntity.setLastTime(date);
                monitorFileEntity.setFileName(name);
                monitorFileEntity.setUserId(userServiceimpl.getUserId());
                monitorFileEntity.setStatus(MonitorFileStatus.UPLOADING.name());
                monitorFileDao.save(monitorFileEntity);
                log.info("初始化 数据库新增文件 " + monitorFileEntity.getFileName());

            }


            /**
             * 启动一个线程
             *
             * 这个线程 轮询数据库
             * 将 uploading 状态的 忽略
             * 将 uploaded 状态的 传给前端 并修改为 finished
             * 将 finished 状态的 忽略
             * 将 remove 状态的 删除
             * 将 error 状态的 提示，告警重启
             *
             */
            SentMess sentMess = new SentMess(monitorFileDao);
            monitorThreadPool.execute(sentMess);


            // 为指定文件夹下面的指定文件注册文件观察者
            FileAlterationMonitor monitor = fileListenerMonitorUtils.getMonitor(directory, intervalSeconds, prefix, suffix);
            // 启动观察者
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
