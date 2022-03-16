/*
 * @author S.Maevsky
 * @since 14.03.2022, 23:38
 * @version V 1.0.0
 */


package ru.intervale.TeamProject.database.query.impl;

import org.springframework.stereotype.Component;
import ru.intervale.TeamProject.database.query.SqlQueryHolder;
import ru.intervale.TeamProject.service.bank.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * The type Sql query holder.
 */
@Component
public class SqlQueryHolderImpl implements SqlQueryHolder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String SELECT_RATES_SQL_QUERY = "SELECT * FROM rates ORDER BY date ASC";

    public static final String SELECT_RATE_BY_ID_SQL_QUERY =
            "SELECT * FROM rates WHERE id = :id";

    private static final String SELECT_RATES_BY_PERIOD_SQL_QUERY = "SELECT date, %s FROM rates WHERE ";

    private static final String INSERT_RATE_SQL_QUERY =
            "INSERT INTO rates (date, usd, eur, rub) VALUES (:date, :usd, :eur, :rub)";

    private static final String DELETE_RATES_BY_START_END_DATE_SQL_QUERY =
            "DELETE FROM rates WHERE date >= :startDate AND date <= :endDate";

    @Override
    public String getAllSql() {

        return SELECT_RATES_SQL_QUERY;
    }

    @Override
    public String getById() {

        return SELECT_RATE_BY_ID_SQL_QUERY;
    }

    @Override
    public String getByPeriodSql(List<LocalDateTime> period, Currency currency) {

        StringBuilder strBuilder =
                new StringBuilder(String.format(SELECT_RATES_BY_PERIOD_SQL_QUERY, currency.name().toLowerCase()));

        for (LocalDateTime dateTime : period) {

            strBuilder.append("date='").append(dateTime).append("' OR ");
        }

        strBuilder.delete(strBuilder.length() - 4, strBuilder.length());

        return strBuilder.toString();
    }

    @Override
    public String saveSql() {

        return INSERT_RATE_SQL_QUERY;
    }

    @Override
    public String saveByRateMapSql(Map<String, BigDecimal> exchangeRateMap) {

        StringBuilder sbSql = new StringBuilder("INSERT INTO rates (date");
        StringBuilder sbValue = new StringBuilder(" VALUES (:date");

        for (Map.Entry<String, BigDecimal> pair: exchangeRateMap.entrySet()) {

            sbSql.append(", ").append(pair.getKey().toLowerCase());
            sbValue.append(", :").append(pair.getKey().toLowerCase());
        }

        sbSql.append(")");
        sbValue.append(")");
        sbSql.append(sbValue);

        return sbSql.toString();
    }

    @Override
    public String deleteSql() {

        return DELETE_RATES_BY_START_END_DATE_SQL_QUERY;
    }
}
