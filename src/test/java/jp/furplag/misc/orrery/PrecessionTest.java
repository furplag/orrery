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

package jp.furplag.misc.orrery;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import jp.furplag.misc.Astror;
import jp.furplag.time.Julian;

public class PrecessionTest {

  @Test
  public void test() {
    ZonedDateTime zdt = ZonedDateTime.parse("2001-01-01T00:00Z");
    IntStream.rangeClosed(-1000, 1000)
      .forEach(y -> {
        final double j = Julian.ofEpochMilli(zdt.withYear(y).toInstant().toEpochMilli());
        IntStream.range(0, 360)
          .forEach(d -> {
            Constructor<Precession> c;
            try {
              c = Precession.class.getDeclaredConstructor(double.class);
              c.setAccessible(true);
              Precession precession = c.newInstance(Astror.toTerrestrialTime(j));
              double actual = precession.compute(Astror.circulate(d - 180), 360 - d).optimize(d);
              assertThat(0.0 <= actual && actual <= 360.0, CoreMatchers.is(true));
              assertThat(precession.getTerrestrialTime(), CoreMatchers.is(Astror.toTerrestrialTime(j)));
              assertNotNull(precession.getLongitude());
              assertNotNull(precession.getLatitude());
            } catch (ReflectiveOperationException e) {
              e.printStackTrace();
            }
          });
      });

  }
}
