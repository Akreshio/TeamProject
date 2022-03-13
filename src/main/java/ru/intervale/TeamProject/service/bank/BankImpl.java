/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "09.03.2022, 19:42"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.bank;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BankImpl implements Bank {


    private AlfabankService alfabank;
    private FormationPeriod formationPeriod;

    @Override
    public Map<String, BigDecimal> getExchangeRate(Currency currency, Map<String, String> paramMap) {

        if (paramMap!=null) {
            if (paramMap.get("per")!=null){
                switch (paramMap.get("per")) {
                    case "hour":
                        return null;
                    case "day":
                    case "week":
                    case "month":
                        return alfabank.get(currency,formationPeriod.get(paramMap));
                    default:
                        return alfabank.get(currency,formationPeriod.get());
                }
            } else {
                return alfabank.get(currency,formationPeriod.get());
            }
        }
            return alfabank.get(currency,formationPeriod.get());
        }


    @Override
    public void requestCurrentRate() {

    }

    @Override
    public Map<String, BigDecimal> hour(Currency currency, List<String> time) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> day(Currency currency, List<String> time) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> month(Currency currency, List<String> time) {
        return null;
    }



}
