package com.github.dmtex.format.object.model;

/**
 * {@code FormatValue} interface describes formatting value.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public interface FormatValue {

  /**
   * Provides values to be formatted.
   *
   * @return value
   */
  Object getValue();
}
