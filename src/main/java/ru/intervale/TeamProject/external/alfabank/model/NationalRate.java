package ru.intervale.TeamProject.external.alfabank.model;

import java.time.LocalDate;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Курс валюты Национального банка Республики Беларусь
 */
@ApiModel(description = "Курс валюты Национального банка Республики Беларусь")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-02-18T13:29:10.146+02:00")

public class NationalRate   {
  @JsonProperty("rate")
  private BigDecimal rate = null;

  @JsonProperty("iso")
  private String iso = null;

  @JsonProperty("code")
  private Integer code = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("date")
  private String date = null;

  @JsonProperty("name")
  private String name = null;

  public NationalRate rate(BigDecimal rate) {
    this.rate = rate;
    return this;
  }

  /**
   * Курс Национального банка Республики Беларусь
   * @return rate
  **/
  @ApiModelProperty(value = "Курс Национального банка Республики Беларусь")

  @Valid

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public NationalRate iso(String iso) {
    this.iso = iso;
    return this;
  }

  /**
   * Мнемоника валюты
   * @return iso
  **/
  @ApiModelProperty(value = "Мнемоника валюты")


  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public NationalRate code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Код валюты
   * @return code
  **/
  @ApiModelProperty(value = "Код валюты")


  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public NationalRate quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Количество валюты
   * @return quantity
  **/
  @ApiModelProperty(value = "Количество валюты")


  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public NationalRate date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Дата начала действия курса
   * @return date
  **/
  @ApiModelProperty(example = "06.08.2019", value = "Дата начала действия курса")

  @Valid

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public NationalRate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Наименование валюты
   * @return name
  **/
  @ApiModelProperty(value = "Наименование валюты")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NationalRate nationalRate = (NationalRate) o;
    return Objects.equals(this.rate, nationalRate.rate) &&
        Objects.equals(this.iso, nationalRate.iso) &&
        Objects.equals(this.code, nationalRate.code) &&
        Objects.equals(this.quantity, nationalRate.quantity) &&
        Objects.equals(this.date, nationalRate.date) &&
        Objects.equals(this.name, nationalRate.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rate, iso, code, quantity, date, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NationalRate {\n");
    
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
    sb.append("    iso: ").append(toIndentedString(iso)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

