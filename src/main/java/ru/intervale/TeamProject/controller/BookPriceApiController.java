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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.TeamProject.service.ServiceChangePriceImpl;

import java.util.List;


/**
 * The type Book price api controller.
 */
@Slf4j
@RestController
@NoArgsConstructor
public class BookPriceApiController implements BookPriceApi {

    @Autowired
    ServiceChangePriceImpl service;

    @Override
    public ResponseEntity<?> get(String name, int currency) {

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(service.get(name, currency).toString());
    }
}
