package com.github.dmtex.format.object.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * {@code FormatGroup} class is core model to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class FormatGroup {

  private final String name;
  private final List<FormatItem> items;

  /**
   * Initializes object.
   *
   * @param name  group name
   * @param items group items
   */
  public FormatGroup(String name, List<FormatItem> items) {
    this.name = name;
    this.items = Optional.ofNullable(items).map(Collections::unmodifiableList).orElse(null);
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
   * Provides the items value.
   *
   * @return the items
   */
  public List<FormatItem> getItems() {
    return Optional.ofNullable(items).map(Collections::unmodifiableList).orElse(null);
  }
}
