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
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Profile("mock2")
public class DatabaseAccessMock2 implements DatabaseAccess {

    @Override
    public List<BookEntity> get(String name) {

        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity book1 = new BookEntity(10, new BigDecimal("10.50"),100, "10-1578-185", "name", "The test book", null);
        BookEntity book2 = new BookEntity(747, new BigDecimal("50.99"),700, "10-1578-186", "no name", "The test book", null);
        bookEntities.add(book1);
        bookEntities.add(book2);

        return bookEntities;
    }
}
