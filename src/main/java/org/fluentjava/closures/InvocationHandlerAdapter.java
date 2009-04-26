/**
 * 
 */
package org.fluentjava.closures;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Adapts a closure to a Invocation Handler. Essentially it ignores the proxy and the
 * method called, and just forwards args to Closure.call. Only forwards method of the
 * adapted interface.
 */
public class InvocationHandlerAdapter implements InvocationHandler {

	protected final Closure closure;
	protected final Class<?> adpatedInteface;

	protected InvocationHandlerAdapter(Closure closure, Class<?> adpatedInteface) {
		this.closure = closure;
		this.adpatedInteface = adpatedInteface;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (adpatedInteface == method.getDeclaringClass()) {
			if (args == null) {
				return this.closure.call();
			}
			return this.closure.call(args);
		}
		return method.invoke(closure, args);
	}
}