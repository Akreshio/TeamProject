/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "22.03.2022, 20:58"
 * @version V 1.0.0
 */

/*
 * @author S.Maevsky
 * @since 15.03.2022, 0:14
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.database.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.intervale.TeamProject.database.entity.RateEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Rate row mapper.
 */
@Component
public class RateRowMapper implements RowMapper<RateEntity> {

    @Override
    public RateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        RateEntity rate = new RateEntity();
        rate.setId(rs.getLong("id"));
        rate.setDate(rs.getTimestamp("date").toLocalDateTime());
        rate.setUsd(rs.getBigDecimal("usd"));
        rate.setEur(rs.getBigDecimal("eur"));
        rate.setRub(rs.getBigDecimal("rub"));

        return rate;
    }
}
