package org.fluentjava;

import org.fluentjava.closures.Closure;
import org.fluentjava.collections.Dictionary;
import org.fluentjava.collections.ExtendedSet;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.collections.FluentSet;
import org.fluentjava.collections.Pair;

/**
 * Class with no attributes that allows subclasses to easly create collections and
 * closures. A lot of type inference to make things go more fluent.
 * 
 * If you do not with to extend Fluency, FluentUtils with import static can be a good
 * enough replacement.
 * 
 * @see FluentUtils
 */
public abstract class Fluency {
	/**
	 * Creates an empty map.
	 * 
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	protected <K, V> FluentMap<K, V> map() {
		return new Dictionary<K, V>();
	}

	/**
	 * Delegates to {@link FluentUtils#list()}.
	 * 
	 * @param <T>
	 * @return
	 */
	protected <T> FluentList<T> list() {
		return FluentUtils.<T> list();
	}

	/**
	 * Delegates to {@link FluentUtils#list(Object...))}.
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	protected <T> FluentList<T> list(T... args) {
		return FluentUtils.<T> list(args);
	}

	/**
	 * Delegates to {@link FluentUtils#listFromIterable(Iterable)}.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	protected <T> FluentList<T> listFromIterable(Iterable<T> iterable) {
		return FluentUtils.<T> listFromIterable(iterable);
	}

	/**
	 * Delegates to {@link FluentUtils#set()}.
	 * 
	 * @param <T>
	 * @return
	 */
	protected <T> FluentSet<T> set() {
		return new ExtendedSet<T>();
	}

	/**
	 * Delegates to {@link FluentUtils#set(Object...)}.
	 * 
	 * @param <T>
	 * @param first
	 * @param list
	 * @return
	 */
	protected <T> FluentSet<T> set(T... args) {
		return FluentUtils.<T> set(args);
	}

	/**
	 * Delegates to {@link FluentUtils#setFromIterable(Iterable)}.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	protected <T> FluentSet<T> setFromIterable(Iterable<T> iterable) {
		return FluentUtils.<T> setFromIterable(iterable);
	}

	/**
	 * Delegates to {@link FluentUtils#pair(Object, Object)}.
	 * 
	 * @param <F>
	 * @param <S>
	 * @param first
	 * @param second
	 * @return
	 */
	protected <F, S> Pair<F, S> pair(F first, S second) {
		return FluentUtils.pair(first, second);
	}

	/**
	 * Delegates to {@link FluentUtils#my(Object, String)}, passing this as target.
	 * 
	 * @return
	 */
	protected Closure my(String methodName) {
		return FluentUtils.my(this, methodName);
	}
	
	/**
	 * Delegates to {@link FluentUtils#range(int)}, passing range as argument.
	 * 
	 * @return
	 */
	protected FluentList<Integer> range(int range) {
		return FluentUtils.range(range);
	}
	
	/**
	 * Delegates to {@link FluentUtils#range(int, int)}, passing start and stop as argument.
	 * 
	 * @return
	 */
	protected FluentList<Integer> range(int start, int range) {
		return FluentUtils.range(start, range);
	}

}
