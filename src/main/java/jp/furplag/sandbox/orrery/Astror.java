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

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import jp.furplag.sandbox.time.Deamtiet;

/**
 * code snippet for any of calculation .
 *
 * @author furplag
 *
 */
public interface Astror {

  /** radian to angle . */
  static final double radianizr = Math.PI / 180.0;

  /** angle to radian . */
  static final double degreezr = 180.0 / Math.PI;

  /**
   * normalize the degree to range of 0&deg; - 360&deg; .
   *
   * @param degree the degree
   * @return normalized degree to range of 0&deg; - 360&deg;
   */
  static double circulate(final double degree) {
    return degree % 360.0 == 0 ? 0 : ((degree % 360.0) + (degree < 0.0 ? 360.0 : 0.0));
  }

  /**
   * calculates the delta between Universal Time (UT) and Terrestrial Time (TT) .
   *
   * @param julianDate the astronomical julian date
   * @return &Delta;T
   */
  static double toTerrestrialTime(final double julianDate) {
    return (julianDate - Deamtiet.j2000 + (getDeltaOfT(julianDate))) / (Deamtiet.daysOfYearOfJulian * 100.0);
  }

  /**
   * calculate the delta between Universal Time (UT) and Terrestrial Time (TT) .
   *
   * @param julianDate the astronomical julian date
   * @return &Delta;T
   */
  static double getDeltaOfT(final double julianDate) {
    return Deamtiet.julian.ofEpochMilli(((long) (DeltaT.estimate(julianDate) * 1000))) - Deamtiet.epochAsJulianDate;
  }

  /**
   * returns the decimal year represented by specified AJD .
   *
   * @param julianDate the astronomical julian date
   * @return the decimal year represented by specified AJD
   */
  static double yearize(final double julianDate) {
    final ZonedDateTime utc = Deamtiet.julian.toInstant(julianDate).atZone(ZoneOffset.UTC);

    return (utc.getYear() == 0 ? -1 : utc.getYear() < 0 ? (utc.getYear() - 1) : utc.getYear()) + ((utc.getMonthValue() - .5) / 12);
  }
}
