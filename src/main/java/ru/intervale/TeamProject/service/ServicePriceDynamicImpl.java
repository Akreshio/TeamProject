/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:33"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.intervale.TeamProject.external.alfabank.Currency;
import ru.intervale.TeamProject.external.alfabank.FormationPeriod;
import ru.intervale.TeamProject.model.book.BookEntityList;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.external.alfabank.AlfabankService;
import ru.intervale.TeamProject.model.book.BookEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The type Service change price.
 */
@Service
@AllArgsConstructor
public class ServicePriceDynamicImpl implements ServicePriceDynamic {

    private AlfabankService alfabank;
    private DatabaseAccess dto;
    private BookEntityList bookEntities;
    private FormationPeriod formationPeriod;

    @Override
    public ResponseEntity<?> get(String name, Currency currency, String accept) {
         bookEntities.setBookEntities(getBook(name));
         bookEntities.addChangePrice(getChangePrice(currency));
        formationPeriod.get();

        switch (accept) {
            case "application/json":
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bookEntities);
            case "application/xml":
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(accept))
                        .body(bookEntities);
            case "image/svg+xml":
                return acceptSvg(accept, bookEntities.getBookEntities());
            case "text/csv":
                return acceptCsv(accept, bookEntities.getBookEntities());
            case "application/pdf":
                return acceptPdf(accept, bookEntities.getBookEntities());
            default:
                return ResponseEntity.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Bad reques");
        }
    }

    /**
     * Реализация: Дмитрий Самусев.
     */
    private ResponseEntity<?> acceptSvg (String accept, List<BookEntity> bookEntitiesList) {

        return  ResponseEntity.badRequest()
                .contentType(MediaType.parseMediaType(accept))
                .body("Bad reques");
    }

    /**
     * Реализация: Сергей Маевский.
     */
    private ResponseEntity<?> acceptCsv (String accept, List<BookEntity> bookEntitiesList) {

        return  ResponseEntity.badRequest()
                .contentType(MediaType.parseMediaType(accept))
                .body("Bad reques");
    }

    /**
     * Реализация: Игорь Прохорченко.
     */
    private ResponseEntity<?> acceptPdf (String accept, List<BookEntity> bookEntitiesList) {

        return  ResponseEntity.badRequest()
                .contentType(MediaType.parseMediaType(accept))
                .body("Bad reques");
    }

    private Map<String, BigDecimal> getChangePrice(Currency currency) {
        return  alfabank.get(currency);
    }

    private List<BookEntity> getBook(String name) {
        return  dto.get(name);
    }
}
