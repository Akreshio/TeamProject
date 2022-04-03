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
import ru.intervale.TeamProject.service.external.alfabank.BelApbExchangeClient;

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
    private BelApbExchangeClient belApbBank;

    private static Map<String, BigDecimal> rateChange;


    /**
     * Request to alfa bank every day from 8am to 6pm with an interval of 10 minutes.
     */
//каждый день с 8:00 до 18:00 с шагом в 10 минут
    @Scheduled(cron = "0 0/1 8-18 * * *")
    public void requestEveryTenMinutes () {
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime date = LocalDateTime.of(
                dateNow.getYear(),
                dateNow.getMonth(),
                dateNow.getDayOfMonth(),
                dateNow.getHour(),
                dateNow.getMinute()
        );

        if (rateChange==null) {
            rateChange = belApbBank.getRatesByDate(date);
        }
        rateChange.putAll(alfaBank.getNow());

        rateChange.forEach((key, value) -> log.debug(key + " " + value));
        alfaBankDao.save(date,rateChange);
    }

    /**
     * Request every day at 12 o'clock.
     */
//каждый день в 12 ночи
    @Scheduled(cron = "0 0 0 * * *")
    public void requestCloseDay () {
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime date = LocalDateTime.of(
                dateNow.getDayOfMonth(),
                dateNow.getMonth(),
                dateNow.getYear(),
                dateNow.getHour(),
                0
        );

        rateChange = belApbBank.getRatesByDate(date);
        rateChange.putAll(alfaBank.getNow());

        rateChange.forEach((key, value) -> log.debug(key + " " + value));
        alfaBankDao.save(date,rateChange);
    }
}
