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

import java.io.Serializable;
import java.util.stream.Stream;

import jp.furplag.sandbox.stream.Streamr;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Ecliptic longitude of the Sun, and Moon .
 *
 * @author furplag
 *
 */
public interface EclipticLongitude {

  /**
   * the formula type to calculates Ecliptic longitude of the Sun, and Moon .
   *
   * @author furplag
   *
   */
  @RequiredArgsConstructor
  @EqualsAndHashCode
  static abstract class Formula implements Comparable<Formula>, Serializable {/* @formatter:on */

    static abstract class Exclusive extends Formula {

      Exclusive(int order, double amplitude, double angularVelocity, double initialPhase) {
        super(order, amplitude, angularVelocity, initialPhase);
      }

      Exclusive(double amplitude, double angularVelocity, double initialPhase) {
        super(amplitude, angularVelocity, initialPhase);
      }

      /** {@inheritDoc} */ @Override double amplitude(final double terrestrialTime) { return  terrestrialTime * amplitude; }
    }

    final int order;

    final double amplitude;

    final double angularVelocity;

    final double initialPhase;

    Formula(double amplitude, double angularVelocity, double initialPhase) {
      this(0, amplitude, angularVelocity, initialPhase);
    }

    /** {@inheritDoc} */ @Override public int compareTo(Formula o) { return o == null ? 1 : Integer.compare(order, o.order); }

    /**
     * just a DRY in calculation .
     *
     * @param terrestrialTime T (terrestrialized julian date)
     * @return the amplitude
     */
    double amplitude(final double terrestrialTime) {
      return amplitude;
    }

    /**
     * a part of calculation .
     *
     * @param terrestrialTime T (terrestrialized julian date)
     * @return the result
     */
    double estimate(final double terrestrialTime) {
      return amplitude(terrestrialTime) * Math.cos(((angularVelocity * terrestrialTime) + initialPhase) * Astror.radianizr);
    }

  /* @formatter:on */}

  /** the Moon. */
  static final EclipticLongitude Moon = new EclipticLongitude() {
    @Getter final Formula[] formulas = new Formula[] {
        new Formula(0.0003, 958465.0, 340.0) {}
      , new Formula(0.0003, 381404.0, 354.0) {}
      , new Formula(0.0003, 349472.0, 337.0) {}
      , new Formula(0.0003, 1808933.0, 58.0) {}
      , new Formula(0.0003, 549197.0, 220.0) {}
      , new Formula(0.0003, 4067.0, 70.0) {}
      , new Formula(0.0003, 2322131.0, 191.0) {}
      , new Formula(0.0004, 39871.0, 223.0) {}
      , new Formula(0.0004, 12006.0, 187.0) {}
      , new Formula(0.0005, 1908795.0, 90.0) {}
      , new Formula(0.0005, 1745069.0, 24.0) {}
      , new Formula(0.0005, 509131.0, 242.0) {}
      , new Formula(0.0006, 111869.0, 38.0) {}
      , new Formula(0.0006, 2258267.0, 156.0) {}
      , new Formula(0.0007, 790672.0, 114.0) {}
      , new Formula(0.0007, 405201.0, 50.0) {}
      , new Formula(0.0007, 485333.0, 186.0) {}
      , new Formula(0.0007, 27864.0, 127.0) {}
      , new Formula(0.0008, 1403732.0, 98.0) {}
      , new Formula(0.0009, 858602.0, 129.0) {}
      , new Formula(0.0011, 1920802.0, 186.0) {}
      , new Formula(0.0012, 1267871.0, 249.0) {}
      , new Formula(0.0016, 1856938.0, 152.0) {}
      , new Formula(0.0018, 401329.0, 274.0) {}
      , new Formula(0.0021, 990397.0, 357.0) {}
      , new Formula(0.0021, 71998.0, 85.0) {}
      , new Formula(0.0021, 341337.0, 16.0) {}
      , new Formula(0.0022, 818536.0, 151.0) {}
      , new Formula(0.0023, 922466.0, 163.0) {}
      , new Formula(0.0024, 99863.0, 122.0) {}
      , new Formula(0.0026, 1379739.0, 17.0) {}
      , new Formula(0.0027, 918399.0, 182.0) {}
      , new Formula(0.0028, 1934.0, 145.0) {}
      , new Formula(0.0037, 541062.0, 259.0) {}
      , new Formula(0.0038, 1781068.0, 21.0) {}
      , new Formula(0.004, 1331734.0, 283.0) {}
      , new Formula(0.004, 1844932.0, 56.0) {}
      , new Formula(0.004, 133.0, 29.0) {}
      , new Formula(0.005, 481266.0, 205.0) {}
      , new Formula(0.0052, 31932.0, 107.0) {}
      , new Formula(0.0068, 926533.0, 323.0) {}
      , new Formula(0.0079, 449334.0, 188.0) {}
      , new Formula(0.0085, 826671.0, 111.0) {}
      , new Formula(0.01, 1431597.0, 315.0) {}
      , new Formula(0.0107, 1303870.0, 246.0) {}
      , new Formula(0.011, 489205.0, 142.0) {}
      , new Formula(0.0125, 1443603.0, 52.0) {}
      , new Formula(0.0154, 75870.0, 41.0) {}
      , new Formula(0.0304, 513197.9, 222.5) {}
      , new Formula(0.0347, 445267.1, 27.9) {}
      , new Formula(0.0409, 441199.8, 47.4) {}
      , new Formula(0.0458, 854535.2, 148.2) {}
      , new Formula(0.0533, 1367733.1, 280.7) {}
      , new Formula(0.0571, 377336.3, 13.2) {}
      , new Formula(0.0588, 63863.5, 124.2) {}
      , new Formula(0.1144, 966404.0, 276.5) {}
      , new Formula(0.1851, 35999.05, 87.53) {}
      , new Formula(0.2136, 954397.74, 179.93) {}
      , new Formula(0.6583, 890534.22, 145.7) {}
      , new Formula(1.274, 413335.35, 10.74) {}
      , new Formula(6.2888, 477198.868, 44.963) {}
      , new Formula(218.3162, 0.0, 0.0) {}
      , new Formula.Exclusive(481267.8809, 0.0, 0.0) {}
    };
  };

