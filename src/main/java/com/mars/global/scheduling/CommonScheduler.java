package com.mars.global.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableSchedulerLock(defaultLockAtMostFor = "PT10S")
@RequiredArgsConstructor
public class CommonScheduler {
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @SchedulerLock(name = "commonPushV1", lockAtMostFor = "PT10s", lockAtLeastFor = "PT10s")
    public void miniGameWormPush() {
        log.info("Common Push V1 Start");
    }
}
