package org.fluentjava;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import org.fluentjava.closures.ClosureOfAMethod;
import org.fluentjava.collections.ExtendedSet;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentSet;
import org.fluentjava.collections.Pair;
import org.fluentjava.collections.Sequence;

/**
 * Class with static methods that serves as facade to serveral objects of the api. If you
 * wish a more fluent way to use the methods on this class, mainly
 * {@link #my(Object, String)} without the need to keep on passing this, consider extending {@link Fluency}.
 * 
 * @see Fluency
 */
public class FluentUtils {
	/*
	 * Factory Methods
	 */
	/**
	 * Creates an empty FluentList of type T.
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> FluentList<T> list() {
		return new Sequence<T>();
	}

	/**
	 * Create a FluentList with elements args.
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> FluentList<T> list(T... args) {
		return new Sequence<T>(args);
	}

	/**
	 * Creates a FluentList the with the iterable elements.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> FluentList<T> listFromIterable(Iterable<T> iterable) {
		return new Sequence<T>(iterable);
	}

	/**
	 * Creates an empty FluentSet of type T.
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> FluentSet<T> set() {
		return new ExtendedSet<T>();
	}

	/**
	 * Create a FluentSet with elements args.
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> FluentSet<T> set(T... args) {
		return new ExtendedSet<T>(args);
	}

	/**
	 * Creates a FluentSet the with the iterable elements.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> FluentSet<T> setFromIterable(Iterable<T> iterable) {
		return new ExtendedSet<T>(iterable);
	}

	/**
	 * Creates a simple pair.
	 * 
	 * @param <F>
	 * @param <S>
	 * @param first
	 * @param second
	 * @return
	 */
	public static <F, S> Pair<F, S> pair(F first, S second) {
		return new Pair<F, S>(first, second);
	}

	/**
	 * Returns a closure of a method on the target current class, of the name methodName.
	 * 
	 * @param target
	 * @param methodName
	 * 
	 * @return
	 */
	public static ClosureOfAMethod my(Object target, String methodName) {
		return new ClosureOfAMethod(target, findMethod(target, methodName));
	}

	/*
	 * Private Methods
	 */
	private static Method findMethod(Object target, String methodName) {
		Method method = findOnDeclared(target, methodName);
		if (method != null) {
			return method;
		}
		method = findOnInhenrited(target, methodName);
		if (method != null) {
			return method;
		}
		throw new NoSuchElementException("The method " + methodName + " was not found on " + target);
	}

	private static Method findOnInhenrited(Object target, String methodName) {
		for (Method method : target.getClass().getMethods()) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	private static Method findOnDeclared(Object target, String methodName) {
		for (Method method : target.getClass().getDeclaredMethods()) {
			if (methodName.equals(method.getName())) {
				method.setAccessible(true);
				return method;
			}
		}
		return null;
	}

	public static FluentList<Integer> range(int range) {
		FluentList<Integer> sequence = new Sequence<Integer>();
		for (int i = 0; i < range; i++) {
			sequence.add(i);
		}
		return sequence;
	}
	
	public static FluentList<Integer> range(int start, int stop) {
		FluentList<Integer> list = new Sequence<Integer>();
		for (int i = start; i < stop; i++) {
			list.add(new Integer(i));
		}
		return list;
	}

}
