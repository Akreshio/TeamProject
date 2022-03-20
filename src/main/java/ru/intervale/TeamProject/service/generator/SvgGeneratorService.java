package ru.intervale.TeamProject.service.generator;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;

import java.util.List;

/**
 * The interface Service generate SVG.
 */
public interface SvgGeneratorService {

    /**
     * Генерирует SVG файл с графиком изменения цены
     *
     * @param bookEntityList the list book
     * @param currency the currency
     */

    byte[] generateSvg (List<BookEntity> bookEntityList, Currency currency);
}
