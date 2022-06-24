package com.github.dmtex.format.object.spi;

import com.github.dmtex.format.object.model.FormatValue;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * {@code ObjectFormatterServiceProvider} interface is core SPI interface for Format Object.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public interface ObjectFormatterServiceProvider {

  /**
   * Provides list of resource bundles.
   *
   * @return list of resource bundles
   */
  default List<ResourceBundle> resourceBundles() {
    return Collections.emptyList();
  }

  /**
   * Provides list of extractors.
   *
   * @return list of extractors
   */
  default List<Function<?, FormatValue>> extractors() {
    return Collections.emptyList();
  }
}
