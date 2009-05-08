package org.fluentjava;

import java.util.Map.Entry;

import org.fluentjava.closures.Closure;
import org.fluentjava.collections.Dictionary;
import org.fluentjava.collections.ExtendedSet;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.collections.FluentSet;
import org.fluentjava.collections.Pair;
import org.fluentjava.iterators.ExtendedIterable;

/**
 * Class with no attributes that allows subclasses to easily create collections and
 * closures, without import static. A lot of type inference to make things go more fluent.
 * 
 * If you do not with to extend Fluency, {@link FluentUtils} with import static can be a
 * good enough replacement.
 * 
 * @see FluentUtils
 */
public abstract class Fluency {
	public static void main(String[] args) {
	}

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
		return FluentUtils.<T>list();
	}

	/**
	 * Delegates to {@link FluentUtils#list(Object...))}.
	 * 
	 * @param <T>
	 * @param args
	 * @return
	 */
	protected <T> FluentList<T> list(T... args) {
		return FluentUtils.<T>list(args);
	}

	/**
	 * Delegates to {@link FluentUtils#listFromIterable(Iterable)}.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	protected <T> FluentList<T> listFromIterable(Iterable<? extends T> iterable) {
		return FluentUtils.<T>listFromIterable(iterable);
	}

	/**
	 * Delegates to {@link FluentUtils#alist(Object...)}.
	 * 
	 * @param args
	 * @return
	 */
	public static FluentList<Object> alist(Object... args) {
		return FluentUtils.alist(args);
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
		return FluentUtils.<T>set(args);
	}

	/**
	 * Delegates to {@link FluentUtils#aSet(Object...)}.
	 * 
	 * @param args
	 * @return
	 */
	protected FluentSet<Object> aSet(Object... args) {
		return FluentUtils.aSet(args);
	}

	/**
	 * Delegates to {@link FluentUtils#setFromIterable(Iterable)}.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	protected <T> FluentSet<T> setFromIterable(Iterable<? extends T> iterable) {
		return FluentUtils.<T>setFromIterable(iterable);
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
	 * Delegates to {@link FluentUtils#call(Object, String)}.
	 * 
	 * @return
	 */
	protected Closure call(Object target, String methodName) {
		return FluentUtils.call(target, methodName);
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
	 * Delegates to {@link FluentUtils#range(int, int)}, passing start and stop as
	 * argument.
	 * 
	 * @return
	 */
	protected FluentList<Integer> range(int start, int stop) {
		return FluentUtils.range(start, stop);
	}

	/**
	 * Delegates to {@link FluentUtils#irange(int)}, passing range as argument.
	 * 
	 * @return
	 */
	protected ExtendedIterable<Integer> irange(int range) {
		return FluentUtils.irange(range);
	}

	/**
	 * Delegates to {@link FluentUtils#range(int, int)}, passing start and stop as
	 * argument.
	 * 
	 * @return
	 */
	protected ExtendedIterable<Integer> irange(int start, int stop) {
		return FluentUtils.irange(start, stop);
	}

	/**
	 * Delegates to {@link FluentUtils#as(Object)}.
	 * 
	 * @param <T>
	 * @param o
	 * @return
	 */
	protected <T> T as(Object o) {
		return FluentUtils.<T>as(o);
	}

	/**
	 * Delegates to {@link FluentUtils#cast(Object)}.
	 * 
	 * @param <T>
	 * @param o
	 * @return
	 */
	protected <T> T cast(Object o) {
		return FluentUtils.<T>cast(o);
	}

	/**
	 * Delegates to {@link FluentUtils#map(Entry...)}.
	 * 
	 * @param <K>
	 * @param <V>
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K, V> FluentMap<K, V> map(Entry... args) {
		return FluentUtils.<K, V>map(args);
	}
}
