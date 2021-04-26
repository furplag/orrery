/**
 * Copyright (C) 2018+ furplag (https://github.com/furplag)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.furplag.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import jp.furplag.sandbox.time.Deamtiet;

class AstrorTest {

  @Test
  void test() throws ReflectiveOperationException, SecurityException {
    Constructor<Astror> c = Astror.class.getDeclaredConstructor();
    c.setAccessible(true);
    assertTrue(c.newInstance() instanceof Astror);
    assertEquals(2.0240131997637844E-8d, Astror.toTerrestrialTime(Deamtiet.j2000));
  }

  @Test
  void testCirculate() {
    assertEquals(0d, Astror.circulate(0));
    assertEquals(0d, Astror.circulate(-0));
    assertEquals(90d, Astror.circulate(90));
    assertEquals(0d, Astror.circulate(360));
    assertEquals(270d, Astror.circulate(-90));
    assertEquals(0d, Astror.circulate(360*360));
    assertEquals(0d, Astror.circulate(360*-360));
    assertEquals(90d, Astror.circulate(90*25));
    assertEquals(270d, Astror.circulate(90*-25));
    assertEquals(123.123454321d, BigDecimal.valueOf(Astror.circulate(36123.123454321)).setScale(9, RoundingMode.HALF_UP).doubleValue());
    assertEquals(123.123454321d, BigDecimal.valueOf(Astror.circulate(-236.876545679)).setScale(9, RoundingMode.HALF_UP).doubleValue());
  }

  private static double decimalYear(final Method decimalYear, final GregorianCalendar forDate) {
    try {
      return (double) decimalYear.invoke(null, forDate);
   } catch (ReflectiveOperationException | SecurityException e) {}

   return 0.0;
  }

  @Test
  void testYearize() throws ReflectiveOperationException, SecurityException {
    final ZonedDateTime dateTime = Instant.parse("2001-01-01T00:00:00.000Z").atZone(ZoneOffset.UTC);
    final Method decimalYear = net.e175.klaus.solarpositioning.DeltaT.class.getDeclaredMethod("decimalYear", GregorianCalendar.class);
    decimalYear.setAccessible(true);

    IntStream.rangeClosed(-10000, 10000).forEach(y -> {/* @formatter:off */
      final double expected = decimalYear(decimalYear, GregorianCalendar.from(dateTime.withYear(y)));
      final double actual = Astror.yearize(Deamtiet.julian.ofEpochMilli(dateTime.withYear(y).toInstant().toEpochMilli()));
      assertEquals(expected, actual, Objects.toString(y));
    /* @formatter:on */});
  }
}
