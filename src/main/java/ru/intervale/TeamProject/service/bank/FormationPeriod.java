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
     *     std, date  - дата начала формирования
     *     find, date - дата окончания формирования
     *     per, enum - перечисление вида (месяц, неделя, день)
     *
     * @return the list
     */
    public List<String> get(Map <String, String> paramMap){

        List<String> dateList = new ArrayList<>();

        LocalDate std = LocalDate.now();
        LocalDate find;

        if (paramMap.get("find")!=null){
            std = strToDate(paramMap.get("find"));
        }
        if (paramMap.get("std")!=null){
            find = strToDate(paramMap.get("std"));
        } else { find = std.minusDays(10);}

        if (paramMap.get("per")!=null){
            switch (paramMap.get("per")) {
                case "day":
                    while (find.isBefore(std)){
                        dateList.add(format(std));
                        std = std.minusDays(1);
                    }
                case "week":
                    while (find.isBefore(std)){
                        dateList.add(format(std));
                        std = std.minusDays(7);
                }
                case "month":
                {
                    while (find.isBefore(std)){
                        dateList.add(format(std));
                        std = std.minusMonths(1);
                    }}
                default:{

                }
            }
        } else {
            for (int i=0; i<=10; i++){
                dateList.add(format(std));
                std = std.minusDays(1);
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
