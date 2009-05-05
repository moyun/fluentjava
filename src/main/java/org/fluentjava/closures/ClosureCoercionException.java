package org.fluentjava.closures;

/**
 * Indicates an Object could not be converted to a {@link Closure}.
 */
public class ClosureCoercionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClosureCoercionException() {
		super();
	}

	public ClosureCoercionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClosureCoercionException(String message) {
		super(message);
	}

	public ClosureCoercionException(Throwable cause) {
		super(cause);
	}
}
