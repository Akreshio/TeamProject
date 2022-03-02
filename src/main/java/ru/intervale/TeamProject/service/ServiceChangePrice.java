/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import ru.intervale.TeamProject.DAO.DatabaseAccess;
import ru.intervale.TeamProject.external.alfabank.AlfabankService;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

/**
 * The interface Service change price.
 */
public interface ServiceChangePrice {


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
    List<BookEntity> get (String name, int currency);


}
