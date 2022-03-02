package ru.intervale.TeamProject.external.alfabank.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Выходная модель, содержащая курсы валют Национального банка Республики Беларусь
 */
@ApiModel(description = "Выходная модель, содержащая курсы валют Национального банка Республики Беларусь")
@Validated

public class NationalRateListResponse   {
  @JsonProperty("rates")
  @Valid
  private List<NationalRate> rates = null;

  public NationalRateListResponse rates(List<NationalRate> rates) {
    this.rates = rates;
    return this;
  }

  public NationalRateListResponse addRatesItem(NationalRate ratesItem) {
    if (this.rates == null) {
      this.rates = new ArrayList<NationalRate>();
    }
    this.rates.add(ratesItem);
    return this;
  }

  /**
   * Список курсов валют Национального банка Республики Беларусь
   * @return rates
  **/
  @ApiModelProperty(value = "Список курсов валют Национального банка Республики Беларусь")

  @Valid

  public List<NationalRate> getRates() {
    return rates;
  }

  public void setRates(List<NationalRate> rates) {
    this.rates = rates;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NationalRateListResponse nationalRateListResponse = (NationalRateListResponse) o;
    return Objects.equals(this.rates, nationalRateListResponse.rates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NationalRateListResponse {\n");
    
    sb.append("    rates: ").append(toIndentedString(rates)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

