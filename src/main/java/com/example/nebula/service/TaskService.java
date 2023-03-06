package com.example.nebula.service;

import cn.hutool.core.date.DateUtil;
import com.example.nebula.vo.AttributeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author fulin
 * @date 2023/1/29 16:14
 * @name TaskService
 */
@Component
@EnableScheduling
@Slf4j
public class TaskService {

    @Autowired
    GraphCommonService graphCommonService;

    /**
     * 每天运行一次,保证session不过期
     *
     * @author fulin 2023/1/29 16:20
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void nebulaNotExpired() {
        graphCommonService.executeJson("SHOW SPACES;", AttributeVo.class);
        log.info("延续session不过期:{}", DateUtil.now());
    }
}
