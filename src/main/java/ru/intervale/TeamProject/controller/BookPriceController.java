package ru.intervale.TeamProject.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import org.intellij.lang.annotations.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.rate.Currency;

import java.util.List;

public interface BookPriceController {


    @ApiOperation(value = "Getting a price change for a book", nickname = "getJson", notes = "Returns the requested format", tags={"public"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful execution of the request", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 500, message = "Request execution error", response = Error.class)
    })
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "application/json;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<List<BookDto>> getJson(
            @ApiParam(value = "Book title")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "currency code")
            @RequestParam(value = "currency") Currency currency,


            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "beginning of period (01.01.2022)", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "end of period (01.01.2022)", required=false) String fStr,

            @RequestParam(value = "period", required=false) Period d
    );

    @ApiOperation(value = "Getting a price change for a book", nickname = "getPdf", notes = "Returns the requested format", tags={"public"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful execution of the request", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 500, message = "Request execution error", response = Error.class)
    })
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "application/pdf"},
            method = RequestMethod.GET)
    ResponseEntity<byte[]> getPdf(
            @ApiParam(value = "Book title")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "currency code")
            @RequestParam(value = "currency") Currency currency,

            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "beginning of period (01.01.2022)", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "end of period (01.01.2022)", required=false) String fStr,

            @RequestParam(value = "period", required=false) Period d
    );

    @ApiOperation(value = "Getting a price change for a book", nickname = "getSvg", notes = "Returns the requested format", tags={"public"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful execution of the request", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 500, message = "Request execution error", response = Error.class)
    })
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "image/svg+xml"},
            method = RequestMethod.GET)
    ResponseEntity<byte[]> getSvg(
            @ApiParam(value = "Book title")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "currency code")
            @RequestParam(value = "currency") Currency currency,

            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "beginning of period (01.01.2022)", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "end of period (01.01.2022)", required=false) String fStr,

            @RequestParam(value = "period", required=false) Period d
    );

    @ApiOperation(value = "Getting a price change for a book", nickname = "getCsv", notes = "Returns the requested format", tags={"public"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful execution of the request", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 500, message = "Request execution error", response = Error.class)
    })
    @RequestMapping(value = "/1.0.0/price/stat",
            produces = { "text/csv;charset=UTF-8"},
            method = RequestMethod.GET)
    ResponseEntity<String> getCsv(
            @ApiParam(value = "Book title")
            @RequestParam(value = "name") String name,
            @ApiParam(value = "currency code")
            @RequestParam(value = "currency") Currency currency,

            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "beginning of period (01.01.2022)", required=false) String sStr,
            @Pattern(value = "^(3[01]|[12][0-9]|0[1-9]).(1[0-2]|0[1-9]).((20)[0-9]{2})$")
            @RequestParam(value = "end of period (01.01.2022)", required=false) String fStr,

            @RequestParam(value = "period", required=false) Period d
    );



}


