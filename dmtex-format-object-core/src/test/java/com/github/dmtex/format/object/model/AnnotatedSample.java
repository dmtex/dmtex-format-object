package com.github.dmtex.format.object.model;

import com.github.dmtex.format.object.ModelBuilder.KeyAwareExtractor;
import com.github.dmtex.format.object.annotation.FormatEntity;
import com.github.dmtex.format.object.annotation.FormatField;
import java.time.LocalDate;
import java.util.List;

@FormatEntity(value = "name", fields = {"date", "value", "type", "items"})
public class AnnotatedSample {

  private final String name;

  private final double value;

  @FormatField(extractor = DateExtractor.class)
  private final LocalDate date;

  @FormatField(value = "sample_type", extractor = KeyAwareExtractor.class)
  private final SampleType type;

  @FormatEntity(fields = {"title", "value", "child", "lines"})
  @FormatField(value = "children")
  private final List<Sample> items;

  public AnnotatedSample(String name, double value, LocalDate date, SampleType type, List<Sample> items) {
    this.name = name;
    this.value = value;
    this.date = date;
    this.type = type;
    this.items = items;
  }

  public String getName() {
    return name;
  }

  public double getValue() {
    return value;
  }

  public LocalDate getDate() {
    return date;
  }

  public SampleType getType() {
    return type;
  }

  public List<Sample> getItems() {
    return items;
  }
}
