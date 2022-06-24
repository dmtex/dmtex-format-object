package com.github.dmtex.format.object.model;

import com.github.dmtex.format.object.model.FormatValue;
import com.github.dmtex.format.object.model.SingleValue;
import java.util.function.Function;

public class DateExtractor implements Function<Object, FormatValue> {

  @Override
  public FormatValue apply(Object o) {
    return new SingleValue("XYZ");
  }
}
