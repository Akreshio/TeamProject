/*
 * @author S.Maevsky
 * @since 28.03.2022, 16:26
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.bookinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.intervale.TeamProject.external.bookinfo.model.BookList;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;

import java.util.List;

@Profile("default")
@Service
@Slf4j
public class BookInfo implements DatabaseAccess {

    @Autowired
    @Qualifier("bookInfo")
    private RestTemplate restTemplate;

    /**
     * Тут получаем лист книг из личного проекта.
     *
     * @param name имя книги
     * @return возвращаем лист BookEntity
     * @see BookEntity
     */
    @Override
    public List<BookEntity> get(String name) {
        BookList rateList;
        try {
            rateList = restTemplate.getForEntity(
                    "title=" + name,
                    BookList.class
            ).getBody();
            return rateList.getBookList();
        } catch (RestClientException ex) {
            log.error("RestTemplate Exception when get book from Book info.");
            log.info("Exception: {}", ex.getStackTrace());
            throw new RestClientException("Exception when handling get book from Book info.");
        }
    }

}
