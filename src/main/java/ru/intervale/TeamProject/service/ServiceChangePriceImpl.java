/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.DTO.DatabaseAccess;
import ru.intervale.TeamProject.external.alfabank.AlfabankService;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ServiceChangePriceImpl implements ServiceChangePrice {

    @Autowired
    AlfabankService alfabank;
    @Autowired
    DatabaseAccess dto;

    @Override
    public List<BookEntity> get(String name, int currency) {

        List<BookEntity> bookEntities = getBook(name);

        for (BookEntity book: bookEntities) {
            book.setChangePrice(
                    getChangePrice(
                            book.getPrice(),currency
                    )
            );
        }
        return bookEntities;
    }


    private Map<String, BigDecimal> getChangePrice(BigDecimal price, int currency) {
        return  alfabank.get(price, currency);
    }

    private List<BookEntity> getBook(String name) {
        return  dto.get(name);
    }
}
