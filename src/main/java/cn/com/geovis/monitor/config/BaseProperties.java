package cn.com.geovis.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.Data;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Field;

@Component
@Data
@Order(1)
public class BaseProperties {
	
    @Value("${spring.application.name}")
    private String name;
    
    @Value("${spring.application.version}")
    private String version;

    @Value("${dm.monitor.dir}")
    private static String monitorDir;

    /**
     * 在环境变量之前运行
     */
//    @PostConstruct
//    public void formatProperties(){
//
//    }


}
