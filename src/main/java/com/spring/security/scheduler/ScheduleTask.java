/**
 * Created by Enzo Cotter on 20-4-17.
 */
package com.spring.security.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTask {

    /**
     * 功能描述: 定时保存图片请求次数, 12小时保存一次
     *
     * @param:
     * @return:
     * @auther: wxy
     * @date: 2020/04/17 13:14
     */
    @Scheduled(cron = "0 0 1/12 * * ?")
    public void halfSavePicVisitCount() {
        log.info("开始执行保存访问次数任务开始:{}, 保存访问次数的日期:{}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.now().minusDays(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        service.saveVisits();
    }

    /**
     * 每四个小时保存一次访问量
     */
    @Scheduled(cron = "0 2 0/4 * * ? ")
    public void everFourHourSaveVisits() {
        log.info("开始执行保存每四个小时保存一次访问量:{}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        dataStatisticsService.everFourHourSaveVisits();
    }

    /**
     * 每天统计一次时间间隔 2:05
     */
    @Scheduled(cron = "0 5 2 * * ? ")
    public void everDaySaveInterval() {
        log.info("开始执行保存每天统计一次时间间隔:{}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        dataStatisticsService.everDaySaveInterval();
    }

    /**
     * 每天统计一次错误码 2:08
     */
    @Scheduled(cron = "0 8 2 * * ? ")
    public void everDaySaveErrorCode() {
        log.info("开始执行保存每天统计一次错误码:{}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        dataStatisticsService.everDaySaveErrorCode();
    }

}
