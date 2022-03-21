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
@Profile("mock2")
public class DatabaseAccessMock2 implements DatabaseAccess {

    @Override
    public List<BookEntity> get(String name) {
        Map<LocalDateTime, BigDecimal> price1 = new HashMap<>();
        price1.put(LocalDateTime.now(), new BigDecimal("10.50"));
        price1.put(LocalDateTime.now().minusDays(4), new BigDecimal("8.50"));
        price1.put(LocalDateTime.now().minusDays(8), new BigDecimal("4.50"));

        Map<LocalDateTime, BigDecimal> price2 = new HashMap<>();
        price2.put(LocalDateTime.now(), new BigDecimal("50.50"));
        price2.put(LocalDateTime.now().minusDays(3), new BigDecimal("125.00"));
        price2.put(LocalDateTime.now().minusDays(7), new BigDecimal("24.50"));


        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity book1 = new BookEntity(10, new BigDecimal("10.99"),100, "10-1578-185", "name", "The test book", null, price1);
        BookEntity book2 = new BookEntity(747, new BigDecimal("50.99"),700, "10-1578-186", "no name", "The test book", null, price2);
        bookEntities.add(book1);
        bookEntities.add(book2);

        return bookEntities;
    }
}
