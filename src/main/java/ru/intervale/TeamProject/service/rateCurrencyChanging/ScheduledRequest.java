/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "13.03.2022, 12:43"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.rateCurrencyChanging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.dao.AlfaBankDao;
import ru.intervale.TeamProject.service.external.alfabank.AlfaBankService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class ScheduledRequest {

    private AlfaBankDao alfaBankDao;
    private AlfaBankService alfaBank;

//    каждый рабочий день (понедельник-пятница) с 8:00 до 18:00 с шагом в 15 минт
//    @Scheduled(cron = "0 0/15 8-18 * * MON-FRI")
//    public void reportCurrentTime() {
//        Map<Currency, BigDecimal> exchangeRateChange = alfaBank.getNow();
//        exchangeRateChange.forEach((key, value) -> log.info(key + " " + value));
//    }

    //каждый день с 8:00 до 18:00 с шагом в 10 минт
    @Scheduled(cron = "0 0/1 8-18 * * *")
    public void requestToAlfaBank() {
        Map<String, BigDecimal> exchangeRateChange = alfaBank.getNow();
        exchangeRateChange.forEach((key, value) -> log.info(key + " " + value));
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

    //каждый день в 12 ночи
    @Scheduled(cron = "0 0 0 * * *")
    public void requestCloseDayAlfaBank () {
        Map<String, BigDecimal> exchangeRateChange = alfaBank.getNow();
        exchangeRateChange.forEach((key, value) -> log.info(key + " " + value));
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
