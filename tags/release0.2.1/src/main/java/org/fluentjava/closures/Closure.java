package org.fluentjava.closures;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

/**
 * Approximation of the concept of closure on Java. It is not really a closure, as it does
 * not close around the environment.
 */
public abstract class Closure {

	/**
	 * Generic Call.
	 * 
	 * @param args
	 * Function Arguments
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
	 * Desired Type. Usually infered.
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
	 * Desired Type. Usually infered.
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
	 * Desired Type. Usually infered.
	 * @param args
	 * The args from call/invoke.
	 * @return args[1] Casted
	 */
	public <T> T second(Object... args) {
		return this.<T>arg(1, args);
	}

	/**
	 * Adapts self to a Interface. <b>IMPORTANT:</b> All methods invoked on the interface will trigger call,
	 * so be carefull when adapting interfaces with several methods.
	 * 
	 * @param <T>
	 * Infered type.
	 * @param clazz
	 * Inteface to be adapted
	 * @return
	 * A proxy to self.
	 */
	@SuppressWarnings("unchecked")
	public <T> T toInteface(Class<?> clazz) {
		InvocationHandler handler = new InvocationHandlerAdapter(this, clazz);
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, handler);
	}

	/**
	 * Adapts self to a Callable. <b>IMPORTANT:</b> this methods will trigger call,
	 * so be carefull.
	 * 
	 * @return
	 * A {@link Callable} of self.
	 */
	public <T> Callable<T> asCallable() {
		return toInteface(Callable.class);
	}

	/**
	 * Adapts self to a Runnable. <b>IMPORTANT:</b> this methods will trigger call,
	 * so be carefull.
	 * 
	 * @return
	 * A {@link Runnable} of self.
	 */
	public Runnable asRunnable() {
		return toInteface(Runnable.class);
	}
	
	/**
	 * Adapts self to a Runnable. <b>IMPORTANT:</b> this methods will trigger call,
	 * so be Thread.
	 * 
	 * @return
	 * A {@link Runnable} of self.
	 */
	public Thread asThread() { 
		return new Thread(asRunnable());
	}
	
}
