/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "19.03.2022, 10:54"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;

import java.util.List;

public interface ResponseGenerator {

    String generationCsv(List<BookEntity> books);

    byte[] generationSvg(List<BookEntity> books, Currency currency);

    byte[] generationPdf(List<BookEntity> books);

}
