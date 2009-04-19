package org.fluentjava.closures;

import java.lang.reflect.Method;

/**
 * Wraps around an object instance and a method of its class. 
 */
public class ClosureOfAMethod extends Closure {

	private Method method;
	private Object target;
	
	public ClosureOfAMethod(Object target, Method method) {
		this.target = target;
		this.method = method;
	}

	@Override
	public Object call(Object... args) throws Exception {
		return method.invoke(target, args);
	}

}
