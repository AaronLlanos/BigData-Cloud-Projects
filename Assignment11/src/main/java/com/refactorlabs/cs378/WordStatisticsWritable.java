package com.refactorlabs.cs378;

import com.refactorlabs.cs378.utils.NumericArrayWritable;

import java.util.Iterator;

/**
 * Writable word statistics object.
 * Maintains the fields:
 * As longs:
 * - Document count
 * - Total count
 * - Sum of squares
 * As doubles:
 * - Mean (per document)
 * - Variance
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WordStatisticsWritable extends NumericArrayWritable {

	/**
	 * The number of long values in word statistics.
	 */
	private static final int LONG_COUNT = 3;
	/**
	 * The number of double values in word statistics.
	 */
	private static final int DOUBLE_COUNT = 2;

	/**
	 * Array indices for the long values
	 */
	private static final int DOCUMENT_COUNT_INDEX = 0;
	private static final int TOTAL_COUNT_INDEX = 1;
	private static final int SQUARES_SUM_INDEX = 2;

	/**
	 * Array indices for the double values
	 */
	private static final int MEAN_INDEX = 0;
	private static final int VARIANCE_INDEX = 1;

	/**
	 * Creates an instance of WordStatisticsWritable.  The expected array lengths
	 * are passed to NumericArrayWritable.
	 */
	public WordStatisticsWritable() {
		super(LONG_COUNT, DOUBLE_COUNT);
	}

	/**
	 * Creates an instance of WordStatisticsWritable with the specified values.
	 */
	public WordStatisticsWritable(long[] longValues, double[] doubleValues) {
		super(longValues, doubleValues);
	}

	/**
	 * Sets the value for documentCount.
	 */
	public void setDocumentCount(long count) { this.setLongValue(count, DOCUMENT_COUNT_INDEX); }

	/**
	 * Sets the value for totalCount.
	 */
	public void setTotalCount(long count) {
		this.setLongValue(count, TOTAL_COUNT_INDEX);
	}

	/**
	 * Sets the value for sumOfSquares.
	 */
	public void setSquaresSumIndex(long sum) { this.setLongValue(sum, SQUARES_SUM_INDEX); }

	/**
	 * Sets the value for the mean.
	 */
	private void setMean(double mean) { this.setDoubleValue(mean, MEAN_INDEX); }

	/**
	 * Sets the value for the variance.
	 */
	private void setVariance(double variance) { this.setDoubleValue(variance, VARIANCE_INDEX); }

	/**
	 * Resets all the values in this object to zero.
	 */
	public void clear() {
		for ( int i = 0; i < LONG_COUNT; i++ ) {
			this.setLongValue(0L, i);
		}
		for ( int i = 0; i < DOUBLE_COUNT; i++ ) {
			this.setDoubleValue(0.0, i);
		}
	}

	/**
	 * Sum up the counts (long values) in "values", compute the mean
	 * and variance, and return a WordStatisticsWritable with those values.
	 *
	 * @param values List of value to sum.
	 * @return Instance with the summed values, mean , and variance.
	 */
	public static WordStatisticsWritable sum(Iterable<WordStatisticsWritable> values) {
		Iterator<WordStatisticsWritable> valueIterator = values.iterator();

		if (!valueIterator.hasNext()) {
			return new WordStatisticsWritable();
		}

		long[] counts = valueIterator.next().getLongValues();

		// Sum up the long arrays (position by position) from "values".
		while (valueIterator.hasNext()) {
			long[] addend = valueIterator.next().getLongValues();

			for ( int i = 0; i < LONG_COUNT; i++ ) {
				counts[i] += addend[i];
			}
		}

		WordStatisticsWritable writable = new WordStatisticsWritable();
		writable.setLongValues(counts);

		// Compute the mean and variance.
		double mean = 0.0;
		double variance = 0.0;

		if (counts[DOCUMENT_COUNT_INDEX] <= 1) {
			// Only one occurrence (for example, once in one document), so stats are straightforward.
			mean = (double)counts[TOTAL_COUNT_INDEX];
			variance = 0.0;
		} else {
			mean = (double) counts[TOTAL_COUNT_INDEX] / counts[DOCUMENT_COUNT_INDEX];
			variance = ((double) counts[SQUARES_SUM_INDEX] / counts[DOCUMENT_COUNT_INDEX]) - (mean * mean);
		}
		writable.setMean(mean);
		writable.setVariance(variance);

		return writable;
	}

	/**
	 * Given a printed representation of word stats (output via toString()), parse
	 * the fields and return a WordStatisticsWritable instance with those values.
	 * Input string does not include the long and double array lengths.
	 *
	 * @param text Comma separated values
	 * @return Instance with the values specified in "text"
	 */
	public static WordStatisticsWritable parse(String text) {
		String[] textFields = text.split(",");
		long[] counts = new long[LONG_COUNT];
		double[] stats = new double[DOUBLE_COUNT];
		int fieldIndex = 0;

		for ( int i = 0; i < LONG_COUNT; i++ ) {
			counts[i] = Integer.parseInt(textFields[fieldIndex++]);
		}
		for ( int i = 0; i < DOUBLE_COUNT; i++ ) {
			stats[i] = Double.parseDouble(textFields[fieldIndex]);
		}

		return new WordStatisticsWritable(counts, stats);
	}

	/**
	 * Create the string representation of the word stats
	 * Note that we are not including the size of the long and double arrays,
	 * so don't expect them in the input (when parsing).
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		long[] longValues = getLongValues();
		double[] doubleValues = getDoubleValues();

		buf.append(Long.toString(longValues[0]));
		for ( int i = 1; i < longValues.length; i++ ) {
			buf.append(",");
			buf.append(Long.toString(longValues[i]));
		}
		for ( int i = 0; i < doubleValues.length; i++ ) {
			buf.append(",");
			buf.append(Double.toString(doubleValues[i]));
		}

		return buf.toString();
	}

}
