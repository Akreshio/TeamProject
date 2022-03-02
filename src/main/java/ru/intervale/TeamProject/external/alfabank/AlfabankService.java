/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:58"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.alfabank;

import java.math.BigDecimal;
import java.util.Map;

public interface AlfabankService {

    Map<String, BigDecimal> get(BigDecimal price, int currency);

}
