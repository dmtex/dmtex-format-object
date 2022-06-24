package com.github.dmtex.format.object.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code FormatEntity} annotation marks classes to be formatted.
 *
 * @author Denis Murashev
 *
 * @since Format Object 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD})
public @interface FormatEntity {

  /**
   * Provides entity's title field name.
   *
   * @return title field name
   */
  String value() default "";

  /**
   * Provides entity's fields names.
   *
   * @return fields names
   */
  String[] fields() default {};
}
