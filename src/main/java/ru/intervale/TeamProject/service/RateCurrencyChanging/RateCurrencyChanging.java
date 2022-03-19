/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "09.03.2022, 19:31"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.RateCurrencyChanging;

import ru.intervale.TeamProject.model.request.ParamRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public interface RateCurrencyChanging {


    Map<LocalDateTime, BigDecimal> getExchangeRate (Currency currency, ParamRequest term);

}
