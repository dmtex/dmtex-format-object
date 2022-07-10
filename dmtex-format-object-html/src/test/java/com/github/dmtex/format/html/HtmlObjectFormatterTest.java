package com.github.dmtex.format.html;

import com.github.dmtex.format.html.model.AnnotatedSample;
import com.github.dmtex.format.html.model.Sample;
import com.github.dmtex.format.object.ObjectFormatter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlObjectFormatterTest {

  private final ObjectFormatter formatter = new HtmlObjectFormatter();

  @Test
  void testFormat() {
    // Given
    Sample a = new Sample("a", BigDecimal.ONE);
    Sample b = new Sample("b", BigDecimal.TEN);
    AnnotatedSample sample = new AnnotatedSample("sample", 2.0, a, List.of(a, b));

    // When
    String actual = formatter.format(sample);

    // Then
    String expected = "<html><body>"
        + "<table cellspacing=\"5\" cellpadding=\"5\">"
        + "<tr><th colspan=\"2\">sample</th></tr><tr><td valign=\"top\">value</td><td>2.0</td></tr>"
        + "<tr><td valign=\"top\">sample</td><td>"
        + "<table cellspacing=\"2\" cellpadding=\"2\">"
        + "<tr><td valign=\"top\">title</td><td>a</td></tr>"
        + "<tr><td valign=\"top\">value</td><td>1</td></tr>"
        + "</table>"
        + "</td></tr>"
        + "<tr><td valign=\"top\">children</td><td>"
        + "<div>"
        + "<table cellspacing=\"2\" cellpadding=\"2\">"
        + "<tr><td valign=\"top\">title</td><td>a</td></tr>"
        + "<tr><td valign=\"top\">value</td><td>1</td></tr>"
        + "</table>"
        + "<table cellspacing=\"2\" cellpadding=\"2\">"
        + "<tr><td valign=\"top\">title</td><td>b</td></tr>"
        + "<tr><td valign=\"top\">value</td><td>10</td></tr>"
        + "</table><hr>"
        + "</div>"
        + "</td></tr>"
        + "</table>"
        + "</body></html>";

    assertEquals(expected, actual);
  }
}
