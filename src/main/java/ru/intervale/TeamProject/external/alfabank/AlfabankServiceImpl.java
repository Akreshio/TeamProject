/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.alfabank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.TeamProject.external.alfabank.model.NationalRate;
import ru.intervale.TeamProject.external.alfabank.model.NationalRateListResponse;
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


    @Override
    public Map<String, BigDecimal> get(int currency) {

       /*
       https://ibapi.alfabank.by:8273/partner/1.0.1/public/nationalRates?date=17.01.2022&currencyCode=840'
       */

        List <String> date = new ArrayList<>();
        date.add("17.01.2022");
        date.add("07.01.2022");

        Map<String, BigDecimal> changePrice = new HashMap<>();

        for (String d: date) {
            String URI = "partner/1.0.1/public/nationalRates?date=" + d + "&currencyCode=" + currency;
            NationalRateListResponse rateList = restTemplate.getForEntity(URI, NationalRateListResponse.class).getBody();

            if (rateList!=null) {
                for (NationalRate rate : rateList.getRates()) {
                    BigDecimal quantity = BigDecimal.valueOf(rate.getQuantity());
                    changePrice.put(d, quantity.divide(rate.getRate(), 5, RoundingMode.HALF_UP));
                }
            }
        }
        return changePrice;
    }
}

