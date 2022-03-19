/*
 * @author S.Maevsky
 * @since 14.03.2022, 23:37
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.database.query;


import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The interface Sql query holder.
 */
public interface SqlQueryHolder {

    /**
     * Sql запрос: получение всех данных по курсам валют из базы данных.
     *
     * @return the all sql
     */
    String getAllSql();

    /**
     * Sql запрос: получение данных по курсам валют по id.
     *
     * Получение данных по курсам валют по указанной валюте по списоку дат со временем.
     *
     * @return the by id
     */
    String getById();

    /**
     * Sql запрос: получение данных по курсам валют по указанной валюте по списоку дат со временем.
     *
     * @param period   the period
     * @param currency the currency
     * @return the by period sql
     */
    String getByPeriodSql(List<LocalDateTime> period, Currency currency);

    /**
     * Sql запрос: сохранение курсов валют.
     *
     * @return the string
     */
    String saveSql();

    /**
     * Sql запрос: сохранение курсов валют по дате и Map-е с типом валюты и курсом.
     *
     * @param exchangeRateMap the exchange rate map
     * @return the string
     */
    String saveByRateMapSql(Map<String, BigDecimal> exchangeRateMap);

    /**
     * Sql запрос для удаления с начала периода по дате и времени включительно и до окончания включительно.
     *
     * @return the string
     */
    String deleteSql();
}
