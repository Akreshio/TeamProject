package ru.intervale.TeamProject.service;

import ru.intervale.TeamProject.model.book.BookEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The interface Service generate SVG.
 */
public interface ServiceGenerateSvg {

    /**
     * Генерирует SVG файл с графиком изменения цены
     *
     * @param bookEntityList the list book
     * @param currency the currency
     */

    void generateSvg (List<BookEntity> bookEntityList) throws IOException;
}
