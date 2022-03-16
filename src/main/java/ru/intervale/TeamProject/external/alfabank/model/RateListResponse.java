package ru.intervale.TeamProject.external.alfabank.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import javax.validation.Valid;

/**
 * Выходная модель, содержащая основные курсы валют Альфа-Банка
 */

@ApiModel(description = "Выходная модель, содержащая основные курсы валют Альфа-Банка")
@Validated
@Generated(value = "io.swagger.codegen.languages.SpringCodegen")

public class RateListResponse   {
  @JsonProperty("rates")
  @Valid
  private List<Rate> rates = null;

  /**
   * Rates rate list response.
   *
   * @param rates the rates
   * @return the rate list response
   */
  public RateListResponse rates(List<Rate> rates) {
    this.rates = rates;
    return this;
  }

  /**
   * Add rates item rate list response.
   *
   * @param ratesItem the rates item
   * @return the rate list response
   */
  public RateListResponse addRatesItem(Rate ratesItem) {
    if (this.rates == null) {
      this.rates = new ArrayList<Rate>();
    }
    this.rates.add(ratesItem);
    return this;
  }

  /**
   * Список основных курсов валют Альфа-Банка
   *
   * @return rates rates
   */
  @ApiModelProperty(value = "Список основных курсов валют Альфа-Банка")
  @Valid
  public List<Rate> getRates() {
    return rates;
  }

  /**
   * Sets rates.
   *
   * @param rates the rates
   */
  public void setRates(List<Rate> rates) {
    this.rates = rates;
  }
}

