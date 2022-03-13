/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "09.03.2022, 19:44"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "05.03.2022, 16:45"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "04.03.2022, 21:14"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

/**
 * The type Formation period.
 */
@Component
@NoArgsConstructor
public class FormationPeriod {

    /**
     * Get list.
     *
     * in map <string, string>
     *
     *     sDate, date  - дата начала формирования
     *     fDate, date - дата окончания формирования
     *     per, enum - перечисление вида (месяц, неделя, день)
     *
     * @return the list
     */
    public List<String> get(Map <String, String> paramMap){

        List<String> dateList = new ArrayList<>();

        LocalDate sDate;
        LocalDate fDate = LocalDate.now();

        if (paramMap.get("fDate")!=null){
            fDate = strToDate(paramMap.get("fDate"));
        }
        if (paramMap.get("sDate")!=null){
            sDate = strToDate(paramMap.get("sDate"));
        } else { sDate = fDate.minusDays(10);}

        if (paramMap.get("per")!=null){
            switch (paramMap.get("per")) {
                case "day":
                    while (sDate.isBefore(fDate)){
                        dateList.add(format(sDate));
                        sDate = sDate.plusDays(1);
                    }
                case "week":
                    while (sDate.isBefore(fDate)){
                        dateList.add(format(sDate));
                        sDate = sDate.plusDays(7);
                }
                case "month":
                {
                    while (sDate.isBefore(fDate)){
                        dateList.add(format(sDate));
                        sDate = sDate.plusMonths(1);
                    }}
                default:{

                }
            }
        } else {
            for (int i=0; i<=10; i++){
                dateList.add(format(fDate));
                fDate = fDate.plusDays(1);
            }
        }
        return  dateList;
    }
    public List<String> get(){

        List<String> dateList = new ArrayList<>();
        LocalDate std = LocalDate.now();

            for (int i=0; i<=10; i++){
                dateList.add(format(std));
                std = std.minusDays(1);
            }
        return  dateList;
    }

    private LocalDate strToDate (@NotNull String str) {
        String [] strStd =str.split("\\.");
        return LocalDate.of(Integer.parseInt(strStd[2]), Integer.parseInt(strStd[1]), Integer.parseInt(strStd[0]));
    }

    private String format (@NotNull LocalDate date) {
        return String.format("%02d.%02d.%04d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }
}
