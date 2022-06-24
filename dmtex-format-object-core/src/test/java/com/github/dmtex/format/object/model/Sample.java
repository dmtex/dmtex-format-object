package com.github.dmtex.format.object.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Sample implements Serializable {

  private static final long serialVersionUID = 1932061968740977439L;

  private final String title;
  private final BigDecimal value;
  private transient Object field;
  private final Sample child;
  private final List<String> lines;

  public Sample(String title, BigDecimal value, Sample child, List<String> lines) {
    this.title = title;
    this.value = value;
    this.child = child;
    this.lines = lines;
  }

  public String getTitle() {
    return title;
  }

  public BigDecimal getValue() {
    return value;
  }

  public Sample getChild() {
    return child;
  }

  public List<String> getLines() {
    return lines;
  }
}
