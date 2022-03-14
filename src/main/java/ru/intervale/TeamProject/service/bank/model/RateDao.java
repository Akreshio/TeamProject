/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "10.03.2022, 1:23"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank.model;

import ru.intervale.TeamProject.service.bank.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


public class RateDao {

    LocalDate date;
    Map<Currency, BigDecimal> currency;

}
