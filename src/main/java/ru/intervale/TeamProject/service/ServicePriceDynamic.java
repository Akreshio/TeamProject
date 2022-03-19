/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import org.springframework.http.ResponseEntity;
import ru.intervale.TeamProject.service.rateCurrencyChanging.Currency;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.external.alfabank.AlfaBankService;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;

import java.util.List;

/**
 * The interface Service change price.
 */
public interface ServicePriceDynamic {


    /**
     * Принемаем параметры из контроллера
     *
     * Получаем список книг
     * @see DatabaseAccess
     * Получаем изменение валют за период
     * @see AlfaBankService
     *
     * @param name     the name
     * @param currency the currency
     * @return the list
     */
    ResponseEntity<List<BookEntity>> getJson (String name, Currency currency, ParamRequest term);
    ResponseEntity<byte[]> getSvg (String name, Currency currency, ParamRequest term);
    ResponseEntity<String> getCsv (String name, Currency currency, ParamRequest term);
    ResponseEntity<byte[]> getPdf (String name, Currency currency, ParamRequest term);
}
