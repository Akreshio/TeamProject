/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "09.03.2022, 19:31"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface Bank {



    public Map<String, BigDecimal> getExchangeRate (Currency currency, Map<String, String> term);

    public void  requestCurrentRate ();

     Map<String, BigDecimal> hour (Currency currency, List<String> time);

    Map<String, BigDecimal> day (Currency currency, List<String> time);

    Map<String, BigDecimal> month (Currency currency, List<String> time);
}
