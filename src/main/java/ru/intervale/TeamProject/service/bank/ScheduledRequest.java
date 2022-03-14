/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "13.03.2022, 12:43"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ScheduledRequest {

    //каждый рабочий день (понедельник-пятница) с 8:00 до 18:00 с шагом в 15 минт
    @Scheduled(cron = "0 0/15 8-18 * * MON-FRI")
    public void reportCurrentTime() {
        System.out.println(" text ");
        log.info("The time is now ");
    }
    //каждый день с 8:00 до 18:00 с шагом в 10 минт
    @Scheduled(cron = "0 0/10 8-18 * * *")
    public void reportCurrentTime2() {
        System.out.println(" text ");
        log.info("The time is now ");
    }
    //каждый день в 12 ночи
    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime3() {
        System.out.println(" text ");
        log.info("The time is now ");
    }


}
