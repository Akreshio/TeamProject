/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:58"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.external.alfabank;

import ru.intervale.TeamProject.service.bank.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The interface Alfabank service.
 */
public interface AlfabankService {

    /**
     * Передаём код валюты в банк и получаем значения (дата, множитель валютного перевода)
     *
     *  множитель валютного перевода = кол-во за единицу валюты / курс
     *  Пример: RUB	643  28.5714=100/3.5
     *
     * @param currency the currency
     * @return the map
     */
    Map<LocalDateTime, BigDecimal> get(Currency currency, List<LocalDateTime> dates);
    Map<String , BigDecimal> getNow();
}
