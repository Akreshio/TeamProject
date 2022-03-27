package ru.intervale.TeamProject.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.PriceDynamicService;
import ru.intervale.TeamProject.service.rate.Currency;


import java.time.LocalDateTime;
import java.util.List;


/**
 * The type Book price api controller.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BookPriceController implements BookPrice {

    private PriceDynamicService service;

    private static final String TEXT_CSV = "text/csv";
    private static final String IMAGE_SVG = "image/svg";

    @Override
    public ResponseEntity<byte[]> getPdf(String name, Currency currency, String sStr, String fStr, Period d){

        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }
        log.debug("Get book: " + name + " in pdf");

        HttpHeaders httpHeaders = getHttpHeaders(MediaType.APPLICATION_PDF_VALUE, ".pdf");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        service.getPdf(name, currency, param)
                );
    }

    @Override
    public ResponseEntity<String> getCsv(String name, Currency currency, String sStr, String fStr, Period d){

        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }
        log.debug("Get book: " + name + " in csv");

        HttpHeaders httpHeaders = getHttpHeaders(TEXT_CSV, ".csv");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        service.getCsv(name, currency, param)
                );
    }

    @Override
    public ResponseEntity<byte[]> getSvg(String name, Currency currency, String sStr,String fStr, Period d){

        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }
        log.debug("Get book: " + name + " in svg");

        HttpHeaders httpHeaders = getHttpHeaders(IMAGE_SVG, ".svg");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        service.getSvg(name, currency, param)
                );
    }

    @Override
    public ResponseEntity<List<BookDto>> getJson(String name, Currency currency, String sStr, String fStr, Period d){

        ParamRequest param = null;
        if ((sStr!=null)||(fStr!=null)||(d!=null)){
            param = new ParamRequest(sStr, fStr, d);
        }
        log.debug("Get book: " + name + " in json");
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getJson(name, currency, param));
    }

    private HttpHeaders getHttpHeaders(String mediaType, String format) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, mediaType);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
                .attachment()
                .filename("price_change_report_" + LocalDateTime.now() + format)
                .build()
                .toString()
        );
        return httpHeaders;
    }
}
