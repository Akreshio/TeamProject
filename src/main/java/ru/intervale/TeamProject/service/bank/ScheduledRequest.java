/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "13.03.2022, 12:43"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;

import java.math.BigDecimal;
import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class ScheduledRequest {

    private AlfabankService alfaBank;

    //каждый рабочий день (понедельник-пятница) с 8:00 до 18:00 с шагом в 15 минт
    @Scheduled(cron = "0 0/15 8-18 * * MON-FRI")
    public void reportCurrentTime() {
        Map<Currency, BigDecimal> exchangeRateChange = alfaBank.getNow();
        exchangeRateChange.forEach((key, value) -> log.info(key + " " + value));
    }
    //каждый день с 8:00 до 18:00 с шагом в 10 минт
    @Scheduled(cron = "0 0/10 8-20 * * *")
    public void reportCurrentTime2() {
        Map<Currency, BigDecimal> exchangeRateChange = alfaBank.getNow();
        exchangeRateChange.forEach((key, value) -> log.info(key + " " + value));
    }
    //каждый день в 12 ночи
    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime3() {
        System.out.println(" text ");
        log.info("The time is now ");
    }


}
