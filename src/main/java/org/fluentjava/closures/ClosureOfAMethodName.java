package org.fluentjava.closures;

import static java.util.Arrays.asList;

import java.util.List;

/**
 * Whenever this {@link #call(Object...)} is invoked, it reflectively invokes the
 * {@link #methodName} into the first argument passed, passing the rest of the arguments
 * to the actual method.
 */
public class ClosureOfAMethodName extends Closure {

	protected final String methodName;

	public ClosureOfAMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public Object call(Object... args) throws Exception {
		Object target = first(args);
		List<Object> restList = asList(args).subList(1, args.length);
		return new ReflectiveInvoker(methodName, target, restList).invoke();
	}
}
