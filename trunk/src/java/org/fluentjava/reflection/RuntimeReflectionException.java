package org.fluentjava.reflection;

/**
 * Unchecked exception that encapsulates problems encountered by Mirror class.
 * 
 * @see Mirror
 */
public class RuntimeReflectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuntimeReflectionException() {
		super();
	}

	public RuntimeReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeReflectionException(String message) {
		super(message);
	}

	public RuntimeReflectionException(Throwable cause) {
		super(cause);
	}
	
	
	
}
