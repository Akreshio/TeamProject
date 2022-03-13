package ru.intervale.TeamProject.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * The interface Service generate SVG.
 */
public interface ServiceGenerateSvg {

    /**
     * Генерирует SVG файл с графиком изменения цены
     *
     * @param name     the name
     * @param currency the currency
     */

    void generateSvg (Map<String, BigDecimal> changeMap,  String name, String currency) throws IOException;
}
