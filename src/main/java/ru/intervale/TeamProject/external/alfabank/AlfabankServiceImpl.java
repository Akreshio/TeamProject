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

    @Override
    public Map<String, BigDecimal> get(int currency) {

        List <String> date = new ArrayList<>();
        date.add("17.01.2022");
        date.add("07.01.2022");

        Map<String, BigDecimal> changePrice = new HashMap<>();

        for (String d: date) {
            NationalRateListResponse rateList = restTemplate.getForEntity(urn, NationalRateListResponse.class, d, currency).getBody();

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

