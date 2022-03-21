/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "19.03.2022, 10:54"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.rateCurrencyChanging.Currency;

import java.util.List;

/**
 * The interface Response generator.
 */
public interface ResponseGenerator {

    /**
     * Generation csv string.
     *
     * @param books the books
     * @return the string
     */
    String generationCsv(List<BookEntity> books);

    /**
     * Generation svg byte [ ].
     *
     * @param books the books
     * @return the byte [ ]
     */
    byte[] generationSvg(List<BookEntity> books, Currency currency);

    /**
     * Generation pdf byte [ ].
     *
     * @param books the books
     * @return the byte [ ]
     */
    byte[] generationPdf(List<BookEntity> books);

}
