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
@Profile("mock1")
public class DatabaseAccessMock1 implements DatabaseAccess {

    @Override
    public List<BookEntity> get(String name) {

        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity book = new BookEntity(10, new BigDecimal("10.50"),100, "10-1578-185", "name", "The test book", null);
        bookEntities.add(book);

        return bookEntities;
    }
}
