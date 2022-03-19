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

import java.util.List;

@Component
@NoArgsConstructor

@Profile("mock3")
public class DatabaseAccessMock3 implements DatabaseAccess {


    @Override
    public List<BookEntity> get(String name) {

        return null;
    }
}
