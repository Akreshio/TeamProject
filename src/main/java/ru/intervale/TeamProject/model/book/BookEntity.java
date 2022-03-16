/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:15"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@XmlRootElement(name = "Book")
public class BookEntity {

    @XmlElement(name = "page")
    private int page;

    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "weight")
    private int weight;

    @XmlElement(name = "isbn")
    private String isbn;

    @XmlElement(name = "writer")
    private String writer;

    @XmlElement(name = "title")
    private String title;

    // дата , цена
    @XmlElement(name = "changePrice")
    private Map<LocalDateTime, BigDecimal> changePrice;

    private Map<LocalDateTime, BigDecimal> previousBookPrice;
}
