/*
 * @author
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:39"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.rateCurrencyChanging.Currency;
import ru.intervale.TeamProject.service.ServicePriceDynamic;

import java.util.List;

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
    public ResponseEntity<List<BookEntity>> getJson(String name, Currency currency, String sStr, String fStr, Period d) {
        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }

        return  service.getJson(name, currency, param);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(String name, Currency currency, String sStr,String fStr, Period d) {
        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }

        return  service.getPdf(name, currency, param);
    }

    @Override
    public ResponseEntity<byte[]> getSvg(String name, Currency currency, String sStr,String fStr, Period d) {
        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }

        return  service.getSvg(name, currency, param);
    }



    @Override
    public ResponseEntity<String> getCsv(String name, Currency currency, String sStr, String fStr, Period d) {
        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }

        return  service.getCsv(name, currency, param);
    }
}
