/*
 * @author S.Maevsky
 * @since 14.03.2022, 14:25
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.PriceDynamicService;

import java.util.List;

/**
 * The interface Csv generator service.
 */
public interface CsvGeneratorService {

    /**
     * Gets csv.
     *
     * Принимаем параметр списка книг из сервиса
     * @see PriceDynamicService
     *
     * Конвертируем и возвращаем текст c типом String для формата csv, разделитель - ";"
     *
     * @param bookEntities the book entities
     * @return the csv
     */

    String getCsv(List<BookEntity> bookEntities);
}
