package cn.com.geovis.monitor.dao;

import cn.com.geovis.monitor.domain.MonitorFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonitorFileEntityRepository extends JpaRepository<MonitorFileEntity, Long> {


    void deleteByFileName(String name);

    @Query(value = "select * from monitor_file where status != ?1" ,nativeQuery = true)
    List<MonitorFileEntity> findAllByStatusUnequal(String name);

    MonitorFileEntity findByFileName(String name);
}
