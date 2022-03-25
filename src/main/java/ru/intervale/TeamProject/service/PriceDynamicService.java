/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import ru.intervale.TeamProject.service.rate.Currency;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;

import java.util.List;

/**
 * The interface Service change price.
 */
public interface PriceDynamicService {

    /**
     * Gets json.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the json
     */
    List<BookEntity> getJson (String name, Currency currency, ParamRequest term);

    /**
     * Gets svg.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the svg
     */
    byte[] getSvg (String name, Currency currency, ParamRequest term);

    /**
     * Gets csv.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the csv
     */
    String getCsv (String name, Currency currency, ParamRequest term);

    /**
     * Gets pdf.
     *
     * @param name     the title book
     * @param currency the currency
     * @param term     the term
     * @return the pdf
     */
    byte[] getPdf (String name, Currency currency, ParamRequest term);
}
