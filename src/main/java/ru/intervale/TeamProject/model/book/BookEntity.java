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
import java.math.RoundingMode;
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

    @Override
    public String toString() {

        StringBuilder book = new StringBuilder();

        book.append("Isbn:;" + isbn + ";\n" +
                "Title:;" + title + ";\n" +
                "Writer:;" + writer + ";\n" +
                "Page:;" + page + ";\n" +
                "Weight:;" + weight + ";\n" +
                "Price in BYN:;" +
                price
                        .setScale(2, RoundingMode.HALF_UP)
                        .toString()
                        .replace('.', ',') +
                ";\n\n");

        if (!this.changePrice.isEmpty()) {

            book.append("Date;" + "Price in currency;\n");

            for (Map.Entry<String, BigDecimal> price : changePrice.entrySet()) {

                book.append(price.getKey() + ";" +
                        price
                                .getValue()
                                .setScale(2, RoundingMode.HALF_UP)
                                .toString()
                                .replace('.', ',') +
                        ";\n");
            }

            book.append("\n");
        }

        return book.toString();
    }
}
