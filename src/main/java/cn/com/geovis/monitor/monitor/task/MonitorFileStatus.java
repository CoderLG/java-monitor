package cn.com.geovis.monitor.monitor.task;

/**
 * 文件上传的状态
 */
public enum MonitorFileStatus {

    /**
     * 上传中
     */
    UPLOADING,

    /**
     * 上传完
     */
    UPLOADED,


    /**
     * 上传完成 并推送给前端
     */
    FINISHED,

    /**
     *删除
     */
    REMOVE,

    /**
     * 上传出错
     */
    ERROR

}
