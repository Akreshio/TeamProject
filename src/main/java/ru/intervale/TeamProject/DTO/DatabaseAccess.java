/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 19:07"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.DTO;

import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

public interface DatabaseAccess {

    List<BookEntity> get(String name);

}
