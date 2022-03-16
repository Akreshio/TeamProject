/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 19:20"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.DAO;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
@Profile("mock1")
public class DatabaseAccessMock1 implements DatabaseAccess {

    @Override
    public List<BookEntity> get(String name) {
        Map<LocalDateTime, BigDecimal> price = new HashMap<>();
        price.put(LocalDateTime.now().minusDays(1), new BigDecimal("10.50"));
        price.put(LocalDateTime.now().minusDays(6), new BigDecimal("8.50"));
        price.put(LocalDateTime.now().minusDays(20), new BigDecimal("4.50"));

        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity book = new BookEntity(
                10,
                new BigDecimal("10.90"),
                100, "10-1578-185",
                "name",
                "The test book",
                null,
                price);
        bookEntities.add(book);

        return bookEntities;
    }
}
