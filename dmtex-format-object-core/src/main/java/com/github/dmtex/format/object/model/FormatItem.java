package com.github.dmtex.format.object.model;

/**
 * {@code FormatItem} class is core model to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class FormatItem {

  private final String name;
  private final FormatValue value;

  /**
   * Initializes object.
   *
   * @param name  name
   * @param value value
   */
  public FormatItem(String name, FormatValue value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Provides the name value.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Provides the value value.
   *
   * @return the value
   */
  public FormatValue getValue() {
    return value;
  }
}
