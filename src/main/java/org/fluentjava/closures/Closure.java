package org.fluentjava.closures;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

import org.fluentjava.FluentUtils;

/**
 * Approximation of the concept of closure on Java, abstracting a function definition. It
 * is not really a closure, as it does not close around the environment.
 * 
 * Instead of subclassing this class, try using {@link ClosureCoercion} static methods.
 */
public abstract class Closure {

	/**
	 * Generic Call.
	 * 
	 * @param args
	 * Function Arguments.
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

	/**
	 * Convenient method that casts the args from call, invoke.
	 * 
	 * @param <T>
	 * Desired Type. Usually inferred.
	 * @param i
	 * index of args.
	 * @param args
	 * The args from call/invoke.
	 * @return args[i] Casted
	 */
	@SuppressWarnings("unchecked")
	public <T> T arg(int i, Object... args) {
		return (T) args[i];
	}

	/**
	 * Utility method that returns arg(0, args).
	 * 
	 * @param <T>
	 * Desired Type. Usually inferred.
	 * @param args
	 * The args from call/invoke.
	 * @return args[0] Casted
	 */
	public <T> T first(Object... args) {
		return this.<T>arg(0, args);
	}

	/**
	 * Utility method that returns arg(1, args).
	 * 
	 * @param <T>
	 * Desired Type. Usually inferred.
	 * @param args
	 * The args from call/invoke.
	 * @return args[1] Casted
	 */
	public <T> T second(Object... args) {
		return this.<T>arg(1, args);
	}

	/**
	 * Adapts self to an Interface. <b>IMPORTANT:</b> All methods invoked on the interface
	 * will trigger call, so be careful when adapting interfaces with several methods.
	 * 
	 * @param <T>
	 * Inferred type.
	 * @param clazz
	 * Interface to be adapted
	 * @return A proxy to self.
	 */
	public <T> T toInteface(Class<?> clazz) {
		InvocationHandler handler = new InvocationHandlerAdapter(this, clazz);
		return FluentUtils.<T>as(Proxy.newProxyInstance(clazz.getClassLoader(),
				new Class[] {clazz}, handler));
	}

	/**
	 * Alias to {@link #toInteface(Class)}.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public <T> T as(Class<?> clazz) {
		return this.<T>toInteface(clazz);
	}

	/**
	 * Adapts self to a Callable.
	 * 
	 * @return
	 * 
	 * @see Callable
	 */
	public <T> Callable<T> asCallable() {
		return toInteface(Callable.class);
	}

	/**
	 * Adapts self to a Runnable.
	 * 
	 * @return
	 * 
	 * @see Runnable
	 */
	public Runnable asRunnable() {
		return toInteface(Runnable.class);
	}

	/**
	 * Adapts self to a Thread.
	 * 
	 * @return
	 * 
	 * @see Thread
	 */
	public Thread asThread() {
		return new Thread(asRunnable());
	}

}
