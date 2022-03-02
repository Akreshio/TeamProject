/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 19:07"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.DAO;

import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;

/**
 * The interface Database access.
 */
public interface DatabaseAccess {

    /**
     * тут получаем лист книг из нашей базы данных.
     *
     * @param name имя книги
     * @return  возвращаем лист BookEntity
     *
     * @see BookEntity
     */
    List<BookEntity> get(String name);

}
