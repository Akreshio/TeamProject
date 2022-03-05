/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:40"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import com.itextpdf.text.DocumentException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
public interface BookPriceApi {


    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getJson", notes = "Возвращает json", tags={ "public", })
    @GetMapping(value = "/price/stat",
            produces = { "application/json;charset=UTF-8" })
    List<?> getJson(

            @ApiParam(value = "Наименование книги")
            @Valid @RequestParam(value = "name") String name,
            @ApiParam(value = "код валюты")
            @Valid @RequestParam(value = "currency") int currency);


    @ApiOperation(value = "Получение изменения цены на книгу", nickname = "getPdf", notes = "Возвращает pdf", tags={ "public", })
    @GetMapping(value = "/price/pdf")
    void getPdf(
            HttpServletResponse response) throws IOException, DocumentException;
//            @ApiParam(value = "Наименование книги")
//            @Valid @RequestParam(value = "name") String name,
//            @ApiParam(value = "код валюты")
//            @Valid @RequestParam(value = "currency") int currency) throws IOException;
}


