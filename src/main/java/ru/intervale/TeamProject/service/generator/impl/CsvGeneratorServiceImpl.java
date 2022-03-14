/*
 * @author S.Maevsky
 * @since 14.03.2022, 14:26
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.generator.impl;

import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.service.generator.CsvGeneratorService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;


@Service
public class CsvGeneratorServiceImpl implements CsvGeneratorService {

    @Override
    public String getCsv(List<BookEntity> bookEntities) {

        StringBuilder booksString = new StringBuilder();

        for (BookEntity book : bookEntities) {

            addBookToString(booksString, book);
        }

        return booksString.toString();
    }

     private void addBookToString(StringBuilder booksString, BookEntity book){

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

                for (Map.Entry<String, BigDecimal> price : book.getChangePrice().entrySet()) {

                    booksString.append(price.getKey() + ";" +
                            price
                                    .getValue()
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .toString()
                                    .replace('.', ',') +
                            ";\n");
                }
            }
    }
}
