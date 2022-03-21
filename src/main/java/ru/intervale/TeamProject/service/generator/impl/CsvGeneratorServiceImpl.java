/*
 * @author S.Maevsky
 * @since 14.03.2022, 14:26
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.generator.CsvGeneratorService;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * The type Csv generator service.
 */
@Slf4j
@Service
public class CsvGeneratorServiceImpl implements CsvGeneratorService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public String getCsv(List<BookEntity> bookEntities) {

        log.debug("Get Csv by list of bookEntities = {}", bookEntities);

        StringBuilder booksString = new StringBuilder();

        for (BookEntity book : bookEntities) {

            addBookToString(booksString, book);
        }

        log.debug("Get Csv by list of bookEntities = {}, result = {}", bookEntities, booksString.toString());

        return booksString.toString();
    }

    private void addBookToString(StringBuilder booksString, BookEntity book) {

        booksString.append("Isbn:;" + book.getIsbn() + ";\n" +
                "Title:;" + book.getTitle() + ";\n" +
                "Writer:;" + book.getWriter() + ";\n" +
                "Page:;" + book.getPage() + ";\n" +
                "Weight:;" + book.getWeight() + ";\n" +
                "Price in BYN:;" +
                book.getPrice()
                        .setScale(2, RoundingMode.HALF_UP)
                        .toString()
                        .replace('.', ',') +
                ";\n");

        if ((book.getChangePrice() != null) && (!book.getChangePrice().isEmpty())) {

            booksString.append("Column: Date;" + "Column: Price in currency;\n");

            for (Map.Entry<LocalDateTime, BigDecimal> price : book.getChangePrice().entrySet()) {

                booksString.append(DATE_TIME_FORMATTER.format(price.getKey()))
                        .append(";")
                        .append(price
                                .getValue()
                                .setScale(2, RoundingMode.HALF_UP)
                                .toString()
                                .replace('.', ','))
                        .append(";\n");
            }
        }
    }
}
