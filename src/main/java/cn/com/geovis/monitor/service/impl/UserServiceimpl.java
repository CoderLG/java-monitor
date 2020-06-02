package cn.com.geovis.monitor.service.impl;

import com.geovis.user.annotation.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * author: LG
 * date: 2020-04-23 14:45
 * desc:
 */

@Service
@Slf4j
public class UserServiceimpl {

    @Lazy
    @Autowired
    AuthService authService;


    public String getUserId() {
        Integer id = 0;
        try {
            id= authService.getUserInfo().getId();
        } catch (Exception e) {
//            log.error("获取userId错误",e);
            id = 0;
        }
        return id.toString();

    }

}
