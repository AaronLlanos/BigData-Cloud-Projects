package com.refactorlabs.cs378.sessions;

import java.util.Map;

/**
 * Interface for impression field handlers.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public interface ImpressionFieldHandler {

	public void handle(Impression.Builder builder, Map<String, String> fieldValueMap);
}
