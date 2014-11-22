package com.refactorlabs.cs378.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Writable;

/**
 * Utility class containing useful methods for handling Writables.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WritableUtils {

	// Not instantiable
	private WritableUtils() {}

	/**
	 * Generates a string representation of the Writable values contained in the object.
	 * String representation has commas between the string values of the individual Writables.
	 *
	 * @return String representing the Writable values (comma separated).
	 */
	public static String toString(Writable[] values) {
		String[] stringValues = new String[values.length];

		for ( int i = 0; i < values.length; i++ ) {
			stringValues[i] = (values[i]).toString();
		}

		return StringUtils.join(stringValues, ',');
	}
}
