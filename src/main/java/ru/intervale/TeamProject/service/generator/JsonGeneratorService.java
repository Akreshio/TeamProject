/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "27.03.2022, 21:19"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.PriceDynamicService;

import java.util.List;

public interface JsonGeneratorService {

    /**
     * Gets Json.
     *
     * Принимаем параметр списка книг из сервиса
     * @see PriceDynamicService
     *
     * Возвращаем лист BookDto для дальнейшей передачи в виде Json
     *
     * @param bookEntities the book entities
     * @return the List<BookDto>
     */

    List<BookDto> getJson(List<BookEntity> bookEntities);
}

