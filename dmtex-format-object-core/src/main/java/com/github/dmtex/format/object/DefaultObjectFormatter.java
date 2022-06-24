package com.github.dmtex.format.object;

import com.github.dmtex.format.object.model.FormatGroup;
import com.github.dmtex.format.object.model.FormatItem;
import com.github.dmtex.format.object.model.FormatValue;
import com.github.dmtex.format.object.model.GroupValue;
import com.github.dmtex.format.object.model.ListValue;
import com.github.dmtex.format.object.model.SingleValue;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@code DefaultObjectFormatter} class is default implementation of {@link ObjectFormatter}.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public class DefaultObjectFormatter extends ObjectFormatter {

  private static final Map<Class<? extends FormatValue>, ValueWriter<?>> WRITERS = Map.of(
      SingleValue.class, new SingleValueWriter(),
      GroupValue.class, new GroupValueWriter(),
      ListValue.class, new ListValueWriter()
  );

  @Override
  protected void write(FormatGroup group, Writer writer) throws IOException {
    write("", group, writer);
  }

  private static void write(String indent, FormatGroup group, Writer writer) throws IOException {
    if (StringUtil.notEmpty(group.getName())) {
      newLine(writer);
      writer.write(indent);
      writer.write(group.getName());
    }
    if (Optional.ofNullable(group.getItems()).map(List::isEmpty).orElse(true)) {
      return;
    }
    newLine(writer);
    for (FormatItem item : group.getItems()) {
      writer.write(indent);
      writer.write(item.getName());
      writer.write(": ");
      FormatValue value = item.getValue();
      getWriter(value).write(value, writer, indent);
      newLine(writer);
    }
  }

  private static void newLine(Writer writer) throws IOException {
    writer.write('\n');
  }

  private static String increaseIndent(String indent) {
    return indent + "  ";
  }

  @SuppressWarnings("unchecked")
  private static <T extends FormatValue> ValueWriter<T> getWriter(FormatValue value) {
    return (ValueWriter<T>) WRITERS.get(value.getClass());
  }

  private interface ValueWriter<T extends FormatValue> {
    void write(T value, Writer writer, String indent) throws IOException;
  }

  private static final class SingleValueWriter implements ValueWriter<SingleValue> {

    @Override
    public void write(SingleValue value, Writer writer, String indent) throws IOException {
      writer.write(value.getValue());
    }
  }

  private static final class GroupValueWriter implements ValueWriter<GroupValue> {

    @Override
    public void write(GroupValue value, Writer writer, String indent) throws IOException {
      DefaultObjectFormatter.write(increaseIndent(indent), value.getValue(), writer);
    }
  }

  private static final class ListValueWriter implements ValueWriter<ListValue> {

    @Override
    public void write(ListValue value, Writer writer, String indent) throws IOException {
      for (FormatGroup g : value.getValue()) {
        DefaultObjectFormatter.write(increaseIndent(indent), g, writer);
      }
    }
  }
}
