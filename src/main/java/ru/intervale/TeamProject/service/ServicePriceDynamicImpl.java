/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.intervale.TeamProject.service.rateCurrencyChanging.Currency;
import ru.intervale.TeamProject.service.rateCurrencyChanging.RateCurrencyChanging;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.generator.ResponseGenerator;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Service change price.
 */

@Slf4j
@Service
@AllArgsConstructor
public class ServicePriceDynamicImpl implements ServicePriceDynamic {

    private RateCurrencyChanging changing;
    private DatabaseAccess dto;
    private ResponseGenerator generator;

    private static final String TEXT_CSV = "text/csv";

    /**
     * Реализация: Виктор Дробышевский.
     */
    @Override
    public ResponseEntity<List<BookEntity>> getJson(String name, ru.intervale.TeamProject.service.rateCurrencyChanging.Currency currency, ParamRequest term) {
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBookInfo(name, currency, term));
    }

    /**
     * Реализация: Дмитрий Самусев.
     */
    @Override
    public ResponseEntity<byte[]> getSvg (String name, ru.intervale.TeamProject.service.rateCurrencyChanging.Currency currency, ParamRequest term) {

        HttpHeaders httpHeaders = getHttpHeaders(TEXT_CSV, ".svg");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        generator.generationSvg(
                                getBookInfo(name, currency, term)
                        )
                );
    }

    /**
     * Реализация: Сергей Маевский.
     */
    @Override
    public ResponseEntity<String> getCsv(String name, ru.intervale.TeamProject.service.rateCurrencyChanging.Currency currency, ParamRequest term) {

        HttpHeaders httpHeaders = getHttpHeaders(TEXT_CSV, ".csv");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        generator.generationCsv(
                                getBookInfo(name, currency, term)
                        )
                );
    }


    /**
     * Реализация: Игорь Прохорченко.
     */
    @Override
    public ResponseEntity<byte[]> getPdf (String name, ru.intervale.TeamProject.service.rateCurrencyChanging.Currency currency, ParamRequest term) {

        HttpHeaders httpHeaders = getHttpHeaders(MediaType.APPLICATION_PDF_VALUE, ".pdf");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        generator.generationPdf(
                                getBookInfo(name, currency, term)
                        )
                );
    }

    private List<BookEntity> getBookInfo(String name, ru.intervale.TeamProject.service.rateCurrencyChanging.Currency currency, ParamRequest term) {

        //Получение книг(и) из бд
        log.debug("Получение книг(и) из бд");
        List<BookEntity> bookEntities = getBook(name);
        checkOnNull(bookEntities);

        //Получение курса валюты за период
        log.debug("Получение курса валюты за период {" + currency + "} {" + term.toString() +"}");
        Map<LocalDateTime, BigDecimal> changePrice = getChangeCurrency(currency, term);

        //Расчёт изменение цены
        log.debug("Расчёт изменение цены");
        for (BookEntity book: bookEntities){
            if(book.getPreviousBookPrice()!=null) {
                book.setChangePrice(
                        sortByDate(
                                priceChangeCalculation(
                                        sortByDate(book.getPreviousBookPrice()),
                                        sortByDate(changePrice),
                                        book.getPrice()
                                )
                        )
                );
            } else {
                book.setChangePrice(
                        sortByDate(
                                priceChangeCalculation(
                                        sortByDate(changePrice),
                                        book.getPrice()
                                )
                        )
                );
            }
        }
        return bookEntities;
    }


    private Map<LocalDateTime, BigDecimal> getChangeCurrency(Currency currency, ParamRequest term) {
        return changing.getExchangeRate(currency,term);
    }

    private List<BookEntity> getBook(String title) {
        return dto.get(title);
    }

    //тут должен быть эксепшен
    private void checkOnNull(List<BookEntity> bookEntities) {
        if (bookEntities == null) throw new RuntimeException("Book not found");
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

    private Map<LocalDateTime, BigDecimal> priceChangeCalculation(
            @NotNull Map<LocalDateTime, BigDecimal> priseMap,
            @NotNull Map<LocalDateTime, BigDecimal> currencyMap,
            BigDecimal priceNow
    ) {
        // Создаём Мар для результата обработки
        Map<LocalDateTime, BigDecimal>  changePriceBook =  new HashMap<>();

        // Итератор по динамике изменения цены книги
        Iterator<Map.Entry<LocalDateTime, BigDecimal>> iteratorPrise = priseMap.entrySet().iterator();
        // Получение даты,цены книги на определённый момент
        Map.Entry<LocalDateTime, BigDecimal> changePrice = iteratorPrise.next();
        // Установка цены книги на начало выборки
        BigDecimal price = changePrice.getValue();

        // цикл по дням изменения курса валют
        for (Map.Entry<LocalDateTime, BigDecimal> prices : currencyMap.entrySet()){
            // Если дата изменения цены книги равна или больше даты курса
            if (prices.getKey().isAfter(changePrice.getKey().minusDays(1))) {
                // Есть ли следующее изменение цены
                if (iteratorPrise.hasNext()) {
                    changePrice = iteratorPrise.next();
                    price = changePrice.getValue();
                }
                // Иначе установить актуальную цену книги
                else {
                    price = priceNow;
                }
            }
            // логирование: дата курса -- цена книги -- курс
            log.debug(prices.getKey() +  " -- "
                    + price + " -- "
                    + prices.getValue()
            );
            //расчёт и запись данных в результирующую Мар
            changePriceBook.put(prices.getKey(),prices.getValue().multiply(price));
        }
        return changePriceBook;
    }

    private Map<LocalDateTime, BigDecimal> priceChangeCalculation (
            @NotNull Map<LocalDateTime, BigDecimal> currencyMap,
            BigDecimal priceNow
    ) {
        // Создаём Мар для результата обработки
        Map<LocalDateTime, BigDecimal>  changePriceBook =  new HashMap<>();

        // цикл по дням изменения курса валют
        for (Map.Entry<LocalDateTime, BigDecimal> prices : currencyMap.entrySet()){
            //расчёт и запись данных в результирующую Мар по актуальной цене книги
            changePriceBook.put(prices.getKey(),prices.getValue().multiply(priceNow));
        }
        return changePriceBook;
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
