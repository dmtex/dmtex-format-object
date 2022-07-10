package com.github.dmtex.format.html.model;

import com.github.dmtex.format.object.annotation.FormatEntity;
import java.math.BigDecimal;

@FormatEntity(fields = {"title", "value"})
public class Sample {

  private final String title;
  private final BigDecimal value;

  public Sample(String title, BigDecimal value) {
    this.title = title;
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public BigDecimal getValue() {
    return value;
  }
}
