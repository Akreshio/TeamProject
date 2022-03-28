/*
 * @author S.Maevsky
 * @since 23.03.2022, 0:21
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.belapb.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@Component
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Currency")
@RequiredArgsConstructor
public class RateBelApb {


    @JacksonXmlProperty(localName = "NumCode")
    public int numCode;

    @JacksonXmlProperty(localName = "CharCode")
    public String charCode;

    @JacksonXmlProperty(localName = "Scale")
    public int scale;

    @JacksonXmlProperty(localName = "Name")
    public String name;

    @JacksonXmlProperty(localName = "RateBuy")
    public BigDecimal rateBuy;

    @JacksonXmlProperty(localName = "RateSell")
    public BigDecimal rateSell;

    @JacksonXmlProperty(localName = "CityId")
    public int cityId;

    @JacksonXmlProperty(localName = "BankId")
    public String bankId;

    @JacksonXmlProperty(localName = "Id")
    public String id;
}
