package ru.intervale.TeamProject.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ParamRequest {

    private LocalDateTime start;
    private LocalDateTime finish;
    private Period period;

    public ParamRequest(String start, String finish, Period period) {
        if (start!=null) this.start = strToDate(start);
        if (finish!=null) this.finish = strToDate(finish);
        if (period!=null) this.period = period;
    }

    private LocalDateTime strToDate (@NotNull String str) {
        String [] strStd =str.split("\\.");
        return LocalDateTime.of(Integer.parseInt(strStd[2]), Integer.parseInt(strStd[1]), Integer.parseInt(strStd[0]),0,0);
    }
}
