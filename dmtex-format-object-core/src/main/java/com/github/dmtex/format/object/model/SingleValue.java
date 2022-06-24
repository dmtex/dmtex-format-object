package com.github.dmtex.format.object.model;

/**
 * {@code ListValue} class is single item model to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class SingleValue implements FormatValue {

  private final String value;

  /**
   * Initializes object.
   *
   * @param value value
   */
  public SingleValue(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }
}
