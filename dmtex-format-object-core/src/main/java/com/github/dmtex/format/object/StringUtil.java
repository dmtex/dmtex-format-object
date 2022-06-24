package com.github.dmtex.format.object;

/**
 * {@code StringUtil} class provides various useful methods for working with Strings.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class StringUtil {

  private StringUtil() {
  }

  /**
   * Checks if given string value is not empty.
   *
   * @param value value to be checked
   * @return {@code true} if value is not empty
   */
  public static boolean notEmpty(String value) {
    if (value == null || value.isEmpty()) {
      return false;
    }
    for (int i = 0; i < value.length(); i++) {
      if (!Character.isWhitespace(value.charAt(i))) {
        return true;
      }
    }
    return false;
  }
}
