/*
 * @author S.Maevsky
 * @since 16.03.2022, 0:11
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.model.rate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Rate entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateEntity {

    private Long id;

    private LocalDateTime date;

    private BigDecimal usd;

    private BigDecimal eur;

    private BigDecimal rub;

    /**
     * Instantiates a new Rate entity.
     *
     * @param date the date
     * @param usd  the usd
     * @param eur  the eur
     * @param rub  the rub
     */
    public RateEntity(LocalDateTime date, BigDecimal usd, BigDecimal eur, BigDecimal rub) {
        this.date = date;
        this.usd = usd;
        this.eur = eur;
        this.rub = rub;
    }
}
