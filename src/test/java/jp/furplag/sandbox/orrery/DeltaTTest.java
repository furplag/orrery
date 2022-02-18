/*
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
package jp.furplag.sandbox.orrery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import jp.furplag.sandbox.time.Deamtiet;

class DeltaTTest {

  @Test
  void test() throws ReflectiveOperationException, SecurityException {
    final ZonedDateTime dateTime = Instant.parse("2001-01-01T00:00:00.000Z").atZone(ZoneOffset.UTC);
    // @formatter:off
    IntStream.rangeClosed(-5000, 5000)
    .forEach(y -> {
      final double expected = net.e175.klaus.solarpositioning.DeltaT.estimate(GregorianCalendar.from(dateTime.withYear(y)));
      final double actual = DeltaT.estimate(Deamtiet.julian.ofEpochMilli(dateTime.withYear(y).toInstant().toEpochMilli()));
      assertEquals(expected, actual, 15E-12, Objects.toString(y));
    });
    // @formatter:on
  }
}
