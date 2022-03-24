/*
 * @author S.Maevsky
 * @since 23.03.2022, 0:24
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.belapb.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Component
@XmlRootElement(name="DailyExRates")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@RequiredArgsConstructor
public class DailyExRates {

    @JacksonXmlProperty(localName = "Date")
    String date;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Currency")
    List<RateBelApb> rateBelApbList;
//    @JacksonXmlElementWrapper(useWrapping = false)
//    @JacksonXmlProperty(localName = "Currency")
//    public List<Currency> currencyList;
//
////    @JacksonXmlElementWrapper(useWrapping = false)
//    @JacksonXmlProperty(localName = "Date")
//    public String date;

}
