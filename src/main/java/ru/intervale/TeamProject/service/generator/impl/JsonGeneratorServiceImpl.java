/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "27.03.2022, 21:19"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.mapper.BookMapper;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.generator.JsonGeneratorService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class JsonGeneratorServiceImpl implements JsonGeneratorService {

    private BookMapper mapper;

    @Override
    public List<BookDto> getJson(List<BookEntity> bookEntities) {

        List<BookDto> bookDtoList = new ArrayList<>();
        if (bookEntities != null) {
            log.debug("Формирование вывода в формате Json");
            for (BookEntity book : bookEntities) {
                bookDtoList.add(mapper.mapBook(book));
            }
        }
        return bookDtoList;
    }
}
