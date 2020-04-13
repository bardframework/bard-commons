package org.bardframework.commons.utils;

import java.util.Random;

/**
 * <p>Utility library that supplements the standard {@link Random} class.</p>
 *
 * <p>Caveat: Instances of {@link Random} are not cryptographically secure.</p>
 */
public class RandomUtils {

    /**
     * Random object used by random method. This has to be not local to the
     * random method so as to not return the same value in the same millisecond.
     */
    private static final Random RANDOM = new Random();

    /**
     * <p>
     * {@code RandomUtils} instances should NOT be constructed in standard
     * programming. Instead, the class should be used as
     * {@code RandomUtils.nextBytes(5);}.
     * </p>
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean
     * instance to operate.
     * </p>
     */
    public RandomUtils() {
        super();
    }

    /**
     * <p>
     * Returns a random boolean value
     * </p>
     *
     * @return the random boolean
     */
    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    /**
     * <p>
     * Creates an array of random bytes.
     * </p>
     *
     * @param count the size of the returned array
     * @return the random byte array
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public static byte[] nextBytes(final int count) {
        AssertionUtils.isTrue(count >= 0, "Count cannot be negative.");

        final byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
    }

    /**
     * <p>
     * Returns a random integer within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the random integer
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
        AssertionUtils.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        AssertionUtils.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    /**
     * <p> Returns a random int within 0 - Integer.MAX_VALUE </p>
     *
     * @return the random integer
     */
    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * <p>
     * Returns a random long within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the random long
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static long nextLong(final long startInclusive, final long endExclusive) {
        AssertionUtils.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        AssertionUtils.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return (long) nextDouble(startInclusive, endExclusive);
    }

    /**
     * <p> Returns a random long within 0 - Long.MAX_VALUE </p>
     *
     * @return the random long
     */
    public static long nextLong() {
        return nextLong(0, Long.MAX_VALUE);
    }

    /**
     * <p>
     * Returns a random double within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the random double
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        AssertionUtils.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        AssertionUtils.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }

    /**
     * <p> Returns a random double within 0 - Double.MAX_VALUE </p>
     *
     * @return the random double
     */
    public static double nextDouble() {
        return nextDouble(0, Double.MAX_VALUE);
    }

    /**
     * <p>
     * Returns a random float within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the random float
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        AssertionUtils.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        AssertionUtils.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextFloat());
    }

    /**
     * <p> Returns a random float within 0 - Float.MAX_VALUE </p>
     *
     * @return the random float
     */
    public static float nextFloat() {
        return nextFloat(0, Float.MAX_VALUE);
    }
}
