package com.refactorlabs.cs378.sessions;

import java.util.Map;

/**
 * Interface for session field handlers.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public interface SessionFieldHandler {

	public void handle(Session.Builder builder, Map<String, String> fieldValueMap);
}
