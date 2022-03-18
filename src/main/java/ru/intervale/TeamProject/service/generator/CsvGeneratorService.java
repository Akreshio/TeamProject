/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "18.03.2022, 23:58"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "18.03.2022, 23:56"
 * @version V 1.0.0
 */

/*
 * @author S.Maevsky
 * @since 14.03.2022, 14:25
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.ServicePriceDynamic;

import java.util.List;


/**
 * The interface Csv generator service.
 */
public interface CsvGeneratorService {

    /**
     * Gets csv.
     *
     * Принимаем параметр списка книг из сервиса
     * @see ServicePriceDynamic
     *
     * Конвертируем и возвращаем текст c типом String для формата csv, разделитель - ";"
     *
     * @param bookEntities the book entities
     * @return the csv
     */

    String getCsv(List<BookEntity> bookEntities);
}
