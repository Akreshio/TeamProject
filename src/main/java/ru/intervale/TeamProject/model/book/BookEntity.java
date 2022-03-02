/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:15"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class BookEntity {

    private int page;

    private BigDecimal price;

    private int weight;

    private String isbn;

    private String writer;

    private String title;

    // дата , цена
    private Map<String, BigDecimal> changePrice;
}
