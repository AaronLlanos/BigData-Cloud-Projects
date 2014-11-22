package com.refactorlabs.cs378.utils;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

import java.util.Iterator;

/**
 * LongArrayWritable class allows multiple long values to be written
 * and read as a single value.
 *
 * Several helper methods have been added to facilitate the use of this class.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class LongArrayWritable extends ArrayWritable {

	public LongArrayWritable() {
		super(LongWritable.class);
	}

	public LongArrayWritable(LongWritable[] values) {
		this();
		this.set(values);
	}

	/**
	 * Returns an array of longs representing the values stored here.
	 *
	 * @return Array of longs
	 */
	public long[] getValueArray() {
		Writable[] writableValues = get();
		long[] values = new long[writableValues.length];

		for ( int i = 0; i < values.length; i++ ) {
			values[i] = ((LongWritable)writableValues[i]).get();
		}

		return values;
	}

	/**
	 * Given an array of longs, creates an instance of LongArrayWritable
	 * containing those values.
	 *
	 * @param valueArray An array of longs to initialize the LongArrayWritable instance.
	 * @return Instance of LongArrayWritable containing the given values
	 */
	public static LongArrayWritable make(long[] valueArray) {
		Writable[] writableValues = new Writable[valueArray.length];

		for ( int i = 0; i < valueArray.length; i++ ) {
			writableValues[i] = new LongWritable(valueArray[i]);
		}

		LongArrayWritable returnValue = new LongArrayWritable();
		returnValue.set(writableValues);

		return returnValue;
	}

	/**
	 * Given an Iterable over LongArrayWritable, iterates over the instances
	 * and computes a position-wise sum of the values.  The resulting sum is
	 * returned as an array of longs.
	 *
	 * @param values Iterable over LongArrayWritable, containing the values to sum
	 * @return Array of longs representing the position-wise sum
	 */
	public static long[] sum(Iterable<LongArrayWritable> values) {
		Iterator<LongArrayWritable> iterator = values.iterator();
		long[] sum = null;

		if (iterator.hasNext()) {
			sum = iterator.next().getValueArray();
			while (iterator.hasNext()) {
				long[] addend = iterator.next().getValueArray();
				for ( int i = 0; i < sum.length; i++ ) {
					sum[i] += addend[i];
				}
			}
		} else {
			sum = new long[0];
		}

		return sum;
	}

	/**
	 * Generates a string representation of the long values contained in the object.
	 * String representation has commas between the string values of the longs.
	 *
	 * @return String representing the long values (comma separated).
	 */
	@Override
	public String toString() {
		return WritableUtils.toString(this.get());
	}

	/**
	 * Compares this object against the provided object for equality.
	 * The internal long arrays are compared element by element to determine equality.
	 * Array lengths must be equal.
	 *
	 * @param o Target object
	 * @return Returns true if the internal arrays contain long values that are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LongArrayWritable)) {
			return false;
		}

		long[] valueArray = getValueArray();
		long[] targetValueArray = ((LongArrayWritable)o).getValueArray();

		if (valueArray != targetValueArray) {
			// Not both null or the same array, so we need to look at the values.
			if (valueArray == null || targetValueArray == null || valueArray.length != targetValueArray.length) {
				return false;
			}

			for ( int i = 0; i < valueArray.length; i++ ) {
				if (valueArray[i] != targetValueArray[i]) {
					return false;
				}
			}
		}
		return true;
	}
}
