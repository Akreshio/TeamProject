package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.rate.Currency;


import java.util.List;

/**
 * The interface Service generate SVG.
 */
public interface SvgGeneratorService {

    /**
     * Генерирует байт код SVG файла содержащий график изменения цены
     *
     * @param bookEntityList the list book
     * @param currency the currency
     */

    byte[] generateSvg (List<BookEntity> bookEntityList, Currency currency);
}
