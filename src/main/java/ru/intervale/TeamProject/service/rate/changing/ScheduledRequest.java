/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "22.03.2022, 20:47"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.rate.changing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.dao.AlfaBankDao;
import ru.intervale.TeamProject.service.external.alfabank.AlfaBankService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * The type Scheduled request.
 */
@Service
@Slf4j
@AllArgsConstructor
public class ScheduledRequest {

    private AlfaBankDao alfaBankDao;
    private AlfaBankService alfaBank;

    /**
     * Request to alfa bank every day from 8am to 6pm with an interval of 10 minutes.
     */
//каждый день с 8:00 до 18:00 с шагом в 10 минт
    @Scheduled(cron = "0 0/10 8-18 * * *")
    public void requestEveryTenMinutes () {
        Map<String, BigDecimal> exchangeRateChange = alfaBank.getNow();
        exchangeRateChange.forEach((key, value) -> log.debug(key + " " + value));
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateDB = LocalDateTime.of(
                dateNow.getYear(),
                dateNow.getMonth(),
                dateNow.getDayOfMonth(),
                dateNow.getHour(),
                dateNow.getMinute()
        );
        alfaBankDao.save(dateDB,exchangeRateChange);
    }

    /**
     * Request every day at 12 o'clock.
     */
//каждый день в 12 ночи
    @Scheduled(cron = "0 0 0 * * *")
    public void requestCloseDay () {
        Map<String, BigDecimal> exchangeRateChange = alfaBank.getNow();
        exchangeRateChange.forEach((key, value) -> log.debug(key + " " + value));
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateDB = LocalDateTime.of(
                dateNow.getDayOfMonth(),
                dateNow.getMonth(),
                dateNow.getYear(),
                dateNow.getHour(),
                dateNow.getMinute()
        );
        alfaBankDao.save(dateDB,exchangeRateChange);
    }
}
