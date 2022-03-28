/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:58"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.external.alfabank;

import ru.intervale.TeamProject.service.rate.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The interface Alfabank service.
 */
public interface AlfaBankService {

    /**
     * Передаём код валюты в банк и получаем значения (дата, множитель валютного перевода)
     * множитель валютного перевода = кол-во за единицу валюты / курс
     * Пример: RUB	643  0.035=3.5/100
     *
     * @param currency the currency
     * @param dates    the dates
     * @return the map
     */
    Map<LocalDateTime, BigDecimal> get(Currency currency, List<LocalDateTime> dates);

    /**
     * Gets now.
     *
     * @return the now
     */
    Map<String , BigDecimal> getNow();
}
