package org.fluentjava;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import org.fluentjava.closures.Closure;
import org.fluentjava.closures.ClosureOfAMethod;
import org.fluentjava.closures.ClosureOfAString;
import org.fluentjava.collections.Dictionary;
import org.fluentjava.collections.ExtendedSet;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.collections.FluentSet;
import org.fluentjava.collections.Pair;
import org.fluentjava.collections.Sequence;

/**
 * Class with no attributes that allows subclasses to easly create collections and
 * closures. A lot of type inference to make things go more fluent.
 */
public abstract class Fluency {
	/**
	 * Creates an empty map.
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	protected <K, V> FluentMap<K, V> map() {
		return new Dictionary<K, V>();
	}

	/**
	 * Creates an empty list.
	 * @param <T>
	 * @return
	 */
	protected <T> FluentList<T> list() {
		return new Sequence<T>();
	}

	/**
	 * Varargs version that creates a fluent list from a list of elements.
	 * @param <T>
	 * @param first
	 * @param list
	 * @return
	 */
	protected <T> FluentList<T> list(T first, T... list) {
		return new Sequence<T>().insert(first).insert(list);
	}

	/**
	 * Creates a fluent list from a iterable.
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	protected <T> FluentList<T> list(Iterable<T> iterable) {
		return new Sequence<T>(iterable);
	}
	
	/**
	 * Creates an empty set.
	 * @param <T>
	 * @return
	 */
	protected <T> FluentSet<T> set() {
		return new ExtendedSet<T>();
	}
	
	
	/**
	 * Varargs version that creates a set from a list of elements.
	 * @param <T>
	 * @param first
	 * @param list
	 * @return
	 */
	protected <T> FluentSet<T> set(T first, T... list) {
		return new ExtendedSet<T>().insert(first).insert(list);
	}

	/**
	 * Creates fluentSet from a iterable.
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	protected <T> FluentSet<T> set(Iterable<T> iterable) {
		return new ExtendedSet<T>(iterable);
	}
	
	/**
	 * Create a simple pair.
	 * @param <F>
	 * @param <S>
	 * @param first
	 * @param second
	 * @return
	 */
	protected <F, S> Pair<F, S> pair(F first, S second) {
		return new Pair<F, S>(first, second);
	}

	/**
	 * Returns a closure of a method on the current class, of the name methodName. 
	 * @param methodName
	 * @return
	 */
	protected Closure my(String methodName) {
		return new ClosureOfAMethod(this, findMethod(methodName));
	}
	
	
	/**
	 * Creates the ClosureOfAString on methodName.
	 * @param methodName
	 * @return
	 */
	protected Closure target(String methodName) {
		return new ClosureOfAString(methodName);
	}

	/*
	 * Private Methods
	 */
	private Method findMethod(String methodName) {
		Method method = findOnDeclared(methodName);
		if (method != null) {
			return method;
		}
		method = findOnInhenrited(methodName);
		if (method != null) {
			return method;
		}
		throw new NoSuchElementException("The method " + methodName + " was not found on " + this);
	}

	private Method findOnInhenrited(String methodName) {
		for (Method method : this.getClass().getMethods()) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	private Method findOnDeclared(String methodName) {
		for (Method method : getClass().getDeclaredMethods()) {
			if (methodName.equals(method.getName())) {
				method.setAccessible(true);
				return method;
			}
		}
		return null;
	}


}
