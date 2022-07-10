package com.github.dmtex.format.html.model;

import com.github.dmtex.format.object.annotation.FormatEntity;
import com.github.dmtex.format.object.annotation.FormatField;
import java.util.List;

@FormatEntity(value = "name", fields = {"value", "sample", "items"})
public class AnnotatedSample {

  private final String name;

  private final double value;

  private final Sample sample;

  @FormatField(value = "children")
  private final List<Sample> items;

  public AnnotatedSample(String name, double value, Sample sample, List<Sample> items) {
    this.name = name;
    this.value = value;
    this.sample = sample;
    this.items = items;
  }

  public String getName() {
    return name;
  }

  public double getValue() {
    return value;
  }

  public Sample getSample() {
    return sample;
  }

  public List<Sample> getItems() {
    return items;
  }
}
