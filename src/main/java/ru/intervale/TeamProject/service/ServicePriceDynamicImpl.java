/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ImageBanner;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.service.bank.Bank;
import ru.intervale.TeamProject.service.bank.Currency;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.model.book.BookEntity;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Service change price.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServicePriceDynamicImpl implements ServicePriceDynamic {

    private Bank bank;
    private DatabaseAccess dto;
    private ServiceGenerateSvg serviceGenerateSvg;

    /**
     * Реализация: Виктор Дробышевский.
     */
    public ResponseEntity<?> getJson (String name, Currency currency, ParamRequest term) {
         return  ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(get(name, currency, term));
    }

    /**
     * Реализация: Дмитрий Самусев.
     */
    public ResponseEntity<?> getSvg (String name, Currency currency, ParamRequest term) throws IOException {
    serviceGenerateSvg.generateSvg(get(name, currency, term));
        return  ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body("SVGChart.svg");
    }

    /**
     * Реализация: Сергей Маевский.
     */
    public ResponseEntity<?> getCsv (String name, Currency currency, Map<String, String> term) {

        return  ResponseEntity.badRequest()
                .contentType(MediaType.TEXT_EVENT_STREAM) // Временный найти свой
                .body("Bad reques");
    }

    /**
     * Реализация: Игорь Прохорченко.
     */
    public ResponseEntity<?> getPdf (String name, Currency currency, Map<String, String> term) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
                .attachment()
                .filename("demo-file.pdf")
                .build()
                .toString()
        );
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(null);
    }

    private List<BookEntity> get(String name, Currency currency, ParamRequest term) {

        //Получение книг(и) из бд
        List<BookEntity> bookEntities = getBook(name);
        checkOnNull(bookEntities);

        //Получение курса валюты за период
        Map<LocalDateTime, BigDecimal> changePrice = getChangeCurrency(currency, term);

        //Расчёт изменение цены
        for (BookEntity book: bookEntities){
            book.setChangePrice(
                    sortByDate(
                            priceChangeCalculation(
                                    sortByDate(book.getPreviousBookPrice()),
                                    sortByDate(changePrice),
                                    book.getPrice()
                            )
                    )
            );
        }
        return bookEntities;
    }

    private Map<LocalDateTime, BigDecimal> getChangeCurrency(Currency currency, ParamRequest term) {
        return   bank.getExchangeRate(currency,term);
    }

    private List<BookEntity> getBook(String name) {
        return  dto.get(name);
    }

    //тут должен быть эксепшен
    private void checkOnNull(List<BookEntity> bookEntities) {
        if (bookEntities==null) throw new RuntimeException("Book not found");
    }

    private Map<LocalDateTime, BigDecimal> sortByDate(@NotNull Map<LocalDateTime, BigDecimal> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new
                        )
                );
    }

    private Map<LocalDateTime, BigDecimal> priceChangeCalculation (
            @NotNull Map<LocalDateTime, BigDecimal> priseMap,
            @NotNull Map<LocalDateTime, BigDecimal> currencyMap,
            BigDecimal priceNow
    ) {
        Map<LocalDateTime, BigDecimal>  changePrice =  new HashMap<>();

        Iterator<Map.Entry<LocalDateTime, BigDecimal>> entries = priseMap.entrySet().iterator();
        Map.Entry<LocalDateTime, BigDecimal> entry = entries.next();
        BigDecimal price = entry.getValue();

        for (Map.Entry<LocalDateTime, BigDecimal> prices : currencyMap.entrySet()){

            if (prices.getKey().isAfter(entry.getKey().minusDays(1))) {
                if (entries.hasNext()) {
                    entry = entries.next();
                    price = entry.getValue();

                } else {
                    price = priceNow;
                }
            }
            log.info(prices.getKey() +  " -- "
                    + price + " -- "
                    + prices.getValue()
            );
            changePrice.put(prices.getKey(),prices.getValue().multiply(price));
        }
        return changePrice;
    }
}
