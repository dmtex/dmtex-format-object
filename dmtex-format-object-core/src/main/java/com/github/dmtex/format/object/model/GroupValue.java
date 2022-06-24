package com.github.dmtex.format.object.model;

/**
 * {@code GroupValue} class is group model to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class GroupValue implements FormatValue {

  private final FormatGroup value;

  /**
   * Initializes object.
   *
   * @param value value
   */
  public GroupValue(FormatGroup value) {
    this.value = value;
  }

  @Override
  public FormatGroup getValue() {
    return value;
  }
}
