package org.fluentjava;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import org.fluentjava.closures.ClosureOfAMethod;
import org.fluentjava.collections.Dictionary;
import org.fluentjava.collections.ExtendedSet;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.collections.FluentSet;
import org.fluentjava.collections.Pair;
import org.fluentjava.collections.Sequence;
import org.fluentjava.iterators.AbstractExtendedIterator;
import org.fluentjava.iterators.ExtendedIterable;
import org.fluentjava.iterators.ExtendedIterator;

/**
 * Class with static methods that serves as facade to several objects of the API. If you
 * wish a more fluent way to use the methods on this class, mainly
 * {@link #my(Object, String)} without the need to keep on passing this, consider
 * extending {@link Fluency}. Otherwise, look on <a
 * href=http://code.google.com/p/fluentjava/wiki/Faq>this</a> page on
 * tips to making <code>import statics</code> more easy to use.
 * 
 * @see Fluency
 */
public class FluentUtils {
	/*
	 * Factory Methods
	 */
	/**
	 * Creates an empty {@link FluentList} of type T.
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> FluentList<T> list() {
		return new Sequence<T>();
	}

	/**
	 * Create a {@link FluentList} with elements args.
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> FluentList<T> list(T... args) {
		return new Sequence<T>(args);
	}

	/**
	 * Creates a {@link FluentList} the with the iterable elements.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> FluentList<T> listFromIterable(Iterable<? extends T> iterable) {
		return new Sequence<T>(iterable);
	}

	/**
	 * Creates a fluent list of objects.
	 * 
	 * @param args
	 * @return
	 */
	public static FluentList<Object> alist(Object... args) {
		return new Sequence<Object>(args);
	}

	/**
	 * Creates an empty {@link FluentSet} of type T.
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> FluentSet<T> set() {
		return new ExtendedSet<T>();
	}

	/**
	 * Create a {@link FluentSet} with elements args.
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	public static <T> FluentSet<T> set(T... args) {
		return new ExtendedSet<T>(args);
	}

	/**
	 * Creates a {@link FluentSet} of objects.
	 * 
	 * @param args
	 * @return
	 */
	public static FluentSet<Object> aSet(Object... args) {
		return new ExtendedSet<Object>(args);
	}

	/**
	 * Creates a {@link FluentSet} the with the iterable elements.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> FluentSet<T> setFromIterable(Iterable<? extends T> iterable) {
		return new ExtendedSet<T>(iterable);
	}

	/**
	 * Creates a simple {@link Pair}.
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
	 * Creates a fluent map from entries (such as {@link Pair}).
	 * 
	 * @param args
	 * @return
	 * 
	 * @see #pair(Object, Object).
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> FluentMap<K, V> map(Entry... args) {
		FluentMap<K, V> ret = new Dictionary<K, V>();
		for (Entry<K, V> entry : args) {
			ret.put(entry.getKey(), entry.getValue());
		}
		return ret;
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

	/**
	 * Alias to {@link #my(Object, String)}.
	 * 
	 * @param target
	 * @param methodName
	 * 
	 * @return
	 */
	public static ClosureOfAMethod call(Object target, String methodName) {
		return new ClosureOfAMethod(target, findMethod(target, methodName));
	}

	/**
	 * <pre>
	 * Calls {@link FluentUtils#range(int, int)} passing 0 as start.
	 * </pre>
	 * 
	 * @see {@link FluentUtils#range(int, int)}
	 * @param stop
	 * @return
	 */
	public static FluentList<Integer> range(int stop) {
		return range(0, stop);
	}

	/**
	 * This method creates a {@link FluentList} of a sequence of Integer starting from
	 * start (including) and ends at stop (excluding).
	 * 
	 * Examples:
	 * 
	 * <pre>
	 * range(0,10) creates [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
	 * range(5,10) creates [5, 6, 7, 8, 9]
	 * </pre>
	 * 
	 * @param start
	 * start of the sequence (including)
	 * @param stop
	 * stop of a sequence (excluding)
	 * 
	 * @return [start,stop)
	 * 
	 */
	public static FluentList<Integer> range(int start, int stop) {
		return new Sequence<Integer>(irange(start, stop));
	}

	/**
	 * <pre>
	 * Calls {@link FluentUtils#irange(int, int)} passing 0 as start.
	 * </pre>
	 * 
	 * @see {@link FluentUtils#irange(int, int)}
	 * @param stop
	 * @return
	 */
	public static ExtendedIterable<Integer> irange(int stop) {
		return irange(0, stop);
	}

	/**
	 * This method creates a {@link ExtendedIterator} of a sequence of Integer starting
	 * from start (including) and ends at stop (excluding).
	 * 
	 * Examples:
	 * 
	 * <pre>
	 * range(0,10) creates [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
	 * range(5,10) creates [5, 6, 7, 8, 9]
	 * </pre>
	 * 
	 * @param start
	 * start of the sequence (including)
	 * @param stop
	 * stop of a sequence (excluding)
	 * 
	 * @return [start,stop)
	 * 
	 */
	public static ExtendedIterable<Integer> irange(int start, int stop) {
		return new IRangeFactory(start, stop);
	}

	/**
	 * Fluent Cast any object to a type T. Natural type inference.
	 * 
	 * @param <T>
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object o) {
		return (T) o;
	}

	/**
	 * Alias to {@link #cast(Object)}.
	 * 
	 * @param <T>
	 * @param o
	 * @return
	 */
	public static <T> T as(Object o) {
		return FluentUtils.<T>cast(o);
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

	/**
	 * Inner class that makes iranges.
	 */
	private static class IRangeExtendedIterator extends AbstractExtendedIterator<Integer> {
		private final int stop;
		private int cur;

		public IRangeExtendedIterator(int start, int stop) {
			this.cur = start;
			this.stop = stop;
		}

		public boolean hasNext() {
			return cur < stop;
		}

		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			int ret = cur;
			cur++;
			return ret;
		}
	}

	/**
	 * Create IRangeExtendedIterators.
	 */
	private static class IRangeFactory implements ExtendedIterable<Integer> {
		private int stop;
		private int start;

		public IRangeFactory(int start, int stop) {
			super();
			this.stop = stop;
			this.start = start;
		}

		public ExtendedIterator<Integer> iterator() {
			return new IRangeExtendedIterator(start, stop);
		}
	}

	private FluentUtils() {
	}

}
