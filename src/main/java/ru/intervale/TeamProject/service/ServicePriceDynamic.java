/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import org.springframework.http.ResponseEntity;
import ru.intervale.TeamProject.service.bank.Currency;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;

import java.util.Map;

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
     * @see AlfabankService
     *
     * @param name     the name
     * @param currency the currency
     * @return the list
     */
    ResponseEntity<?> getJson (String name, Currency currency, ParamRequest term);
    ResponseEntity<?> getSvg (String name, Currency currency, Map<String, String> term);
    ResponseEntity<?> getCsv (String name, Currency currency, Map<String, String> term);
    ResponseEntity<?> getPdf (String name, Currency currency, Map<String, String> term);
}
