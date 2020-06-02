package cn.com.geovis.monitor.monitor;

import cn.com.geovis.monitor.dao.MonitorFileEntityRepository;
import cn.com.geovis.monitor.domain.MonitorFileEntity;
import cn.com.geovis.monitor.monitor.task.MonitorFileStatus;
import cn.com.geovis.monitor.service.impl.UserServiceimpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * author: LG
 * date: 2020-05-15 16:55
 * desc:
 */

@Component
@Slf4j
public class FileListenerExample  extends FileAlterationListenerAdaptor {

    @Autowired
    private MonitorFileEntityRepository monitorFileDao;

    @Autowired
    private UserServiceimpl userServiceimpl;

    @Value("${dm.monitor.dir}")
    private String monitorDir;

    @Override
    public void onStart(FileAlterationObserver observer) {
        log.info("观察文件变化任务开始");
    }


    @Override
    public void onDirectoryCreate(File directory) {
        log.info("创建文件夹 " + directory);
        createProcess(directory);

    }

    @Override
    public void onDirectoryChange(File directory) {
        log.info("修改文件夹 " + directory);

        changeProcess(directory);
    }

    @Override
    public void onDirectoryDelete(File directory) {
        log.info("删除文件夹 " + directory);
       deleteProcess(directory);
    }



    @Override
    public void onFileCreate(File file) {
        log.info("创建文件 " + file);
        createProcess(file);
    }

    @Override
    public void onFileChange(File file) {
       log.info("修改文件 " + file);
        changeProcess(file);
    }

    @Override
    public void onFileDelete(File file) {
        log.info("删除文件 " + file);
        deleteProcess(file);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
       log.info("观察文件变化任务结束\n");
    }

    private void  createProcess(File directory){

        String[] tartgetPathArr = getTartgetPathArr(directory);
        if(tartgetPathArr.length > 1){
            //不是首层
            process(tartgetPathArr);
        }else{
            //首层
            createFile(directory, tartgetPathArr[0]);
        }
    }

    private void  changeProcess(File directory){
        String[] tartgetPathArr = getTartgetPathArr(directory);
        process(tartgetPathArr);
    }

    private void  deleteProcess(File directory){

        String[] tartgetPathArr = getTartgetPathArr(directory);
        if(tartgetPathArr.length > 1){
            //不是首层
            process(tartgetPathArr);
        }else{
            //首层
            MonitorFileEntity fileByName = getFileByName(tartgetPathArr[0]);
            updateStatus(fileByName,MonitorFileStatus.REMOVE);
        }
    }





    private String[] getTartgetPathArr(File directory){
        File file = new File(monitorDir);
        String replaceStr = file.getAbsolutePath() +  File.separator;
        String[] split = directory.getAbsolutePath().replace(replaceStr, "").split("\\" + File.separator);
        return split;

    }

    private void createFile(File directory,String name){
        Date date = new Date();
        MonitorFileEntity monitorFileEntity = new MonitorFileEntity();
        monitorFileEntity.setLength(directory.length());
        monitorFileEntity.setFileDir(directory.getAbsolutePath());
        monitorFileEntity.setCreateTime(date);
        monitorFileEntity.setLastTime(date);
        monitorFileEntity.setFileName(name);
        monitorFileEntity.setUserId(userServiceimpl.getUserId());
        monitorFileEntity.setStatus(MonitorFileStatus.UPLOADING.name());
        monitorFileDao.save(monitorFileEntity);
        log.info("数据库新增文件 " + monitorFileEntity.getFileName());
    }

    private MonitorFileEntity getFileByName(String name){
          return monitorFileDao.findByFileName(name);
    }


    private void updateStatus( MonitorFileEntity monitorFileEntity, MonitorFileStatus status){
        Date date = new Date();
        monitorFileEntity.setStatus(status.name());
        monitorFileEntity.setLastTime(date);
        monitorFileDao.save(monitorFileEntity);
        log.info("数据库修改文件：" + monitorFileEntity.getFileName()+" 状态为：" + status.name());
    }

    /**
     * 判断首层是否是finfished
     *
     * 不是  修改时间 状态为uploading
     * 是   修改时间  状态为error
     */
    private void process(String [] tartgetPathArr){

        MonitorFileEntity monitorFile = getFileByName(tartgetPathArr[0]);
        if ((monitorFile!= null && monitorFile.getStatus().equals(MonitorFileStatus.FINISHED.name())) || (monitorFile!= null && monitorFile.getStatus().equals(MonitorFileStatus.ERROR.name()))) {
            updateStatus(monitorFile,MonitorFileStatus.ERROR);

        }else {
            updateStatus(monitorFile,MonitorFileStatus.UPLOADING);
        }
    }
}
