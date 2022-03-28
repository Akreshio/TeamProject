
/*
 * @author S.Maevsky
 * @since 28.03.2022, 11:02
 * @version V 1.0.0
 */

/*
 * @author S.Maevsky
 * @since 20.03.2022, 18:49
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.intervale.TeamProject.dto.BookDto;
import ru.intervale.TeamProject.mapper.BookMapper;
import ru.intervale.TeamProject.model.book.BookEntity;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.dao.DatabaseAccess;
import ru.intervale.TeamProject.service.generator.JsonGeneratorService;
import ru.intervale.TeamProject.service.generator.impl.JsonGeneratorServiceImpl;
import ru.intervale.TeamProject.service.impl.PriceDynamicServiceImpl;
import ru.intervale.TeamProject.service.rate.Currency;
import ru.intervale.TeamProject.service.rate.changing.RateCurrencyChanging;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class PriceDynamicServiceImplTest {

    private static final LocalDateTime DATE_TIME_START =
            LocalDateTime.of(2022, Month.MARCH, 1, 00, 00);

    private static final LocalDateTime DATE_TIME_MIDLE =
            LocalDateTime.of(2022, Month.MARCH, 2, 00, 00);

    private static final LocalDateTime DATE_TIME_FINISH =
            LocalDateTime.of(2022, Month.MARCH, 3, 00, 00);

    @Mock
    private RateCurrencyChanging changing;

    @Mock
    private DatabaseAccess bookDao;

    @Spy
    private BookMapper mapper = new BookMapper();

    @Spy
    JsonGeneratorService jsonGenerator = new JsonGeneratorServiceImpl(mapper);

    @InjectMocks
    private PriceDynamicServiceImpl service;

    @Test
    void testGetJson() {

        List<BookEntity> booksTest = getTestBooks();
        List<BookDto> bookEntitiesResult = getResultBooks();
        Map<LocalDateTime, BigDecimal> ratesMapTest = getRatesMapForTest();

        Mockito.when(bookDao.get(Mockito.anyString())).thenReturn(booksTest);

        Mockito.when(changing.getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class)))
                .thenReturn(ratesMapTest);

        ParamRequest term = new ParamRequest(DATE_TIME_START, DATE_TIME_START, Period.day);

        assertEquals(service.getJson("test", Currency.RUB, term), bookEntitiesResult);

        verify(bookDao).get(Mockito.anyString());
        verify(changing).getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class));
    }

    @Test
    void testGetJsonWithoutPriceChange() {

        List<BookEntity> booksTest = Collections.singletonList(getTestBook1WithoutPriceChange());
        List<BookDto> booksResult = getResultBooksWithoutPriceChange();
        Map<LocalDateTime, BigDecimal> ratesMapTest = getRatesMapForTest();

        Mockito.when(bookDao.get(Mockito.anyString())).thenReturn(booksTest);

        Mockito.when(changing.getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class)))
                .thenReturn(ratesMapTest);

        ParamRequest term = new ParamRequest(DATE_TIME_START, DATE_TIME_START, Period.day);

        assertEquals(service.getJson("test", Currency.RUB, term), booksResult);

        verify(bookDao).get(Mockito.anyString());
        verify(changing).getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class));
    }


    @Test
    void testGetJsonWithPriceChange() {

        List<BookEntity> booksTest = Collections.singletonList(getTestBook2WithPriceChange());
        List<BookDto> booksResult = getResultBooksWithPriceChange();
        Map<LocalDateTime, BigDecimal> ratesMapTest = getRatesMapForTest();

        Mockito.when(bookDao.get(Mockito.anyString())).thenReturn(booksTest);

        Mockito.when(changing.getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class)))
                .thenReturn(ratesMapTest);

        ParamRequest term = new ParamRequest(DATE_TIME_START, DATE_TIME_START, Period.day);

        assertEquals(service.getJson("test", Currency.RUB, term), booksResult);

        verify(bookDao).get(Mockito.anyString());
        verify(changing).getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class));
    }

    //В период до появления книги в бд цена книги в выводе на UI будет 0,00
    @Test
    void testGetJsonWithNewBook() {

        List<BookEntity> booksTest = Collections.singletonList(getTestBook3WithNewBook());
        List<BookDto> booksResult = getResultBooksWithNewBook();
        Map<LocalDateTime, BigDecimal> ratesMapTest = getRatesMapForTest();

        Mockito.when(bookDao.get(Mockito.anyString())).thenReturn(booksTest);

        Mockito.when(changing.getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class)))
                .thenReturn(ratesMapTest);

        ParamRequest term = new ParamRequest(DATE_TIME_START, DATE_TIME_START, Period.day);

        assertEquals(service.getJson("test", Currency.RUB, term), booksResult);

        verify(bookDao).get(Mockito.anyString());
        verify(changing).getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class));
    }

    @Test
    void testGetJsonWithPriceChangeAfterPeriod() {

        List<BookEntity> booksTest = Collections.singletonList(getTestBook4WithPriceChangeAfterPeriod());
        List<BookDto> booksResult = getResultBooksWithPriceChangeAfterPeriod();
        Map<LocalDateTime, BigDecimal> ratesMapTest = getRatesMapForTest();

        Mockito.when(bookDao.get(Mockito.anyString())).thenReturn(booksTest);

        Mockito.when(changing.getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class)))
                .thenReturn(ratesMapTest);

        ParamRequest term = new ParamRequest(DATE_TIME_START, DATE_TIME_START, Period.day);

        assertEquals(service.getJson("test", Currency.RUB, term), booksResult);

        verify(bookDao).get(Mockito.anyString());
        verify(changing).getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class));
    }

    private BookEntity getTestBook1WithoutPriceChange() {

        return BookEntity.builder()
                .isbn("978-5-389-07123-0")
                .title("test")
                .writer("Лев Николаевич Толстой")
                .page(1408)
                .weight(1020)
                .price(new BigDecimal(18.00))
                .build();
    }

    private List<BookDto> getResultBooksWithoutPriceChange() {

        List<BookDto> bookDtosResult = Collections.singletonList(mapper.mapBook(getTestBook1WithoutPriceChange()));
        Map<LocalDateTime, BigDecimal> changePriceMap = new HashMap<>();

        changePriceMap.put(DATE_TIME_START, new BigDecimal("600.00"));
        changePriceMap.put(DATE_TIME_MIDLE, new BigDecimal("562.50"));
        changePriceMap.put(DATE_TIME_FINISH, new BigDecimal("580.65"));
        bookDtosResult.get(0).setPriceBook(sortByDate(changePriceMap));

        return bookDtosResult;
    }

    private BookEntity getTestBook2WithPriceChange() {

        BookEntity bookEntity = getTestBook1WithoutPriceChange();
        bookEntity.setPreviousBookPrice(getPreviousPriceMap2());

        return bookEntity;
    }

    @NotNull
    private Map<LocalDateTime, BigDecimal> getPreviousPriceMap2() {

        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
        priceMap.put(DATE_TIME_MIDLE, new BigDecimal("9.00"));
        priceMap.put(DATE_TIME_FINISH, new BigDecimal("3.00"));

        return priceMap;
    }

    private List<BookDto> getResultBooksWithPriceChange() {

        List<BookDto> bookDtosResult = Collections.singletonList(mapper.mapBook(getTestBook1WithoutPriceChange()));
        Map<LocalDateTime, BigDecimal> changePriceMap = new HashMap<>();

        changePriceMap.put(DATE_TIME_START, new BigDecimal("300.00"));
        changePriceMap.put(DATE_TIME_MIDLE, new BigDecimal("93.75"));
        changePriceMap.put(DATE_TIME_FINISH, new BigDecimal("580.65"));
        bookDtosResult.get(0).setPriceBook(sortByDate(changePriceMap));

        return bookDtosResult;
    }

    private BookEntity getTestBook3WithNewBook() {

        BookEntity bookEntity = getTestBook1WithoutPriceChange();
        bookEntity.setPreviousBookPrice(getPreviousPriceMap3());

        return bookEntity;
    }

    @NotNull
    private Map<LocalDateTime, BigDecimal> getPreviousPriceMap3() {

        //В день появления книги в БД мы сохраняем 0.00 в таблице предыдущих цен
        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
        priceMap.put(DATE_TIME_MIDLE, new BigDecimal("0.00"));
        priceMap.put(DATE_TIME_FINISH, new BigDecimal("3.00"));

        return priceMap;
    }

    private List<BookDto> getResultBooksWithNewBook() {

        List<BookDto> bookDtosResult = Collections.singletonList(mapper.mapBook(getTestBook1WithoutPriceChange()));
        Map<LocalDateTime, BigDecimal> changePriceMap = new HashMap<>();

        changePriceMap.put(DATE_TIME_START, new BigDecimal("0.00"));
        changePriceMap.put(DATE_TIME_MIDLE, new BigDecimal("93.75"));
        changePriceMap.put(DATE_TIME_FINISH, new BigDecimal("580.65"));
        bookDtosResult.get(0).setPriceBook(sortByDate(changePriceMap));

        return bookDtosResult;
    }

    private BookEntity getTestBook4WithPriceChangeAfterPeriod() {

        BookEntity bookEntity = getTestBook1WithoutPriceChange();
        bookEntity.setPreviousBookPrice(getPreviousPriceMap4());

        return bookEntity;
    }

    @NotNull
    private Map<LocalDateTime, BigDecimal> getPreviousPriceMap4() {

        //При изменении цены после периода, должна быть в map одна дополнительная запис с изменением цены после периода
        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
        priceMap.put(DATE_TIME_MIDLE, new BigDecimal("0.00"));
        priceMap.put(DATE_TIME_FINISH, new BigDecimal("3.00"));
        priceMap.put(DATE_TIME_FINISH.plusDays(1), new BigDecimal("12.00"));

        return priceMap;
    }

    private List<BookDto> getResultBooksWithPriceChangeAfterPeriod() {

        List<BookDto> bookDtosResult = Collections.singletonList(mapper.mapBook(getTestBook1WithoutPriceChange()));
        Map<LocalDateTime, BigDecimal> changePriceMap = new HashMap<>();

        changePriceMap.put(DATE_TIME_START, new BigDecimal("0.00"));
        changePriceMap.put(DATE_TIME_MIDLE, new BigDecimal("93.75"));
        changePriceMap.put(DATE_TIME_FINISH, new BigDecimal("387.10"));
        bookDtosResult.get(0).setPriceBook(sortByDate(changePriceMap));

        return bookDtosResult;
    }

    private List<BookEntity> getTestBooks(){

        BookEntity book1 = getTestBook1WithoutPriceChange();
        BookEntity book2 = getTestBook2WithPriceChange();
        List<BookEntity> books = Arrays.asList(book1, book2);

        return books;
    }

    private List<BookDto> getResultBooks(){

        List<BookDto> booksResult = new ArrayList<>();
        booksResult.addAll(getResultBooksWithoutPriceChange());
        booksResult.addAll(getResultBooksWithPriceChange());

        return booksResult;
    }

    private Map<LocalDateTime, BigDecimal> getRatesMapForTest() {

        Map<LocalDateTime, BigDecimal> ratesMap = new HashMap<>();
        ratesMap.put(DATE_TIME_START, new BigDecimal(0.03));
        ratesMap.put(DATE_TIME_MIDLE, new BigDecimal(0.032));
        ratesMap.put(DATE_TIME_FINISH, new BigDecimal(0.031));

        return ratesMap;
    }

    private Map<LocalDateTime, BigDecimal> sortByDate(@NotNull Map<LocalDateTime, BigDecimal> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new
                        )
                );
    }
}