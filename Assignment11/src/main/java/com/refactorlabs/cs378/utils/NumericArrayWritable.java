package com.refactorlabs.cs378.utils;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by davidfranke on 9/21/14.
 */
public class NumericArrayWritable implements Writable {

	private long[] longValues;
	private double[] doubleValues;

	public NumericArrayWritable(int longCount, int doubleCount) {
		this.longValues = new long[longCount];
		this.doubleValues = new double[doubleCount];
	}

	/**
	 * Creates a NumericArray with the values specified.  The input arrays
	 * are copied on object creation.
	 *
	 * @param longValues
	 * @param doubleValues
	 */
	public NumericArrayWritable(long[] longValues, double[] doubleValues) {
		this.setValues(longValues, doubleValues);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		longValues = new long[in.readInt()];
		for ( int i = 0; i < longValues.length; i++ ) {
			longValues[i] = in.readLong();
		}
		doubleValues = new double[in.readInt()];
		for ( int i = 0; i < doubleValues.length; i++ ) {
			doubleValues[i] = in.readDouble();
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(longValues.length);
		for ( int i = 0; i < longValues.length; i++ ) {
			out.writeLong(longValues[i]);
		}
		out.writeInt(doubleValues.length);
		for ( int i = 0; i < doubleValues.length; i++ ) {
			out.writeDouble(doubleValues[i]);
		}
	}

	/**
	 * Returns the long array stored in this object.
	 */
	public long[] getLongValues() {
		long[] returnValues = new long[longValues.length];

		for ( int i = 0; i < longValues.length; i++ ) {
			returnValues[i] = longValues[i];
		}

		return returnValues;
	}

	/**
	 * Returns the double array stored in this object.
	 */
	public double[] getDoubleValues() {
		double[] returnValues = new double[doubleValues.length];

		for ( int i = 0; i < doubleValues.length; i++ ) {
			returnValues[i] = doubleValues[i];
		}

		return returnValues;
	}

	/**
	 * Sets the long values in this object.  The array of values is copied.
	 */
	public void setLongValues(long[] values) {
		if (values == null) {
			return;
		}

		if (longValues == null || longValues.length != values.length) {
			longValues = new long[values.length];
		}
		for ( int i = 0; i < values.length; i++ ) {
			longValues[i] = values[i];
		}
	}

	/**
	 * Sets the long value at the specified index.
	 */
	public void setLongValue(long value, int index) {
		try {
			longValues[index] = value;
		} catch (IndexOutOfBoundsException ioobe) {
			// The set is ignored.
		}
	}

	/**
	 * Sets the double values in this object.  The array of values is copied.
	 */
	public void setDoubleValues(double[] values) {
		if (values == null) {
			return;
		}

		if (doubleValues == null || doubleValues.length != values.length) {
			doubleValues = new double[values.length];
		}
		for ( int i = 0; i < values.length; i++ ) {
			doubleValues[i] = values[i];
		}
	}

	/**
	 * Sets the double value at the specified index.
	 */
	public void setDoubleValue(double value, int index) {
		try {
			doubleValues[index] = value;
		} catch (IndexOutOfBoundsException ioobe) {
			// The set is ignored.
		}
	}

	/**
	 * Sets the long and double values of this object.  Value arrays are copied.
	 */
	public void setValues(long[] longValues, double[] doubleValues) {
		setLongValues(longValues);
		setDoubleValues(doubleValues);
	}

	/**
	 * String representation of NumericArrayWritable.  The count of the number
	 * of longs precedes the longs values, and a count of the number of doubles
	 * precedes the double values.
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		if (longValues == null) {
			buf.append("0");
		} else {
			buf.append(Integer.toString(longValues.length));
		}
		for ( int i = 0; i < longValues.length; i++ ) {
			buf.append(",");
			buf.append(Long.toString(longValues[i]));
		}

		if (doubleValues == null) {
			buf.append(",0");
		} else {
			buf.append(",");
			buf.append(Integer.toString(doubleValues.length));
		}
		for ( int i = 0; i < doubleValues.length; i++ ) {
			buf.append(",");
			buf.append(Double.toString(doubleValues[i]));
		}
		return buf.toString();
	}

	/**
	 * Checks the equality of this NumericArrayWritable with the object provided.
	 * The provided object must be of this type, and the values in the arrays must
	 * be equal, position by position.
	 *
	 * TODO: Add a tolerance to the double compare.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof NumericArrayWritable)) {
			return false;
		}

		NumericArrayWritable target = (NumericArrayWritable)o;

		if (longValues != target.longValues) {
			// Not both null or the same array, so we need to look at the individual values.
			if (longValues == null || target.longValues == null || longValues.length != target.longValues.length) {
				return false;
			}
			for ( int i = 0; i < longValues.length; i++ ) {
				if (longValues[i] != target.longValues[i]) {
					return false;
				}
			}
		}

		if (doubleValues != target.doubleValues) {
			// Not both null or the same array, so we need to look at the individual values.
			if (doubleValues == null || target.doubleValues == null
					|| doubleValues.length != target.doubleValues.length) {
				return false;
			}
			for ( int i = 0; i < doubleValues.length; i++ ) {
				if (doubleValues[i] != target.doubleValues[i]) {
					return false;
				}
			}
		}

		return true;
	}
}