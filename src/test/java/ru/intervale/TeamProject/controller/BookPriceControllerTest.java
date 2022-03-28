/*
 * @author S.Maevsky
 * @since 19.03.2022, 23:05
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.controller;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.service.PriceDynamicService;
import ru.intervale.TeamProject.service.rate.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookPriceControllerTest {

    @MockBean
    private PriceDynamicService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetJson() throws Exception {

        List<BookDto> booksTest = getBooksForTest();

        when(service.getJson(anyString(), any(Currency.class), any()))
                .thenReturn(booksTest);

        mockMvc.perform(get("/1.0.0/price/stat")
                .accept("application/json;charset=UTF-8")
                .param("name", "test")
                .param("currency", "USD")
                .param("sStr", "01.03.2022")
                .param("fStr", "02.03.2022")
                .param("d", "day")
        )
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].isbn").value("978-5-389-07123-0"))
                .andExpect(jsonPath("$.[0].title").value("test"))
                .andExpect(jsonPath("$.[0].writer").value("Лев Николаевич Толстой"))
                .andExpect(jsonPath("$.[0].page").value(1408))
                .andExpect(jsonPath("$.[0].weight").value(1020))
                .andExpect(jsonPath("$.[0].price").value(20))
                .andExpect(jsonPath("$.[0].priceBook.2022-03-01T00:00").value(7.50))
                .andExpect(jsonPath("$.[0].priceBook.2022-03-02T00:00").value(8.50))
                .andExpect(jsonPath("$.[1].isbn").value("978-5-389-04935-2"))
                .andExpect(jsonPath("$.[1].title").value("test2"))
                .andExpect(jsonPath("$.[1].writer").value("Лев Николаевич Толстой"))
                .andExpect(jsonPath("$.[1].page").value(864))
                .andExpect(jsonPath("$.[1].weight").value(571))
                .andExpect(jsonPath("$.[1].price").value(21.50))
                .andExpect(jsonPath("$.[1].priceBook.2022-03-01T00:00").value(8.80))
                .andExpect(jsonPath("$.[1].priceBook.2022-03-02T00:00").value(9.80))
                .andExpect(status().isOk());

        verify(service).getJson(anyString(), any(Currency.class), any());
    }

    @Test
    public void testGetJsonInvalidUrn() throws Exception {

        mockMvc.perform(get("/100.0.0/price/stat")
                .accept("application/json;charset=UTF-8")
                .param("name", "test")
                .param("currency", "USD")
                .param("sStr", "01.03.2022")
                .param("fStr", "02.03.2022")
                .param("d", "day")
        )
                .andExpect(status().isNotFound());
    }

    private List<BookDto> getBooksForTest() {

        BookDto bookDtoTest = getBookForTest();
        BookDto bookDtoTest2 = getBook2ForTest();
        List<BookDto> bookDtos = Arrays.asList(bookDtoTest, bookDtoTest2);

        return bookDtos;
    }

    private BookDto getBookForTest() {

        Map<LocalDateTime, BigDecimal> priceMap = getPriceMap();

        return BookDto.builder()
                .isbn("978-5-389-07123-0")
                .title("test")
                .writer("Лев Николаевич Толстой")
                .page(1408)
                .weight(1020)
                .price(new BigDecimal(20))
                .priceBook(priceMap)
                .build();
    }

    @NotNull
    private Map<LocalDateTime, BigDecimal> getPriceMap() {

        LocalDateTime dateTime1 = LocalDateTime.of(2022, Month.MARCH, 1, 00, 00);
        LocalDateTime dateTime2 = LocalDateTime.of(2022, Month.MARCH, 2, 00, 00);
        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
        priceMap.put(dateTime1, new BigDecimal("7.50"));
        priceMap.put(dateTime2, new BigDecimal("8.50"));

        return priceMap;
    }

    private BookDto getBook2ForTest() {

        Map<LocalDateTime, BigDecimal> priceMap = getPriceMap2();

        return BookDto.builder()
                .isbn("978-5-389-04935-2")
                .title("test2")
                .writer("Лев Николаевич Толстой")
                .page(864)
                .weight(571)
                .price(new BigDecimal(21.50))
                .priceBook(priceMap)
                .build();
    }

    @NotNull
    private Map<LocalDateTime, BigDecimal> getPriceMap2() {

        LocalDateTime dateTime1 = LocalDateTime.of(2022, Month.MARCH, 1, 00, 00);
        LocalDateTime dateTime2 = LocalDateTime.of(2022, Month.MARCH, 2, 00, 00);
        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
        priceMap.put(dateTime1, new BigDecimal("8.80"));
        priceMap.put(dateTime2, new BigDecimal("9.80"));

        return priceMap;
    }
}
