package cn.com.geovis.monitor;

import com.geovis.user.annotation.EnableAuth;
import com.spring4all.swagger.EnableSwagger2Doc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 问题：
 * 1.当error状态时，删除数据。 新出信息是否返回给前端。
 * 目前返回，监听并恢复正常
 */

@EnableAuth(userRoleIds = {"1","3"},pathPatterns = "**/api/**")
@ComponentScan({"com.geovis.user.annotation.service", "cn.com.geovis.monitor"})
@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableSwagger2Doc
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
