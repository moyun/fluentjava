package org.fluentjava.closures;

/**
 * Approximation of the concept of closure on Java. It is not really a closure, as it does
 * not close around the environment.
 */
public abstract class Closure {

	/**
	 * Generic Call.
	 * 
	 * @param args
	 * Function Arguments
	 * 
	 * @return The result of Call
	 * 
	 * @throws Exception
	 */
	public abstract Object call(Object... args) throws Exception;

	/**
	 * Convenience method to call and cast result.
	 * 
	 * @param <T>
	 * Type to cast
	 * 
	 * @param args
	 * Function Arguments
	 * 
	 * @return The result of Call
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T invoke(Object... args) throws Exception {
		return (T) call(args);
	}
}