  /** the Sun. */
  static final EclipticLongitude Sun = new EclipticLongitude() {
    @Getter final Formula[] formulas = new Formula[] {
        new Formula(0.0004, 31557.0, 161.0) {}
      , new Formula(0.0004, 29930.0, 48.0) {}
      , new Formula(0.0005, 2281.0, 221.0) {}
      , new Formula(0.0005, 155.0, 118.0) {}
      , new Formula(0.0006, 33718.0, 316.0) {}
      , new Formula(0.0007, 9038.0, 64.0) {}
      , new Formula(0.0007, 3035.0, 110.0) {}
      , new Formula(0.0007, 65929.0, 45.0) {}
      , new Formula(0.0013, 22519.0, 352.0) {}
      , new Formula(0.0015, 45038.0, 254.0) {}
      , new Formula(0.0018, 445267.0, 208.0) {}
      , new Formula(0.0018, 19.0, 159.0) {}
      , new Formula(0.002, 32964.0, 158.0) {}
      , new Formula.Exclusive(-0.0048, 35999.0, 268.0) {}

      , new Formula(0.02, 71998.1, 265.1) {}
      , new Formula(1.9147, 35999.05, 267.52) {}
      , new Formula(280.4659, 0.0, 0.0) {}

      , new Formula.Exclusive(36000.7695, 0.0, 0.0) {}
      , new Formula(-0.0057, 0.0, 0.0) {}
      , new Formula(0.0048, 1934.0, 145.0) {}
    };
  };

  /**
   * just a getter to reduce ABC .
   *
   * @return formula for calculate
   */
  Formula[] getFormulas();

  /**
   * just a getter to reduce ABC .
   *
   * @return formula for calculate
   */
  default Stream<Formula> getFormulaAsStream() {
    return Streamr.stream(getFormulas());
  }


  /**
   * a part of {@link #getEclipticLongitude(double)} .
   *
   * @param terrestrialTime T (terrestrialized julian date)
   * @return longitude of the planet in instant represented by specified julian
   *         date
   */
  private double _calculate(final double terrestrialTime) {
    return Astror.circulate(getFormulaAsStream().mapToDouble(f -> f.estimate(terrestrialTime)).sum());
  }

  /**
   * returns the longitude of the planet at a julian date that specified .
   *
   * @param julianDate the astronomical julian date
   * @return longitude of the planet in instant represented by specified julian
   *         date
   */
  default double getEclipticLongitude(final double julianDate) {
    return Astror.circulate(_calculate(Astror.toTerrestrialTime(julianDate)));
  }
}
