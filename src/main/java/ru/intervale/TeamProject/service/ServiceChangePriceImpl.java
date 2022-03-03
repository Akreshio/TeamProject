/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServiceChangePriceImpl implements ServiceChangePrice {

    private AlfabankService alfabank;

    private DatabaseAccess dto;

    @Override
    public List<BookEntity> get(String name, int currency) {

        List<BookEntity> bookEntities = getBook(name);

        for (BookEntity book: bookEntities) {

            Map<String, BigDecimal> changePrice = new HashMap<>();
            for (Map.Entry<String, BigDecimal> rate :  getChangePrice(currency).entrySet()) {
                changePrice.put(rate.getKey(),book.getPrice().multiply(rate.getValue()));
            }
            book.setChangePrice(changePrice);
        }
        return bookEntities;
    }

    private Map<String, BigDecimal> getChangePrice(int currency) {
        return  alfabank.get(currency);
    }

    private List<BookEntity> getBook(String name) {
        return  dto.get(name);
    }
}
