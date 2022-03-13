/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "09.03.2022, 23:22"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.dao;

import ru.intervale.TeamProject.service.bank.Currency;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

public interface DatabaseBank {

    Map<String, BigDecimal> get(Currency currency, String table, Date sDay, Date fDay);

    boolean add (String table, Map<String, BigDecimal> exchangeRate);
    boolean delete (String table, Map<String, BigDecimal> exchangeRate);
    boolean remove (String table);

}
