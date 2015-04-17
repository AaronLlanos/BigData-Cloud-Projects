package com.refactorlabs.cs378.sessions;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Utility class for processing logs.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class LogUtils {

	public static final String IMPRESSION_ENTRY = "I";
	public static final String LEAD_ENTRY = "L";
	public static final String LOG_ENTRY_TYPE = "log entry type";

	private static final String EMPTY_STRING = "";

	// Not instantiable
	private LogUtils() {}

	public static Map<String, String> parse(String logEntry) {
		Map<String, String> keyValueMap = Maps.newHashMapWithExpectedSize(50);

		// Log file starts with a string followed by a tab.
		// This first string indicates the log entry type.
		String[] fields = logEntry.split("\t", 2);
		if (fields.length > 0) {
			keyValueMap.put(LOG_ENTRY_TYPE, fields[0]);
		}

		// The remainder of the log entry is parameter/value pairs (parameter and value
		// are separated by a colon), and parameter/value pairs are separated by "|\t|".
		if (fields.length > 1) {
			fields = fields[1].split("\\|\t\\|");

			for ( String field : fields ) {
				String[] pvPair = field.split(":", 2);

				if (pvPair.length == 2) {
					keyValueMap.put(pvPair[0], pvPair[1]);
				} else if (pvPair.length == 1) {
					keyValueMap.put(pvPair[0], EMPTY_STRING);
				}
			}
		}

		return keyValueMap;
	}

	/**
	 * Given a string value, returns the ActionName value that has name.
	 */
	public static ActionName getActionName(String value) {
		for ( ActionName name : ActionName.values() ) {
			if (name.name().equalsIgnoreCase(value)) {
				return name;
			}
		}
		return ActionName.UNKNOWN;
	}

}
