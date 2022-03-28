/*
 * @author S.Maevsky
 * @since 23.03.2022, 0:20
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.belapb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.intervale.TeamProject.external.belapb.model.DailyExRates;
import ru.intervale.TeamProject.external.belapb.model.RateBelApb;
import ru.intervale.TeamProject.service.external.alfabank.BelApbExchangeClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
public class BelApbExchangeClientImpl implements BelApbExchangeClient {

    private final RestTemplate restTemplate;

    private static final String END_POINT_GET_BY_DATE = "/CashExRatesDaily.php?ondate={date}";

    private static final String BANK_ID = "8270270000";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public BelApbExchangeClientImpl(@Qualifier("belAgroPromBank") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, BigDecimal> getRatesByDate(LocalDateTime dateTime) {

        DailyExRates dailyExRates = tryGetRatesBelApbByDate(dateTime);
        List<RateBelApb> rateList = getRatesByBankId(dailyExRates);

        return rateListToMap(rateList);
    }

    private DailyExRates tryGetRatesBelApbByDate(LocalDateTime dateTime) {

        DailyExRates response;
        Map<String, String> uriParameter = new HashMap<>();
        uriParameter.put("date", dateToString(dateTime));

        try {

            response = restTemplate.getForObject(END_POINT_GET_BY_DATE,
                    DailyExRates.class, uriParameter);

            return response;

        } catch (RestClientException ex) {

            log.info("Exception: {}", ex.getStackTrace());

            log.debug("Exception when handling get rates from belapb.by by date: {}", dateTime);
            throw new RestClientException("Exception when handling get rates from belapb.by. " +
                    "Please try again later.");
        }
    }

    private String dateToString(LocalDateTime dateTime) {

        return dateTime.format(DATE_TIME_FORMATTER);
    }

    private List<RateBelApb> getRatesByBankId(DailyExRates response) {

        List<RateBelApb> currencyList = new ArrayList<>();

        if (response != null) {

            currencyList = response.getRateBelApbList()
                    .stream()
                    .filter(x -> x.getBankId().equals(BANK_ID))
                    .collect(Collectors.toList());
        }

        return currencyList;
    }

    private Map<String, BigDecimal> rateListToMap(List<RateBelApb> rateList) {

        Map<String, BigDecimal> rateMap = new HashMap<>();

        if (rateList != null && !rateList.isEmpty()) {

            putRatesIntoMap(rateList, rateMap);
        }

        return rateMap;
    }

    private void putRatesIntoMap(List<RateBelApb> rateList, Map<String, BigDecimal> rateMap) {

        for (RateBelApb rate : rateList) {

            BigDecimal rateBuy =
                    rate.getRateBuy().divide(new BigDecimal(rate.getScale()), 5, RoundingMode.HALF_UP);
            rateMap.put(rate.getCharCode(), rateBuy);
        }
    }
}
