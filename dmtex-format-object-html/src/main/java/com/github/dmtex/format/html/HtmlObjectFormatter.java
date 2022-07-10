package com.github.dmtex.format.html;

import com.github.dmtex.format.object.ObjectFormatter;
import com.github.dmtex.format.object.model.FormatGroup;
import com.github.dmtex.format.object.model.FormatValue;
import com.github.dmtex.format.object.model.GroupValue;
import com.github.dmtex.format.object.model.ListValue;
import com.github.dmtex.format.object.model.SingleValue;
import j2html.rendering.FlatHtml;
import j2html.tags.DomContent;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.hr;
import static j2html.TagCreator.html;
import static j2html.TagCreator.span;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.text;
import static j2html.TagCreator.th;
import static j2html.TagCreator.tr;
import static java.util.Optional.ofNullable;

/**
 * {@code HtmlObjectFormatter} class provides formatting object data as HTML.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public class HtmlObjectFormatter extends ObjectFormatter {

  private static final Map<Class<? extends FormatValue>, ValueBuilder<? extends FormatValue>> BUILDERS = Map.of(
      SingleValue.class, new SingleValueBuilder(),
      GroupValue.class, new GroupValueBuilder(),
      ListValue.class, new ListValueBuilder()
  );

  @Override
  protected void write(FormatGroup group, Writer writer) throws IOException {
    final int size = 5;
    html(
        body(
            buildTable(group, size)
        )
    ).render(FlatHtml.into(writer));
    writer.flush();
  }

  private static DomContent buildTable(FormatGroup entity, int size) {
    Boolean noName = ofNullable(entity.getName()).map(String::isEmpty).orElse(true);
    Boolean noItems = ofNullable(entity.getItems()).map(List::isEmpty).orElse(true);
    if (noName && noItems) {
      return span();
    }
    return table()
        .condWith(!noName, tr(th(entity.getName()).attr("colspan", 2)))
        .condWith(!noItems, each(entity.getItems(), item -> tr(
            td(item.getName()).attr("valign", "top"),
            td(build(item.getValue()))
        ))
    ).attr("cellspacing", size).attr("cellpadding", size);
  }

  @SuppressWarnings("unchecked")
  private static <T extends FormatValue> DomContent build(T value) {
    ValueBuilder<T> builder = (ValueBuilder<T>) BUILDERS.get(value.getClass());
    return builder.apply(value);
  }

  private interface ValueBuilder<T extends FormatValue> extends Function<T, DomContent> {
  }

  private static final class SingleValueBuilder implements ValueBuilder<SingleValue> {

    @Override
    public DomContent apply(SingleValue value) {
      return text(value.getValue());
    }
  }

  private static final class GroupValueBuilder implements ValueBuilder<GroupValue> {

    @Override
    public DomContent apply(GroupValue value) {
      return buildTable(value.getValue(), 2);
    }
  }

  private static final class ListValueBuilder implements ValueBuilder<ListValue> {

    @Override
    public DomContent apply(ListValue value) {
      return div(
          each(value.getValue(), item -> buildTable(item, 2)),
          hr()
      );
    }
  }
}
