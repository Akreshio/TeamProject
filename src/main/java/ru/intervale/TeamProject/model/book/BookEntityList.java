/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "04.03.2022, 1:14"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.model.book;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
public class BookEntityList {

    List<BookEntity> bookEntities = new ArrayList<>();

    public boolean addChangePrice (Map<String, BigDecimal> rateChange ) {
        for (BookEntity book: bookEntities) {

            Map<String, BigDecimal> changePrice = new HashMap<>();
            for (Map.Entry<String, BigDecimal> rate :  rateChange.entrySet()) {
                changePrice.put(rate.getKey(),book.getPrice().multiply(rate.getValue()));
            }
            book.setChangePrice(changePrice);
        }
        return true;
    }
}
