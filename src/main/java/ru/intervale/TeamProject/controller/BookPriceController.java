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

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.TeamProject.service.ServiceChangePriceImpl;

import java.util.List;


/**
 * The type Book price api controller.
 */
@Slf4j
@RestController
@NoArgsConstructor
public class BookPriceController implements BookPrice {

    @Autowired
    ServiceChangePriceImpl service;

    @Override
    public List<?> get(String name, int currency) {
        return service.get(name, currency);
    }
}
