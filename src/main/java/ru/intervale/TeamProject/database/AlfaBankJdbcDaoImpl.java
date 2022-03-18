/*
 * @author S.Maevsky
 * @since 14.03.2022, 23:33
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.intervale.TeamProject.database.query.SqlQueryHolder;
import ru.intervale.TeamProject.mapper.RateRowMapper;
import ru.intervale.TeamProject.model.rate.RateEntity;
import ru.intervale.TeamProject.service.bank.Currency;
import ru.intervale.TeamProject.service.dao.AlfaBankDao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Alfa bank jdbc dao.
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AlfaBankJdbcDaoImpl implements AlfaBankDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RateRowMapper rateRowMapper;

    private final SqlQueryHolder sqlQueryHolder;

    @Override
    public List<RateEntity> getAll() throws DataAccessException {

        log.debug("Get all rates from database");

        List<RateEntity> rateEntityList = jdbcTemplate.query(sqlQueryHolder.getAllSql(), rateRowMapper);

        log.debug("Get all rates from database result {}", rateEntityList);

        return rateEntityList;
    }

    @Override
    public RateEntity getById(Long id) throws DataAccessException {

        log.debug("Get rate by id {}", id);

        SqlParameterSource param = new MapSqlParameterSource("id", id);
        RateEntity rateEntity = jdbcTemplate.queryForObject(sqlQueryHolder.getById(),
                param,
                rateRowMapper);

        log.debug("Get rate by id = {}, result = {}", id, rateEntity);

        return rateEntity;
    }

    @Override
    public Map<LocalDateTime, BigDecimal> getByPeriod(List<LocalDateTime> period, Currency currency)
            throws DataAccessException {

        log.debug("Get rate currency = {}, by period {}", currency, period);

        Map<LocalDateTime, BigDecimal> rateByPeriodMap;

        if (period != null && currency != null) {

            rateByPeriodMap = jdbcTemplate.query(sqlQueryHolder.getByPeriodSql(period, currency),
                    new ResultSetExtractor<>() {
                        @Override
                        public Map extractData(ResultSet rs) throws SQLException, DataAccessException {

                            HashMap<LocalDateTime, BigDecimal> mapReturn = new HashMap<>();

                            while (rs.next()) {

                                mapReturn.put(rs.getTimestamp("date").toLocalDateTime(),
                                        rs.getBigDecimal(currency.name().toLowerCase()));
                            }

                            return mapReturn;
                        }
                    });

            log.debug("Get rate currency = {}, by period  = {}, result = {}", currency, period, rateByPeriodMap);

            return rateByPeriodMap;
        }

        return Collections.emptyMap();
    }

    @Override
    public boolean save(RateEntity exchangeRate) throws DataAccessException {

        log.debug("Save exchange rate = {}", exchangeRate);

        if (exchangeRate != null) {

            MapSqlParameterSource params = convertToMapSql(exchangeRate);
            jdbcTemplate.update(sqlQueryHolder.saveSql(), params);

            log.debug("Save exchange rate = {} completed successfully.", exchangeRate);

            return true;
        }

        log.debug("Save exchange rate = {} not completed.", (Object) null);

        return false;
    }

    @Override
    public boolean save(LocalDateTime dateTime, Map<String, BigDecimal> exchangeRateMap) throws DataAccessException {

        log.debug("Save exchange rate by date = {}, rate = {}", dateTime, exchangeRateMap);

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (exchangeRateMap != null) {

            params.addValue("date", dateTime);
            params.addValues(exchangeRateMap);
            jdbcTemplate.update(sqlQueryHolder.saveByRateMapSql(exchangeRateMap), params);

            log.debug("Save exchange rate by date = {}, rate = {} completed successfully.", dateTime, exchangeRateMap);

            return true;
        }

        log.debug("Save exchange rate by date = {}, rate = {} not completed.", dateTime, null);

        return false;
    }

    @Override
    public boolean delete(LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {

        log.debug("Delete exchange rate by start date = {}, end date = {}", startDate, endDate);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", startDate);
        params.addValue("endDate", endDate);
        jdbcTemplate.update(sqlQueryHolder.deleteSql(), params);

        log.debug("Delete exchange rate by start date = {}, end date = {} completed successfully.", startDate, endDate);

        return true;
    }

    @NotNull
    private MapSqlParameterSource convertToMapSql(RateEntity exchangeRate) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date", exchangeRate.getDate());
        params.addValue("usd", exchangeRate.getUsd());
        params.addValue("eur", exchangeRate.getEur());
        params.addValue("rub", exchangeRate.getRub());

        return params;
    }
}
