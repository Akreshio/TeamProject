/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:40"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.intervale.TeamProject.service.bank.Currency;

public interface BookPrice {


    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getJson", notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = {"application/json;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<?> getJson(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency
    );

    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getPdf", notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = {"application/pdf"},
            method = RequestMethod.GET)
    ResponseEntity<?> getPdf(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency
    );


    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getCsv",
            notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = {"text/csv"},
            method = RequestMethod.GET)
    ResponseEntity<?> getCsv(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency
    );
}


