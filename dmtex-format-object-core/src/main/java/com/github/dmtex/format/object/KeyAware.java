package com.github.dmtex.format.object;

/**
 * {@code KeyAware} interface is used for entities which name to be loaded from resource bundle by key.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public interface KeyAware {

  /**
   * Provides key.
   *
   * @return key
   */
  String getKey();
}
