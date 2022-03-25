/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:15"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor

public class BookEntity {

    @JsonProperty("page")
    private int page;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("writer")
    private String writer;

    @JsonProperty("title")
    private String title;

    // дата , цена
    @JsonProperty("changePrice")
    private Map<LocalDateTime, BigDecimal> changePrice;

    @JsonProperty("previousBookPrice")
    private Map<LocalDateTime, BigDecimal> previousBookPrice;
}
