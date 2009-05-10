package org.fluentjava.closures;

import static java.util.Arrays.asList;

/**
 * Like {@link ClosureOfAMethodName}, but on a specific target, and calls protected and
 * private methods as well.
 */
public class PriviligedClosureOfAMethodName extends Closure {

	protected final Object target;
	protected final String methodName;

	public PriviligedClosureOfAMethodName(Object target, String methodName) {
		this.target = target;
		this.methodName = methodName;
	}

	@Override
	public Object call(Object... args) throws Exception {
		return new ReflectiveInvoker(methodName, target, asList(args)).priviligedInvoke();
	}

}
