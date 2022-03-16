/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 21:41"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 19:07"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.dao;

import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

/**
 * The interface Database access.
 */
public interface DatabaseAccess {

    /**
     * Тут получаем лист книг из базы данных.
     *
     * @param name имя книги
     * @return  возвращаем лист BookEntity
     *
     * @see BookEntity
     */
    List<BookEntity> get(String name);

}
