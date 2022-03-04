/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 20:40"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 20:40"
 * @version V 1.0.0
 */

/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:39"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.TeamProject.model.Currency;
import ru.intervale.TeamProject.service.ServiceChangePriceImpl;


/**
 * The type Book price api controller.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BookPriceController implements BookPrice {

    private ServiceChangePriceImpl service;

    // http://localhost:8080/price/stat?name=The test book&currency=EUR

    @Override
    public ResponseEntity<?> get(String name, Currency currency, String accept) {
        return  service.get(name, currency.getCode(), accept);
    }
}
