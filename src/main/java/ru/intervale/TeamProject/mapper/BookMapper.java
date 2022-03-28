/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "27.03.2022, 21:01"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.mapper;

import org.springframework.stereotype.Component;
import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.model.book.BookEntity;

@Component
public class BookMapper {

    public BookDto mapBook (BookEntity book){
       return new BookDto(
               book.getIsbn(),
               book.getTitle(),
               book.getWriter(),
               book.getPage(),
               book.getWeight(),
               book.getPrice(),
               book.getChangePrice()
       );
    }
}
