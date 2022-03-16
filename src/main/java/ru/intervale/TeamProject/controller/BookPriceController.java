/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:39"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.TeamProject.service.bank.Currency;
import ru.intervale.TeamProject.service.ServicePriceDynamic;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Book price api controller.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BookPriceController implements BookPrice {

    private ServicePriceDynamic service;

    // http://localhost:8080/price/stat?name=The test book&currency=EUR

    @Override
    public ResponseEntity<?> getJson(String name, Currency currency, String sStr,String fStr, Period d) {
        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }

        return  service.getJson(name, currency, paramMap);
    }

    @Override
    public ResponseEntity<?> getPdf(String name, Currency currency) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sDate", "01.01.2021");
        paramMap.put("fDate", "03.03.2022");
        paramMap.put("per","day");

        return  service.getPdf(name, currency, paramMap);
    }

    @Override
    public ResponseEntity<?> getCsv(String name, Currency currency) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sDate", "01.02.2022");
        paramMap.put("fDate", "03.03.2022");
        paramMap.put("per","day");

        return  service.getCsv(name, currency, paramMap);
    }
}
