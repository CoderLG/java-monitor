package cn.com.geovis.monitor.monitor.task;

import cn.com.geovis.monitor.dao.MonitorFileEntityRepository;
import cn.com.geovis.monitor.domain.MonitorFileEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Slf4j
public class SentMess implements Task {
    private MonitorFileEntityRepository monitorFileDao;

    public  SentMess( MonitorFileEntityRepository monitorFileDao){
        this.monitorFileDao = monitorFileDao;
    }

    /**
     *
     * 这个线程 轮询数据库
     * 将 uploading 状态的 忽略
     * 将 uploaded 状态的 传给前端 并修改为 finished
     * 将 finished 状态的 忽略
     * 将 remove 状态的 删除
     * 将 error 状态的 提示，告警重启
     *
     */

    @Override
    public void run() {
        Date date = null;
        log.info("开始处理文件各种状态");
        try {
            while (true) {
                date = new Date();
                //修改查询条件
                List<MonitorFileEntity> all = monitorFileDao.findAllByStatusUnequal(MonitorFileStatus.FINISHED.name());
                for (MonitorFileEntity monitorFileEntity : all) {
                    if (monitorFileEntity.getStatus().equals(MonitorFileStatus.ERROR.name())) {

                        long interval =  (date.getTime() - monitorFileEntity.getLastTime().getTime())/1000;

                        if(interval > 40l ){
                            log.error(monitorFileEntity.getFileName() + " 出现发送给前端消息 后，又发生改变的情况！！重启并发送消息给前端");  //修改
                            //重启
                            //todo
                        }

                    }else if(monitorFileEntity.getStatus().equals(MonitorFileStatus.UPLOADING.name())){
                        //判断时间 修改状态finished
                        //间隔 秒
                        long interval =  (date.getTime() - monitorFileEntity.getLastTime().getTime())/1000;

                        if(interval > 40l ){
                            monitorFileEntity.setLastTime(date);
                            monitorFileEntity.setStatus(MonitorFileStatus.FINISHED.name());
                            monitorFileDao.save(monitorFileEntity);
                            log.info(monitorFileEntity.getFileName() + " 文件上传完毕，发送消息给前端,状态改为FINISHED");
                            //传给前端
                            //todo
                        }

                    }else {
                        monitorFileDao.delete(monitorFileEntity);
                        log.info(monitorFileEntity.getFileName() + " 文件被删除，发送消息给前端");
                    }

                }

                Thread.sleep( 1000 * 7);
            }
        } catch (Exception e) {
            log.error("改变文件存储发生错误",e.getMessage());
            //重启
            //todo
        }
    }
}
