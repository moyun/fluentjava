package org.fluentjava.collections;

/**
 * Exception caused when iterating over a collection through the extended collection
 * methods, such as find, exists, collect.
 */
public class EnumeratingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EnumeratingException() {
		super();
	}

	public EnumeratingException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnumeratingException(String message) {
		super(message);
	}

	public EnumeratingException(Throwable cause) {
		super(cause);
	}

}
