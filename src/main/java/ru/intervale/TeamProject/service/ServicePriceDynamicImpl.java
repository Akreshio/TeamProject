/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.bank.Bank;
import ru.intervale.TeamProject.service.bank.Currency;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.generatepdf.PDFGenerateServiceImpl;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Service change price.
 */
@Service
@AllArgsConstructor
public class ServicePriceDynamicImpl implements ServicePriceDynamic {

    private Bank bank;
    private DatabaseAccess dto;
    private PDFGenerateServiceImpl pdfGenerateService;

    /**
     * Реализация: Виктор Дробышевский.
     */
    public ResponseEntity<?> getJson (String name, Currency currency, Map<String, String> term) {
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
                .body(pdfGenerateService.getPdf(name, currency, term));
    }


    @Override
    public List<BookEntity> get(String name, Currency currency, Map<String, String> term) {

        List<BookEntity> bookEntities = getBook(name); // достаём книги из бд
        Map<String, BigDecimal> changePrice = getChangePrice(currency, term); // получаем курс валют за период

        for (BookEntity book: bookEntities) {
            for (Map.Entry<String, BigDecimal> rate :  changePrice.entrySet()) {
                //задаём изменение цены на книгу
                changePrice.put(rate.getKey(),book.getPrice().multiply(rate.getValue()));
            }

            Map<String, BigDecimal> newMapSortedByKey = changePrice.entrySet().stream()
                    .sorted((e1,e2) -> strToDate(e1.getKey()).compareTo(strToDate(e2.getKey())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            book.setChangePrice(newMapSortedByKey);
        }
        return bookEntities;
    }

    private Map<String, BigDecimal> getChangePrice(Currency currency, Map<String, String> term) {
        return   bank.getExchangeRate(currency,term);
    }

    private List<BookEntity> getBook(String name) {
        return  dto.get(name);
    }

    private LocalDate strToDate (@NotNull String str) {
        String [] strStd =str.split("\\.");
        return LocalDate.of(Integer.parseInt(strStd[2]), Integer.parseInt(strStd[1]), Integer.parseInt(strStd[0]));
    }

    //тут должен быть эксепшен
    private void checkOnNull(List<BookEntity> bookEntities) {
        if (bookEntities==null) throw new RuntimeException("Book not found");
    }
}
