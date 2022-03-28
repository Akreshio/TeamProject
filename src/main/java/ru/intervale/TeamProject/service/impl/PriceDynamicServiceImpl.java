/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "22.03.2022, 20:43"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.exception.BookNotFoundException;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.service.PriceDynamicService;
import ru.intervale.TeamProject.service.generator.CsvGeneratorService;
import ru.intervale.TeamProject.service.generator.JsonGeneratorService;
import ru.intervale.TeamProject.service.generator.PdfGeneratorService;
import ru.intervale.TeamProject.service.generator.SvgGeneratorService;
import ru.intervale.TeamProject.service.rate.Currency;
import ru.intervale.TeamProject.service.rate.changing.RateCurrencyChanging;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Service change price.
 */

@Slf4j
@Service
@AllArgsConstructor
public class PriceDynamicServiceImpl implements PriceDynamicService {

    private RateCurrencyChanging changing;
    private DatabaseAccess dto;

    private PdfGeneratorService pdfGenerator;
    private CsvGeneratorService csvGenerator;
    private SvgGeneratorService svgGenerator;
    private JsonGeneratorService jsonGenerator;
    /**
     * Реализация: Виктор Дробышевский.
     */
    @Override
    public List<BookDto> getJson(String name, Currency currency, ParamRequest term) {
        return jsonGenerator.getJson(
                getBookInfo(name, currency, term)
        );
    }

    /**
     * Реализация: Дмитрий Самусев.
     */
    @Override
    public byte[] getSvg(String name, Currency currency, ParamRequest term) {
        return svgGenerator.generateSvg(
                getBookInfo(name, currency, term),currency
        );
    }

    /**
     * Реализация: Сергей Маевский.
     */
    @Override
    public String getCsv(String name, Currency currency, ParamRequest term) {
        return csvGenerator.getCsv(getBookInfo(name, currency, term));
    }


    /**
     * Реализация: Игорь Прохорченко.
     */
    @Override
    public byte[] getPdf (String name, Currency currency, ParamRequest term) {
        return pdfGenerator.getPdf(
                getBookInfo(name, currency, term)
        );
    }

    private List<BookEntity> getBookInfo(String title, Currency currency, ParamRequest term) {

        //Получение книг(и) из бд
        log.debug("Получение книг(и) из бд");
        List<BookEntity> bookEntities = dto.get(title);
        checkOnNull(bookEntities);

        //Получение курса валюты за период
        log.debug("Получение курса валюты за период {" + currency + "} {" + term +"}");
        Map<LocalDateTime, BigDecimal> changePrice = changing.getExchangeRate(currency,term);

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


    private void checkOnNull(List<BookEntity> bookEntities) {
        if (bookEntities == null) throw new BookNotFoundException(", nothing found for your request");
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
            changePriceBook.put(
                    prices.getKey(),
                    price.divide(prices.getValue(), 2, RoundingMode.HALF_UP)
            );
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
            changePriceBook.put(
                    prices.getKey(),
                    priceNow.divide(prices.getValue(),2, RoundingMode.HALF_UP)
            );
        }
        return changePriceBook;
    }
}
