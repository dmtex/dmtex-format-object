package com.github.dmtex.format.object.model;

import com.github.dmtex.format.object.annotation.FormatEntity;
import com.github.dmtex.format.object.annotation.FormatField;

@FormatEntity
public class NoFieldsSample {

  @FormatField
  private final String name;

  private final String description;

  @FormatField
  private final Integer value;

  public NoFieldsSample(String name, String description, Integer value) {
    this.name = name;
    this.description = description;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Integer getValue() {
    return value;
  }
}
