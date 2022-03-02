/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:51"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

public interface ServiceChangePrice {


    List<BookEntity> get (String name, int currency);


}
