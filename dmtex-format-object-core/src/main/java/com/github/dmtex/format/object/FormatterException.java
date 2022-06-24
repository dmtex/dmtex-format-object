package com.github.dmtex.format.object;

/**
 * {@code FormatterException} class is general exception for Format Object project.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public class FormatterException extends RuntimeException {

  private static final long serialVersionUID = -5033647674203362201L;

  /**
   * Constructs a new runtime exception with the specified detail message. The cause is not initialized.
   *
   * @param message the detail message
   */
  public FormatterException(String message) {
    super(message);
  }

  /**
   * Constructs a new runtime exception with the specified detail message and cause.
   *
   * @param  message the detail message
   * @param  cause the cause
   */
  public FormatterException(String message, Throwable cause) {
    super(message, cause);
  }
}
