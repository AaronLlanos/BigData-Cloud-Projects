package com.refactorlabs.cs378.utils;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Writable;

import java.util.Iterator;

/**
 * DoubleArrayWritable class allows multiple double values to be written
 * and read as a single value.
 *
 * Several helper methods have been added to facilitate the use of this class.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class DoubleArrayWritable extends ArrayWritable {

	public DoubleArrayWritable() {
		super(DoubleWritable.class);
	}

	public DoubleArrayWritable(DoubleWritable[] values) {
		this();
		this.set(values);
	}

	/**
	 * Returns an array of doubles representing the values stored here.
	 *
	 * @return Array of doubles
	 */
	public double[] getValueArray() {
		Writable[] writableValues = get();
		double[] values = new double[writableValues.length];

		for ( int i = 0; i < values.length; i++ ) {
			values[i] = ((DoubleWritable)writableValues[i]).get();
		}

		return values;
	}

	/**
	 * Given an array of doubles, creates an instance of DoubleArrayWritable
	 * containing those values.
	 *
	 * @param valueArray An array of doubles to initialize the DoubleArrayWritable instance.
	 * @return Instance of DoubleArrayWritable containing the given values
	 */
	public static DoubleArrayWritable make(double[] valueArray) {
		Writable[] writableValues = new Writable[valueArray.length];

		for ( int i = 0; i < valueArray.length; i++ ) {
			writableValues[i] = new DoubleWritable(valueArray[i]);
		}

		DoubleArrayWritable returnValue = new DoubleArrayWritable();
		returnValue.set(writableValues);

		return returnValue;
	}

	/**
	 * Given an Iterable over DoubleArrayWritable, iterates over the instances
	 * and computed a position-wise sum of the values.  The resulting sum is
	 * returned as an array of doubles.
	 *
	 * @param values Iterable over DoubleArrayWritable, containing the values to sum
	 * @return Array of doubles representing the position-wise sum
	 */
	public static double[] sum(Iterable<DoubleArrayWritable> values) {
		Iterator<DoubleArrayWritable> iterator = values.iterator();
		double[] sum = null;

		if (iterator.hasNext()) {
			sum = iterator.next().getValueArray();
			while (iterator.hasNext()) {
				double[] addend = iterator.next().getValueArray();
				for ( int i = 0; i < sum.length; i++ ) {
					sum[i] += addend[i];
				}
			}
		} else {
			sum = new double[0];
		}

		return sum;
	}

	/**
	 * Generates a string representation of the double values contained in the object.
	 * String representation has commas between the string values of the doubles.
	 *
	 * @return String representing the double values (comma separated).
	 */
	@Override
	public String toString() {
		return WritableUtils.toString(this.get());
	}

	/**
	 * Compares this object against the provided object for equality.
	 * The internal double arrays are compared element by element to determine equality.
	 * Array lengths must be equal.
	 *
	 * @param o Target object
	 * @return Returns true if the internal arrays contain double values that are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DoubleArrayWritable)) {
			return false;
		}

		double[] valueArray = getValueArray();
		double[] targetValueArray = ((DoubleArrayWritable)o).getValueArray();

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
