package com.github.dmtex.format.object;

import com.github.dmtex.format.object.model.FormatGroup;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * {@code ObjectFormatter} class is super class for all object formatters.
 * It provides functionality to build hierarchical structure of given object
 * and delegates formatting of object to its subclasses.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public abstract class ObjectFormatter {

  /**
   * Formats given object and writes formatted output to given writer.
   *
   * @param object object to be formatted
   * @param writer target writer
   */
  public final void format(Object object, Writer writer) {
    try {
      write(new ModelBuilder().build(object), writer);
      writer.flush();
    } catch (IOException e) {
      throw new FormatterException("Unexpected exception", e);
    }
  }

  /**
   * Formats given entity and returns as {@code String}.
   *
   * @param object object to be formatted
   * @return formatted entity
   */
  public final String format(Object object) {
    StringWriter writer = new StringWriter();
    format(object, writer);
    return writer.toString();
  }

  /**
   * Writes given {@link FormatGroup} object to given writer.
   *
   * @param group  hierarchical object to be formatted
   * @param writer writer
   * @throws IOException if unable to write formatted data
   */
  protected abstract void write(FormatGroup group, Writer writer) throws IOException;
}
