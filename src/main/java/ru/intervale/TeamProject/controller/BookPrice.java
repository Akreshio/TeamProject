/*
 * @author
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:40"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.intellij.lang.annotations.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.RateCurrencyChanging.Currency;

import java.util.List;

public interface BookPrice {


    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getJson", notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "application/json;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<List<BookEntity>> getJson(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency,

            @ApiParam(value = "Наименование книги")
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "s", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "f", required=false) String fStr,

            @RequestParam(value = "d", required=false) Period d
    );

    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getPdf", notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "application/pdf;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<byte[]> getPdf(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency,


            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "s", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "f", required=false) String fStr,

            @RequestParam(value = "d", required=false) Period d
    );

    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getSvg", notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "image/svg+xml;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<byte[]> getSvg(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency,

            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "s", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "f", required=false) String fStr,

            @RequestParam(value = "d", required=false) Period d
    );

    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getCsv", notes = "Возвращает запрошенный формат", tags={"public"})
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "text/csv;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<String> getCsv(
            @ApiParam(value = "Наименование книги")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @RequestParam(value = "currency") Currency currency,

            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "s", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "f", required=false) String fStr,

            @RequestParam(value = "d", required=false) Period d
    );



}


