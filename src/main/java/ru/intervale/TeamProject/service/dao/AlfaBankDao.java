/*
 * @author S.Maevsky
 * @since 14.03.2022, 23:13
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.dao;

import org.springframework.dao.DataAccessException;
import ru.intervale.TeamProject.model.rate.RateEntity;
import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The interface Alfa bank dao.
 */
public interface AlfaBankDao {

    /**
     * Получение всех данных по курсам валют из базы данных.
     *
     * @return the all
     * @throws DataAccessException the data access exception
     */
    List<RateEntity> getAll() throws DataAccessException;

    /**
     * Получение данных по курсам валют по id.
     *
     * @param id the id
     * @return the by id
     * @throws DataAccessException the data access exception
     */
    RateEntity getById(Long id) throws DataAccessException;

    /**
     * Получение данных по курсам валют по указанной валюте по списоку дат со временем.
     *
     * @param period   the period
     * @param currency the currency
     * @return the by period
     * @throws DataAccessException the data access exception
     */
    Map<LocalDateTime, BigDecimal> getByPeriod(List<LocalDateTime> period, Currency currency)
            throws DataAccessException;

    /**
     * Сохранение курсов валют.
     *
     * @param exchangeRate the exchange rate
     * @return the boolean
     * @throws DataAccessException the data access exception
     */
    boolean save(RateEntity exchangeRate) throws DataAccessException;

    /**
     * Сохранение курсов валют по дате и Map-е с типом валюты и курсом.
     *
     * @param dateTime        the date time
     * @param exchangeRateMap the exchange rate map
     * @return the boolean
     * @throws DataAccessException the data access exception
     */
    boolean save(LocalDateTime dateTime, Map<String, BigDecimal> exchangeRateMap) throws DataAccessException;

    /**
     * Удаление курсов валют с даты и времени начала периода включительно
     * и по дату и время окончания периода включительно.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     * @throws DataAccessException the data access exception
     */
    boolean delete(LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException;

}
