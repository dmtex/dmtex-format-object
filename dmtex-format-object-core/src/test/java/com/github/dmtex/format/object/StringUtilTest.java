package com.github.dmtex.format.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilTest {

  @Test
  void testNotEmty() {
    assertAll(
        () -> assertFalse(StringUtil.notEmpty(null), "null value"),
        () -> assertFalse(StringUtil.notEmpty(""), "empty value"),
        () -> assertFalse(StringUtil.notEmpty(" "), "only spaces value"),
        () -> assertTrue(StringUtil.notEmpty("a"), "not empty value")
    );
  }
}
