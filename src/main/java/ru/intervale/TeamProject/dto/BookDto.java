/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "27.03.2022, 20:44"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Data
public class BookDto {

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("writer")
    private String writer;

    @JsonProperty("page")
    private int page;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("price in BYN")
    private BigDecimal price;

    // дата , цена
    @JsonProperty("price book in select period")
    private Map<LocalDateTime, BigDecimal> priceBook;

}
