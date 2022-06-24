package com.github.dmtex.format.object.annotation;

import com.github.dmtex.format.object.ModelBuilder.DefaultExtractor;
import com.github.dmtex.format.object.model.FormatValue;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * {@code FormatEntity} annotation marks classes to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface FormatField {

  /**
   * Provides key for the field.
   *
   * @return field's key
   */
  String value() default "";

  /**
   * Provides extractor.
   *
   * @return extractor
   */
  Class<? extends Function<?, FormatValue>> extractor() default DefaultExtractor.class;
}
