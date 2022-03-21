/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import org.springframework.http.ResponseEntity;
import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.external.alfabank.AlfaBankService;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;

import java.io.IOException;

import java.util.List;

/**
 * The interface Service change price.
 */
public interface ServicePriceDynamic {

    /**
     * Gets json.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the json
     */
    ResponseEntity<List<BookEntity>> getJson (String name, Currency currency, ParamRequest term);

    /**
     * Gets svg.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the svg
     */
    ResponseEntity<byte[]> getSvg (String name, Currency currency, ParamRequest term);

    /**
     * Gets csv.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the csv
     */
    ResponseEntity<String> getCsv (String name, Currency currency, ParamRequest term);

    /**
     * Gets pdf.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the pdf
     */
    ResponseEntity<byte[]> getPdf (String name, Currency currency, ParamRequest term);
}
