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

package ru.intervale.TeamProject.external.alfabank;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@NoArgsConstructor
public class FormationPeriod {

    private GregorianCalendar calendar = new GregorianCalendar();
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public List<String> get(){

        List<String> dateList = new ArrayList<>();

        for (int i=0; i<10; i++){
            if(calendar.isWeekDateSupported()) {
                dateList.add(format(calendar.getTime()));
            }
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        return  dateList;
    }


    private String format (@NotNull Date date) {
        return dateFormat.format(date.getTime());
    }
}
