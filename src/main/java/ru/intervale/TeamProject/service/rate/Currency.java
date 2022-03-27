/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "22.03.2022, 20:46"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.service.rate;

import lombok.Getter;

@Getter
public enum Currency {

    USD(840, "Доллар США"),
    EUR(978, "Евро"),
    RUB(643, "Российский рубль"),
    CAD(124, "Канадский доллар"),
    CNY(156, "Китайский юань женьминьби"),
    CZK(203, "Чешская крона"),
    SEK(752, "Шведская крона"),
    CHF(756, "Швейцарский франк"),
    GBP(826, "Фунт стерлингов Великобритании"),
    UAH(980, "Украинская гривна"),
    PLN(985, "Польский злотый");

    private final int code;
    private final String name;

    Currency(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
