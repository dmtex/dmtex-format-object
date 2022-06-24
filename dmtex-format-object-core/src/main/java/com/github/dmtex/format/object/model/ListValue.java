package com.github.dmtex.format.object.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * {@code ListValue} class is list model to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class ListValue implements FormatValue {

  private final List<FormatGroup> value;

  /**
   * Initializes object.
   *
   * @param value value
   */
  public ListValue(List<FormatGroup> value) {
    this.value = Optional.ofNullable(value).map(Collections::unmodifiableList).orElse(null);
  }

  @Override
  public List<FormatGroup> getValue() {
    return Optional.ofNullable(value).map(Collections::unmodifiableList).orElse(null);
  }
}
