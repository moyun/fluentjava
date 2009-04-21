package org.fluentjava.reflection;

/**
 * Checked exception that encapsulates problems encountered by Mirror class. For unchecked
 * exceptions, see RuntimeReflectionException
 * 
 * @see Mirror, RuntimeReflectionException
 */
public class ReflectionException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReflectionException() {
		super();
	}

	public ReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectionException(String message) {
		super(message);
	}

	public ReflectionException(Throwable cause) {
		super(cause);
	}
	
	

}
