package com.github.dmtex.format.object;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

final class ReflectionUtil {

  private ReflectionUtil() {
  }

  static List<Field> getFields(Object object) {
    Class<?> type = object.getClass();
    List<Field> fields = new ArrayList<>();
    while (!Object.class.equals(type)) {
      List<Field> list = new ArrayList<>();
      for (Field field : type.getDeclaredFields()) {
        list.add(field);
      }
      fields.addAll(0, list);
      type = type.getSuperclass();
    }
    return fields;
  }

  static Object getFieldValue(Field field, Object object) {
    return AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
      try {
        field.setAccessible(true);
        return field.get(object);
      } catch (IllegalAccessException e) {
        throw new FormatterException("Illegal access to the field", e);
      }
    });
  }
}
