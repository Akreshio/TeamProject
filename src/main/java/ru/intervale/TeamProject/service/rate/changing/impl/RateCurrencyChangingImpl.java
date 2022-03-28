/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "22.03.2022, 20:47"
 * @version V 1.0.0
 */


package ru.intervale.TeamProject.service.rate.changing.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.dao.AlfaBankDao;
import ru.intervale.TeamProject.service.rate.Currency;
import ru.intervale.TeamProject.service.rate.changing.RateCurrencyChanging;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class RateCurrencyChangingImpl implements RateCurrencyChanging {

    private AlfaBankDao alfaBankDao;

    @Override
    public Map<LocalDateTime, BigDecimal> getExchangeRate(Currency currency, ParamRequest param) {

        if (param!=null) {
            List<LocalDateTime> date = getTimePeriod(param);
            if ((param.getPeriod() != null) && (param.getPeriod() == Period.hour)) {
                return alfaBankDao.getByPeriod(date.get(0), date.get(1), currency);
            }
            return alfaBankDao.getByPeriod(date, currency);
        }
        return alfaBankDao.getByPeriod(getTimePeriod(), currency);
    }

    private List<LocalDateTime> getTimePeriod(ParamRequest param) {
        List<LocalDateTime> dateList = new ArrayList<>();

        // Дата окончания выборки
        LocalDateTime fDate = LocalDateTime.now();
        if (param.getFinish()!=null){
            fDate = param.getFinish();
        }
        // Дата начала выборки
        LocalDateTime sDate;
        if (param.getStart()!=null){
            sDate = param.getStart();
        } else { sDate = fDate.minusDays(10);}

        // Период выборки
        if (param.getPeriod()!=null){
            switch (param.getPeriod()) {
                case hour: {
                    dateList.add(sDate);
                    dateList.add(fDate);
                    return  dateList;
                }
                case day: {
                    log.debug(
                            "Using the period formation for day with start day: " + sDate
                                    + " and finish day: " + fDate
                    );
                    while (sDate.isBefore(fDate)) {
                        dateList.add(sDate);
                        sDate = sDate.plusDays(1);
                    }
                    dateList.add(sDate);
                    return  dateList;
                }
                case  week: {
                    log.debug(
                            "Using the period formation for week with start day: " + sDate
                                    + " and finish day: " + fDate
                    );
                    while (sDate.isBefore(fDate)) {
                        dateList.add(sDate);
                        sDate = sDate.plusDays(7);
                    }
                    return  dateList;
                }
                case month: {
                    log.debug(
                            "Using the period formation for month with start day: " + sDate
                                    + " and finish day: " + fDate
                    );
                    while (sDate.isBefore(fDate)){
                        dateList.add(sDate);
                        sDate = sDate.plusMonths(1);
                    }
                    return  dateList;
                }
            }
        } else {
            for (int i=0; i<=10; i++){
                dateList.add(sDate);
                sDate = sDate.plusDays(1);
            }
        }
        return  dateList;
    }

    private List<LocalDateTime> getTimePeriod(){
        log.debug("Using the period formation without param");

        List<LocalDateTime> dateList = new ArrayList<>();
        LocalTime time = LocalTime.of(0, 0, 0, 0);
        LocalDateTime std = LocalDateTime.of(LocalDate.now(), time);

        for (int i=0; i<10; i++){
            dateList.add(std);
            std = std.minusDays(1);
        }
        return  dateList;
    }
}
