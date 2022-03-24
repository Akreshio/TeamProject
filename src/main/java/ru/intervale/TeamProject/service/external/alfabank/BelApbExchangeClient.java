/*
 * @author S.Maevsky
 * @since 24.03.2022, 13:23
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.external.alfabank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public interface BelApbExchangeClient {

    Map<String, BigDecimal> getRatesByDate(LocalDateTime dateTime);

}
