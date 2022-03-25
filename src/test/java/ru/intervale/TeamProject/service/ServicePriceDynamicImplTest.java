//
///*
// * @author S.Maevsky
// * @since 20.03.2022, 18:49
// * @version V 1.0.0
// */
//
//package ru.intervale.TeamProject.service;
//
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import ru.intervale.TeamProject.model.book.BookEntity;
//import ru.intervale.TeamProject.model.request.ParamRequest;
//import ru.intervale.TeamProject.model.request.Period;
//import ru.intervale.TeamProject.service.dao.DatabaseAccess;
//import ru.intervale.TeamProject.service.rate.changing.RateCurrencyChanging;
//import ru.intervale.TeamProject.service.rateCurrencyChanging.RateCurrencyChanging;
//import ru.intervale.TeamProject.service.rateCurrencyChanging.Currency;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.verify;
//
//
//@ExtendWith(MockitoExtension.class)
//public class ServicePriceDynamicImplTest {
//
//    private static final LocalDateTime DATE_TIME_START =
//            LocalDateTime.of(2022, Month.MARCH, 1, 00, 00);
//
//    private static final LocalDateTime DATE_TIME_FINISH =
//            LocalDateTime.of(2022, Month.MARCH, 2, 00, 00);
//
//    @Mock
//    private RateCurrencyChanging changing;
//
//    @Mock
//    private DatabaseAccess bookDao;
//
//    @InjectMocks
//    private ServicePriceDynamicImpl service;
//
//
//    @Test
//    void testGetJson() {
//
//        List<BookEntity> bookEntitiesTest = getBooksForTest();
//        List<BookEntity> bookEntitiesResult = getBooksForResult();
//        Map<LocalDateTime, BigDecimal> ratesMapTest = getRatesMapForTest();
//
//        Mockito.when(bookDao.get(Mockito.anyString())).thenReturn(bookEntitiesTest);
//
//        Mockito.when(changing.getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class)))
//                .thenReturn(ratesMapTest);
//
//        ParamRequest term = new ParamRequest(DATE_TIME_START, DATE_TIME_START, Period.day);
//
//        assertEquals(service.getJson("test", Currency.RUB, term),
//                ResponseEntity
//                        .ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(bookEntitiesResult)
//                );
//
//        verify(bookDao).get(Mockito.anyString());
//        verify(changing).getExchangeRate(Mockito.any(Currency.class), Mockito.any(ParamRequest.class));
//    }
//
//    private List<BookEntity> getBooksForTest() {
//
//        BookEntity bookDtoTest = getBookForTest();
//        BookEntity bookDtoTest2 = getBook2ForTest();
//        List<BookEntity> books = Arrays.asList(bookDtoTest, bookDtoTest2);
//
//        return books;
//    }
//
//    private BookEntity getBookForTest() {
//
//        Map<LocalDateTime, BigDecimal> pricePreviousMap = getPreviousPriceMap();
//
//        return BookEntity.builder()
//                .isbn("978-5-389-07123-0")
//                .title("test")
//                .writer("Лев Николаевич Толстой")
//                .page(1408)
//                .weight(1020)
//                .price(new BigDecimal(20.00))
//                .changePrice(pricePreviousMap)
//                .build();
//    }
//
//    @NotNull
//    private Map<LocalDateTime, BigDecimal> getPreviousPriceMap() {
//
//        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
//        priceMap.put(DATE_TIME_START, new BigDecimal("10.00"));
//
//        return priceMap;
//    }
//
//    private BookEntity getBook2ForTest() {
//
//        Map<LocalDateTime, BigDecimal> pricePreviousMap2 = getPreviousPriceMap2();
//
//        return BookEntity.builder()
//                .isbn("978-5-389-04935-2")
//                .title("test2")
//                .writer("Лев Николаевич Толстой")
//                .page(864)
//                .weight(571)
//                .price(new BigDecimal(7.00))
//                .changePrice(pricePreviousMap2)
//                .build();
//    }
//
//    @NotNull
//    private Map<LocalDateTime, BigDecimal> getPreviousPriceMap2() {
//
//        Map<LocalDateTime, BigDecimal> priceMap = new HashMap<>();
//        priceMap.put(DATE_TIME_FINISH, new BigDecimal("6.00"));
//
//        return priceMap;
//    }
//
//    private Map<LocalDateTime, BigDecimal> getRatesMapForTest() {
//
//        Map<LocalDateTime, BigDecimal> ratesMap = new HashMap<>();
//        ratesMap.put(DATE_TIME_START, new BigDecimal(30));
//        ratesMap.put(DATE_TIME_FINISH, new BigDecimal(32));
//
//        return ratesMap;
//    }
//
//    private List<BookEntity> getBooksForResult(){
//
//        List<BookEntity> bookEntitiesResult = getBooksForTest();
//        Map<LocalDateTime, BigDecimal> priceDinamicMap = new HashMap<>();
//
//        priceDinamicMap.put(DATE_TIME_START, new BigDecimal("600"));
//        priceDinamicMap.put(DATE_TIME_FINISH, new BigDecimal("640"));
//        bookEntitiesResult.get(0).setChangePrice(sortByDate(priceDinamicMap));
//
//        Map<LocalDateTime, BigDecimal> priceDinamicMap2 = new HashMap<>();
//        priceDinamicMap2.put(DATE_TIME_START, new BigDecimal("210"));
//        priceDinamicMap2.put(DATE_TIME_FINISH, new BigDecimal("224"));
//        bookEntitiesResult.get(1).setChangePrice(sortByDate(priceDinamicMap2));
//
//        return bookEntitiesResult;
//    }
//
//    private Map<LocalDateTime, BigDecimal> sortByDate(@NotNull Map<LocalDateTime, BigDecimal> map) {
//        return map.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .collect(
//                        Collectors.toMap(
//                                Map.Entry::getKey,
//                                Map.Entry::getValue,
//                                (e1, e2) -> e1, LinkedHashMap::new
//                        )
//                );
//    }
//}
