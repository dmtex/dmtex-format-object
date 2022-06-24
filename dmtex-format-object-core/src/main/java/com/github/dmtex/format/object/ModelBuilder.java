package com.github.dmtex.format.object;

import com.github.dmtex.format.object.annotation.FormatEntity;
import com.github.dmtex.format.object.annotation.FormatField;
import com.github.dmtex.format.object.model.FormatGroup;
import com.github.dmtex.format.object.model.FormatItem;
import com.github.dmtex.format.object.model.FormatValue;
import com.github.dmtex.format.object.model.GroupValue;
import com.github.dmtex.format.object.model.ListValue;
import com.github.dmtex.format.object.model.SingleValue;
import com.github.dmtex.format.object.spi.ObjectFormatterServiceProvider;
import java.lang.reflect.Field;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * {@code ModelBuilder} class is responsible for building {@link FormatGroup} models.
 * The class is not thread safe.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
public final class ModelBuilder {

  private static final Set<Class<?>> PRIMITIVE_TYPES = Set.of(CharSequence.class, Number.class, TemporalAccessor.class);

  private final List<ResourceBundle> resourceBundles = new ArrayList<>();

  private final Map<Class<?>, Function<?, FormatValue>> extractors = new HashMap<>();

  private final DefaultExtractor defaultExtractor = new DefaultExtractor();

  private final Map<Object, FormatEntity> cache = new IdentityHashMap<>();

  ModelBuilder() {
    extractors.put(DefaultExtractor.class, defaultExtractor);
    extractors.put(KeyAwareExtractor.class, new KeyAwareExtractor());
    ServiceLoader.load(ObjectFormatterServiceProvider.class).forEach(provider -> {
      resourceBundles.addAll(provider.resourceBundles());
      extractors.putAll(provider.extractors().stream().collect(Collectors.toMap(p -> p.getClass(), p -> p)));
    });
  }

  /**
   * Builds {@link FormatGroup} model from given object.
   *
   * @param object object should not be null
   * @return model
   */
  public FormatGroup build(Object object) {
    Objects.requireNonNull(object);
    FormatEntity formatEntity = cache.getOrDefault(object, object.getClass().getAnnotation(FormatEntity.class));
    if (formatEntity == null) {
      throw new FormatterException("Unsupported class (not annotated): " + object.getClass());
    }
    return build(object, formatEntity.value(), formatEntity.fields());
  }

  private FormatGroup build(Object object, String nameField, String[] fieldNames) {
    List<Field> fields = ReflectionUtil.getFields(object);
    Map<String, Field> map = fields.stream().collect(Collectors.toMap(Field::getName, f -> f, (a, b) -> a));
    String name = nameField.isEmpty() ? nameField
        : String.valueOf(ReflectionUtil.getFieldValue(map.get(nameField), object));
    if (fieldNames.length == 0) {
      return build(object, name,
          fields.stream().filter(f -> Objects.nonNull(f.getAnnotation(FormatField.class))).collect(toList()));
    }
    return build(object, name,
        Stream.of(fieldNames).map(map::get).peek(Objects::requireNonNull).collect(toList()));
  }

  private FormatGroup build(Object object, String name, List<Field> fields) {
    List<FormatItem> items = new ArrayList<>();
    for (Field field : fields) {
      buildField(field, object).ifPresent(items::add);
    }
    return new FormatGroup(name, items);
  }

  private Optional<FormatItem> buildField(Field field, Object object) {
    Object value = ReflectionUtil.getFieldValue(field, object);
    if (value == null) {
      return Optional.empty();
    }
    ofNullable(field.getAnnotation(FormatEntity.class)).ifPresent(a -> cache.put(value, a));
    FormatField formatField = field.getAnnotation(FormatField.class);
    if (formatField == null) {
      return Optional.of(new FormatItem(field.getName(), defaultExtractor.apply(value)));
    }
    String name = findByKey(formatField.value())
        .orElseGet(() -> formatField.value().isEmpty() ? field.getName() : formatField.value());
    return Optional.of(new FormatItem(name, getExtractor(formatField.extractor()).apply(value)));
  }

  @SuppressWarnings("unchecked")
  private Function<Object, FormatValue> getExtractor(Class<? extends Function<?, FormatValue>> type) {
    return (Function<Object, FormatValue>) ofNullable(extractors.get(type)).orElseThrow();
  }

  private boolean isPrimitive(Class<?> type) {
    for (Class<?> c : PRIMITIVE_TYPES) {
      if (c.isAssignableFrom(type)) {
        return true;
      }
    }
    return type.isPrimitive();
  }

  private Optional<String> findByKey(String key) {
    return resourceBundles.stream()
        .filter(b -> b.containsKey(key))
        .findFirst()
        .map(b -> b.getString(key));
  }

  /**
   * {@code DefaultExtractor} class is default model extractor.
   */
  public final class DefaultExtractor implements Function<Object, FormatValue> {

    @Override
    public FormatValue apply(Object value) {
      if (value instanceof Iterable) {
        List<FormatGroup> list = new ArrayList<>();
        FormatEntity cached = cache.get(value);
        for (Object o : (Iterable<?>) value) {
          ofNullable(cached).ifPresent(c -> cache.put(o, c));
          if (isPrimitive(o.getClass())) {
            list.add(new FormatGroup(String.valueOf(o), null));
          } else {
            list.add(ModelBuilder.this.build(o));
          }
        }
        return new ListValue(list);
      }
      Class<?> c = value.getClass();
      if (isPrimitive(c)) {
        return new SingleValue(String.valueOf(value));
      }
      return new GroupValue(ModelBuilder.this.build(value));
    }
  }

  /**
   * {@code KeyAwareExtractor} class extract.
   */
  public class KeyAwareExtractor implements Function<KeyAware, FormatValue> {

    @Override
    public SingleValue apply(KeyAware object) {
      return findByKey(object.getKey()).map(SingleValue::new)
          .orElseThrow(() -> new FormatterException("Unknown resource key: " + object.getKey()));
    }
  }
}
