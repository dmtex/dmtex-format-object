package com.github.dmtex.format.object.model;

import java.util.function.Function;

public class DateExtractor implements Function<Object, FormatValue> {

  @Override
  public FormatValue apply(Object o) {
    return new SingleValue("XYZ");
  }
}
