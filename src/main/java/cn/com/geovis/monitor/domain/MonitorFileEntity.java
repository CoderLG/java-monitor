package cn.com.geovis.monitor.domain;

import cn.com.geovis.monitor.monitor.task.MonitorFileStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * author: LG
 * date: 2020-04-23 10:35
 * desc:
 * 监听文件的实体
 */

@Data
@Entity(name="monitorFile")
public class MonitorFileEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件名
     * 可视为主键，因为操作系统中 文件名不能重复
     */
    @Column(nullable=false)
    @ApiModelProperty("文件名")
    private String fileName;


    /**
     * 保存的绝对路径
     */
    @Column(nullable=false)
    @ApiModelProperty("保存的绝对路径")
    private String fileDir;

    /**
     * 保存的绝对路径
     */
    @Column(nullable=false)
    @ApiModelProperty("保存的绝对路径")
    private Long length;


    /**
     * 数据类型
     *  细节再定
     */
    @Column(length=10)
    @ApiModelProperty("数据类型")
    private String dataType;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Column(nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();


    /**
     * 最后操作时间
     */
    @ApiModelProperty("最后一次操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastTime;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    @Column(length = 1000)
    private String error;


    /**
     * 状态
     */
    @Column(length = 10,nullable=false)
    private String status = MonitorFileStatus.UPLOADING.name();


    /**
     * 当前操作的用户id
     */
    @ApiModelProperty("当前操作的用户id")
    private String userId;

}
