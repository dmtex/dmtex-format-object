package com.github.dmtex.format.object.spi;

import com.github.dmtex.format.object.model.DateExtractor;
import com.github.dmtex.format.object.model.FormatValue;
import java.util.List;
import java.util.function.Function;

public class ExtractorObjectFormatterServiceProvider implements ObjectFormatterServiceProvider {

  @Override
  public List<Function<?, FormatValue>> extractors() {
    return List.of(new DateExtractor());
  }
}
