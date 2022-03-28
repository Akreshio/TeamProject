/*
 * @author S.Maevsky
 * @since 28.03.2022, 11:04
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.rate.changing.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.intervale.TeamProject.model.request.ParamRequest;
import ru.intervale.TeamProject.model.request.Period;
import ru.intervale.TeamProject.service.dao.AlfaBankDao;
import ru.intervale.TeamProject.service.rate.Currency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateCurrencyChangingImplTest {

    @Mock
    AlfaBankDao alfaBankDao;

    @InjectMocks
    RateCurrencyChangingImpl rateCurrencyChangingImpl;

    @Test
    void testGetExchangeRateParamNull() {

        Map <LocalDateTime, BigDecimal> testCurrencyMap = getTestCurrencyMap();
        when(alfaBankDao.getByPeriod(getLastTenDays(), Currency.USD)).thenReturn(testCurrencyMap);

        Map<LocalDateTime, BigDecimal> result = rateCurrencyChangingImpl.getExchangeRate(Currency.USD, null);

        assertEquals(testCurrencyMap, result);
    }

    @Test
    void testGetTimePeriodWithoutParams() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        Class<? extends RateCurrencyChangingImpl> classRateCurrencyChanging = rateCurrencyChangingImpl.getClass();
        Method methodGetTimePeriod = classRateCurrencyChanging.getDeclaredMethod("getTimePeriod");
        methodGetTimePeriod.setAccessible(true);
        List<LocalDateTime> testDateTimeList = getLastTenDays();
        List<LocalDateTime> result = (List<LocalDateTime>) methodGetTimePeriod.invoke(rateCurrencyChangingImpl);

        assertEquals(testDateTimeList, result);
    }

    //Возвращает дату начала периода и дату окончания
    @Test
    void testGetTimePeriodHour() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<? extends RateCurrencyChangingImpl> classRateCurrencyChanging = rateCurrencyChangingImpl.getClass();

        Class[] paramTypes = new Class[] { ParamRequest.class };
        Method methodGetTimePeriod = classRateCurrencyChanging.getDeclaredMethod("getTimePeriod", paramTypes);

        methodGetTimePeriod.setAccessible(true);
        LocalDateTime date = LocalDateTime.of(Integer.parseInt("2022"), Integer.parseInt("01"),
                Integer.parseInt("10"),0,0);
        List<LocalDateTime> testDateTimeList = new ArrayList<>();
        testDateTimeList.add(date);
        testDateTimeList.add(date.plusDays(5));

        Object[] args = new Object[] { new ParamRequest("10.01.2022", "15.01.2022", Period.hour) };

        List<LocalDateTime> result = (List<LocalDateTime>) methodGetTimePeriod.invoke(rateCurrencyChangingImpl, args);

        assertEquals(testDateTimeList, result);
    }

    @Test
    void testGetTimePeriodDay() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<? extends RateCurrencyChangingImpl> classRateCurrencyChanging = rateCurrencyChangingImpl.getClass();

        Class[] paramTypes = new Class[] { ParamRequest.class };
        Method methodGetTimePeriod = classRateCurrencyChanging.getDeclaredMethod("getTimePeriod", paramTypes);

        methodGetTimePeriod.setAccessible(true);
        LocalDateTime date = LocalDateTime.of(Integer.parseInt("2022"), Integer.parseInt("01"),
                Integer.parseInt("10"),0,0);
        List<LocalDateTime> testDateTimeList = new ArrayList<>();
        testDateTimeList.add(date);
        testDateTimeList.add(date.plusDays(1));
        testDateTimeList.add(date.plusDays(2));

        Object[] args = new Object[] { new ParamRequest("10.01.2022", "12.01.2022", Period.day) };

        List<LocalDateTime> result = (List<LocalDateTime>) methodGetTimePeriod.invoke(rateCurrencyChangingImpl, args);

        assertEquals(testDateTimeList, result);
    }

    @Test
    void testGetTimePeriodWeek() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<? extends RateCurrencyChangingImpl> classRateCurrencyChanging = rateCurrencyChangingImpl.getClass();

        Class[] paramTypes = new Class[] { ParamRequest.class };
        Method methodGetTimePeriod = classRateCurrencyChanging.getDeclaredMethod("getTimePeriod", paramTypes);

        methodGetTimePeriod.setAccessible(true);
        LocalDateTime date = LocalDateTime.of(Integer.parseInt("2022"), Integer.parseInt("01"),
                Integer.parseInt("10"),0,0);
        List<LocalDateTime> testDateTimeList = new ArrayList<>();
        testDateTimeList.add(date);
        testDateTimeList.add(date.plusDays(7));
        testDateTimeList.add(date.plusDays(14));
        testDateTimeList.add(date.plusDays(21));

        Object[] args = new Object[] { new ParamRequest("10.01.2022", "01.02.2022", Period.week) };

        List<LocalDateTime> result = (List<LocalDateTime>) methodGetTimePeriod.invoke(rateCurrencyChangingImpl, args);

        assertEquals(testDateTimeList, result);
    }

    @Test
    void testGetTimePeriodMonth() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<? extends RateCurrencyChangingImpl> classRateCurrencyChanging = rateCurrencyChangingImpl.getClass();

        Class[] paramTypes = new Class[] { ParamRequest.class };
        Method methodGetTimePeriod = classRateCurrencyChanging.getDeclaredMethod("getTimePeriod", paramTypes);

        methodGetTimePeriod.setAccessible(true);
        LocalDateTime date = LocalDateTime.of(Integer.parseInt("2022"), Integer.parseInt("01"),
                Integer.parseInt("10"),0,0);
        List<LocalDateTime> testDateTimeList = new ArrayList<>();
        testDateTimeList.add(date);
        testDateTimeList.add(date.plusMonths(1));
        testDateTimeList.add(date.plusMonths(2));

        Object[] args = new Object[] { new ParamRequest("10.01.2022", "15.03.2022", Period.month) };

        List<LocalDateTime> result = (List<LocalDateTime>) methodGetTimePeriod.invoke(rateCurrencyChangingImpl, args);

        assertEquals(testDateTimeList, result);
    }

    @NotNull
    private List<LocalDateTime> getLastTenDays() {
        LocalTime time = LocalTime.of(0, 0, 0, 0);
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), time);
        List<LocalDateTime> result = new ArrayList<>();
        for (int i=0; i<10; i++) {
            result.add(date.minusDays(i));
        }
        return result;
    }

    private Map <LocalDateTime, BigDecimal> getTestCurrencyMap(){

        List<LocalDateTime> dateList = getLastTenDays();
        BigDecimal rate = new BigDecimal(3.2);
        Map <LocalDateTime, BigDecimal> currencyMap = new HashMap<>();

        for (LocalDateTime date: dateList) {
            currencyMap.put(date, rate);
        }

        return currencyMap;
    }
}
