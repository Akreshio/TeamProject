/*
 * @author S.Maevsky
 * @since 28.03.2022, 16:21
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.bookinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.util.List;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookList {

    List<BookEntity> bookList;

}
