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

import javax.validation.Valid;
import java.util.List;
public interface BookPriceApi {


    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "get", notes = "Возвращает json", tags={ "public", })
    @RequestMapping(value = "/price/stat",
            produces = { "application/json;charset=UTF-8" },
            method = RequestMethod.GET)
    ResponseEntity<?> get(

            @ApiParam(value = "Наименование книги")
            @Valid @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @Valid @RequestParam(value = "currency") int currency);


}


