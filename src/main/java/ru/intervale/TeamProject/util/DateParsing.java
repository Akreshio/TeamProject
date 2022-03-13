package ru.intervale.TeamProject.util;

import ru.intervale.TeamProject.util.model.DayDateModel;

public class DateParsing {

    public static DayDateModel dayDateParsing(String date){
        String[] array = date.split("\\.");

        return new DayDateModel(
                Integer.parseInt(array[0]),
                Integer.parseInt(array[1]),
                Integer.parseInt(array[2])
        );
    }
}
