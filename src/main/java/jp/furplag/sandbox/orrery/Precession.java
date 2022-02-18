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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;

/**
 * precession of the Sun .
 *
 * @author furplag
 *
 */
public abstract class Precession {

  /**
   * the formula to calculate precession of the Sun .
   *
   * @author furplag
   *
   */
  static class Formula {/* @formatter:off */

    /** construct parameter of &zeta; . */
    static final List<Double> constOfZeta = Collections.unmodifiableList(Arrays.asList(2306.2181, 0.30188, 0.017998));

    /** construct parameter of &Zeta; . */
    static final List<Double> constOfZ = Collections.unmodifiableList(Arrays.asList(2306.2181, 1.09468, 0.018203));

    /** construct parameter of &theta; . */
    static final List<Double> constOfTheta = Collections.unmodifiableList(Arrays.asList(2004.3109, -0.42665, -0.041833));

    /**
     * a part of {@link #ofT(List, double)} .
     *
     * @param parameter construct parameter
     * @param terrestrialTime T (terrestrialized julian date)
     * @return value to generate formula
     */
    private static final double initialization(final List<Double> parameter, final double terrestrialTime) {
      return parameter.stream().mapToDouble((t) ->
        t * IntStream.range(0, parameter.indexOf(t) + 1).mapToDouble((nope)->Double.valueOf(terrestrialTime)).reduce(1.0, (o1, o2) -> o1 * o2)
      ).sum() / 3600.0 * Astror.radianizr;
    }

    /**
     * returns formula to calculate precession of the Sun .
     *
     * @param parameter construct parameter
     * @param terrestrialTime T (terrestrialized julian date)
     * @return the formula
     */
    private static Formula ofT(final List<Double> parameter, final double terrestrialTime) {
      return new Formula(initialization(parameter, terrestrialTime));
    }

    /** sine . */ private final double sine;
    /** cosine . */ private final double cosine;

    Formula(double value) {
      sine = Math.sin(value);
      cosine = Math.cos(value);
    }
  /* @formatter:on */}

  /** T (terrestrialized julian date) . */
  @Getter
  private final double terrestrialTime;

  /** &zeta; . */
  private final Formula zeta;

  /** &Zeta; . */
  private final Formula z;

  /** &theta; . */
  private final Formula theta;

  /** longitude on the Earth . */
  @Getter
  private double longitude;

  /** latitude on the Earth . */
  @Getter
  private double latitude;

  Precession(double terrestrialTime) {
    this.terrestrialTime = terrestrialTime;
    zeta = Formula.ofT(Formula.constOfZeta, terrestrialTime);
    z = Formula.ofT(Formula.constOfZ, terrestrialTime);
    theta = Formula.ofT(Formula.constOfTheta, terrestrialTime);
  }

  /**
   * initialize using location .
   *
   * @param alpha longitude
   * @param delta latitude
   * @return result
   */
  public Precession compute(final double alpha, final double delta) {
    final double r2 = rOf(alpha, delta, (zeta.cosine * z.cosine * theta.cosine), (zeta.sine * z.sine * -1), (-zeta.sine * z.cosine * theta.cosine), (zeta.cosine * z.sine * -1), (-z.cosine * theta.sine));
    final double r3 = rOf(alpha, delta, (zeta.cosine * z.sine * theta.cosine), (zeta.sine * z.cosine), (-zeta.sine * z.sine * theta.cosine), (zeta.cosine * z.cosine), (-z.sine * theta.sine));
    final double r4 = rOf(alpha, delta, (zeta.cosine * theta.sine), 0, (-zeta.sine * theta.sine), 0, theta.cosine);
    longitude = (Math.atan(r3 / r2)) + (r2 < 0 ? 180.0 : r3 < 0 ? 360.0 : 0);
    latitude = Math.asin(r4) * Astror.degreezr;

    return this;
  }

  /**
   * calculations .
   *
   * @param alpha longitude
   * @param delta latitude
   * @param f formula(s)
   * @return result
   */
  private double rOf(double alpha, double delta, double... f) {
    final double l = Math.cos(alpha) * Math.cos(delta);
    final double m = Math.sin(alpha) * Math.cos(delta);
    final double n = Math.sin(delta);

    return (f[0] + f[1]) * l + (f[2] + f[3]) * m + (f[4]) * n;
  }

  /**
   * optimize longitude with precession .
   *
   * @param longitude the longitude of the Sun
   * @return optimized longitude
   */
  public double optimize(double longitude) {
    return Astror.circulate(longitude - this.longitude);
  }
}
