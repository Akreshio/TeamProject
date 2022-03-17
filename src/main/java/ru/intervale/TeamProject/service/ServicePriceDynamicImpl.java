/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import lombok.extern.slf4j.Slf4j;
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
import ru.intervale.TeamProject.service.generatepdf.PDFGenerator;
import ru.intervale.TeamProject.service.generatepdf.PDFGeneratorService;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Bank bank;
    private DatabaseAccess dto;
    private PDFGeneratorService pdfGenerator;


    /**
     * Реализация: Виктор Дробышевский.
     */

    @Override
    public ResponseEntity<?> getJson(String name, Currency currency, ParamRequest term) {
        return  ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(get(name, currency, term));
    }

    /**
     * Реализация: Дмитрий Самусев.
     */
    public ResponseEntity<?> getSvg (String name, Currency currency, Map<String, String> term) {

        return  ResponseEntity.badRequest()
                .contentType(MediaType.IMAGE_PNG) // Временный найти свой
                .body("Bad reques");
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
     * @return
     */
    public ResponseEntity getPdf (String name, Currency currency, ParamRequest term) {

        List<BookEntity> bookEntities = get(name, currency, term);
        ByteArrayInputStream book = null;
        try {
            book = pdfGenerator.getPdf(bookEntities);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
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
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(book));
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
