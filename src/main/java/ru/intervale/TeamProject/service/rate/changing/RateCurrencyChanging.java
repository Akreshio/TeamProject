/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "22.03.2022, 20:47"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.rate.changing;

import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.service.rate.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * The interface Rate currency changing.
 */
public interface RateCurrencyChanging {


    /**
     * Gets exchange rate.
     *
     * @param currency the currency
     * @param term     the term
     * @return the exchange rate
     */
    Map<LocalDateTime, BigDecimal> getExchangeRate (Currency currency, ParamRequest term);

}
