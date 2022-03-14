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
import ru.intervale.TeamProject.service.bank.Currency;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AlfabankServiceImpl implements AlfabankService {

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
    public Map<String, BigDecimal> get(Currency currency,  List <String> date) {

        Map<String, BigDecimal> changePrice = new HashMap<>();

        for (String d: date) {
            NationalRateListResponse rateList = restTemplate.getForEntity(
                    urn,
                    NationalRateListResponse.class,
                    d,
                    currency.getCode()
            ).getBody();

            if (rateList!=null) {
                for (NationalRate rate : rateList.getRates()) {
                    BigDecimal quantity = BigDecimal.valueOf(rate.getQuantity());
                    changePrice.put(rate.getDate(), quantity.divide(rate.getRate(), 5, RoundingMode.HALF_UP));
                }
            }
        }
        return changePrice;
    }
    @Override
    public Map<Currency, BigDecimal> getNow() {
        Map<Currency, BigDecimal> exchangeRateChange = new HashMap<>();
        RateListResponse rateList = restTemplate.getForEntity(urnNow, RateListResponse.class).getBody();

        for (Rate rate:rateList.getRates()) {
            if (rate.getName()!=null) {
                Currency currency = Currency.valueOf(rate.getSellIso());
                exchangeRateChange.put(currency,rate.getSellRate());
            }

        }


        return exchangeRateChange;
    }
}

