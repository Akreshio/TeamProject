/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "09.03.2022, 19:42"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class BankImpl implements Bank {

    private AlfabankService alfabank;

    @Override
    public Map<LocalDateTime, BigDecimal> getExchangeRate(Currency currency, ParamRequest paramMap) {

        if (paramMap!=null) {
            return alfabank.get(currency,get(paramMap));
        }
            return alfabank.get(currency,get());
        }

    private List<LocalDateTime> get(ParamRequest param){
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
                case hour:
                    break;
                case day:{
                    log.info("Using the period formation for day with start day: " + sDate + " and finish day: " + fDate);
                    while (sDate.isBefore(fDate)) {
                        dateList.add(sDate);
                        sDate = sDate.plusDays(1);
                    }
                    dateList.add(sDate);
                    log.info( dateList.toString());
                    return  dateList;
                }
                case  week: {
                    log.info("Using the period formation for week with start day: " + sDate + " and finish day: " + fDate);
                    while (sDate.isBefore(fDate)) {
                        dateList.add(sDate);
                        sDate = sDate.plusDays(7);
                    }
                    return  dateList;
                }
                case  month:
                {
                    log.info("Using the period formation for month with start day: " + sDate + " and finish day: " + fDate);
                    while (sDate.isBefore(fDate)){
                        dateList.add(sDate);
                        sDate = sDate.plusMonths(1);
                    }
                    return  dateList;
                }
            }
        } else {
            for (int i=0; i<=10; i++){
                dateList.add(fDate);
                fDate = fDate.plusDays(1);
            }
        }
        return  dateList;
    }

    private List<LocalDateTime> get(){
        log.debug("Using the period formation without param");

        List<LocalDateTime> dateList = new ArrayList<>();
        LocalDateTime std = LocalDateTime.now();

        for (int i=0; i<=10; i++){
            std = std.minusDays(1);
        }
        return  dateList;
    }
}
