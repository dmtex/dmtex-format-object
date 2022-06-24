package com.github.dmtex.format.object;

import com.github.dmtex.format.object.model.AnnotatedSample;
import com.github.dmtex.format.object.model.NoFieldsSample;
import com.github.dmtex.format.object.model.Sample;
import com.github.dmtex.format.object.model.SampleType;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultObjectFormatterTest {

  private final ObjectFormatter formatter = new DefaultObjectFormatter();

  @Test
  void testFormat() {
    // Given
    Sample a = new Sample("a", BigDecimal.ONE, null, null);
    Sample b = new Sample("b", BigDecimal.TEN, a, List.of("a"));
    AnnotatedSample sample = new AnnotatedSample("sample", 2.0, LocalDate.now(), SampleType.NAME, List.of(a, b));

    // When
    String actual = formatter.format(sample);

    // Then
    String expected = "\n"
        + "sample\n"
        + "date: XYZ\n"
        + "value: 2.0\n"
        + "sample_type: Name\n"
        + "children: \n"
        + "  title: a\n"
        + "  value: 1\n"
        + "\n"
        + "  title: b\n"
        + "  value: 10\n"
        + "  child: \n"
        + "    title: a\n"
        + "    value: 1\n"
        + "\n"
        + "  lines: \n"
        + "    a\n"
        + "\n";
    assertEquals(expected, actual);
  }

  @Test
  void testFormatImplicitFields() {
    // Given
    NoFieldsSample sample = new NoFieldsSample("sample", "desc", 1);

    // When
    String actual = formatter.format(sample);

    // Then
    String expected = "\n"
        + "name: sample\n"
        + "value: 1\n";
    assertEquals(expected, actual);
  }

  @Test
  void testMissingRequired() {
    assertAll(
        () -> assertThrows(FormatterException.class, () -> formatter.format("sample"), "Unsupported class"),
        () -> assertThrows(FormatterException.class,
            () -> formatter.format(new AnnotatedSample("a", 0, null, SampleType.UNKNOWN, null)))
    );
  }

  @Test
  void testInOutFailure() {
    NoFieldsSample sample = new NoFieldsSample("sample", "desc", 1);
    Writer brokenWriter = new Writer() {
      @Override
      public void write(char[] cbuf, int off, int len) {
      }

      @Override
      public void flush() throws IOException {
        throw new IOException();
      }

      @Override
      public void close() {
      }
    };
    assertThrows(FormatterException.class, () -> formatter.format(sample, brokenWriter));
  }
}
