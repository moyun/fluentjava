package org.fluentjava.closures;

import static java.util.Arrays.asList;

import java.lang.reflect.Method;

/**
 * Wraps around a public method name. The first argument of call must always be a class
 * such that method can be invoked on it.
 */
public class ClosureOfAString extends Closure {

	protected final String methodName;

	public ClosureOfAString(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public Object call(Object... args) throws Exception {
		Object first = first(args);
		Object[] rest = asList(args).subList(1, args.length).toArray();
		Method method = findmethod(first);
		return method.invoke(first, rest);
	}

	private Method findmethod(Object target) {
		for (Method method : target.getClass().getMethods()) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}
		throw new IllegalArgumentException("Method of name " + methodName + " could not be found on "
				+ target);
	}
}
