/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.alfabank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.TeamProject.external.alfabank.model.NationalRate;
import ru.intervale.TeamProject.external.alfabank.model.NationalRateListResponse;
import ru.intervale.TeamProject.external.alfabank.model.Rate;
import ru.intervale.TeamProject.external.alfabank.model.RateListResponse;
import ru.intervale.TeamProject.service.rateCurrencyChanging.Currency;
import ru.intervale.TeamProject.service.external.alfabank.AlfaBankService;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AlfaBankServiceImpl implements AlfaBankService {

    /**
     * The Rest template .
     */
    @Autowired
    @Qualifier("alfaBank")
    private RestTemplate restTemplate;

    @Value("${rest.template.alfabank.urn}")
    private String urn;
    @Value("${rest.template.alfabank.urn.now}")
    private String urnNow;

    @Override
    public Map<LocalDateTime, BigDecimal> get(Currency currency,  List <LocalDateTime> dates) {

        Map<LocalDateTime, BigDecimal> changePrice = new HashMap<>();
        for (LocalDateTime date: dates) {
            String d = formatDate(date);
            NationalRateListResponse rateList = restTemplate.getForEntity(
                    urn,
                    NationalRateListResponse.class,
                    d,
                    currency.getCode()
            ).getBody();

            if (rateList!=null) {
                for (NationalRate rate : rateList.getRates()) {
                    BigDecimal quantity = BigDecimal.valueOf(rate.getQuantity());
                    changePrice.put(strToDate(rate.getDate()), quantity.divide(rate.getRate(), 5, RoundingMode.HALF_UP));
                }
            }
        }
        return changePrice;
    }
    @Override
    public Map<String, BigDecimal> getNow() {
        Map<String, BigDecimal> exchangeRateChange = new HashMap<>();
        RateListResponse rateList = restTemplate.getForEntity(urnNow, RateListResponse.class).getBody();

        if (rateList != null){
            for (Rate rate:rateList.getRates()) {
                if (rate.getName() != null) {
                    String currency = rate.getSellIso().toLowerCase();
                    exchangeRateChange.put(currency, rate.getSellRate());
                }
            }
        }
        return exchangeRateChange;
    }
    private String formatDate (@NotNull LocalDateTime date) {
        return String.format("%02d.%02d.%04d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }
    private LocalDateTime strToDate (@NotNull String str) {
        String [] strStd =str.split("\\.");
        return LocalDateTime.of(Integer.parseInt(strStd[2]), Integer.parseInt(strStd[1]), Integer.parseInt(strStd[0]),0,0);
    }
}

