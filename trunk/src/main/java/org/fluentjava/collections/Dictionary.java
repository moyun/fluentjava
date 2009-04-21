package org.fluentjava.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.fluentjava.iterators.AbstractExtendedIterator;
import org.fluentjava.iterators.ExtendedIterator;

/**
 * Standard Implementation of Fluent map.
 * 
 * @param <K>
 * Type of keys.
 * @param <V>
 * Type of Values
 */
public class Dictionary<K, V> extends HashMap<K, V> implements FluentMap<K, V> {

	private static final long serialVersionUID = 7553752893723422794L;

	/*
	 * Constructors
	 */
	public Dictionary() {
		super();
	}

	public Dictionary(Map<? extends K, ? extends V> m) {
		super(m);
	}

	/*
	 * Public Methods
	 */

	public FluentList<V> allValues() {
		return new Sequence<V>(values());
	}

	public FluentSet<K> keys() {
		return new ExtendedSet<K>(keySet());
	}

	public FluentMap<K, V> putAt(K key, V value) {
		super.put(key, value);
		return this;
	}

	public FluentMap<K, V> insert(Map<? extends K, ? extends V> m) {
		super.putAll(m);
		return this;
	}

	public ExtendedIterator<Pair<K, V>> iterator() {
		final Iterator<Entry<K, V>> i = entrySet().iterator();
		return new AbstractExtendedIterator<Pair<K, V>>() {
			public boolean hasNext() {
				return i.hasNext();
			}

			public Pair<K, V> next() {
				return new Pair<K, V>(i.next());
			}
		};
	}

	public boolean allSatisfy(Object closure) throws EnumeratingException {
		return enumerator().allSatisfy(closure);
	}

	public boolean anySatisfy(Object closure) throws EnumeratingException {
		return enumerator().anySatisfy(closure);
	}

	public <T> FluentList<T> collect(Object closure) throws EnumeratingException {
		return enumerator().collect(closure);
	}

	public int count(Object closure) throws EnumeratingException {
		return enumerator().count(closure);
	}

	public Pair<K, V> detect(Object closure) throws EnumeratingException {
		return enumerator().detect(closure);
	}

	public Pair<K, V> detectIfNone(Object closure, Pair<K, V> ifNone) throws EnumeratingException {
		return enumerator().detectIfNone(closure, ifNone);
	}

	public boolean exists(Object closure) throws EnumeratingException {
		return enumerator().exists(closure);
	}

	public void foreach(Object closure) throws EnumeratingException {
		enumerator().foreach(closure);
	}

	public Pair<K, V> inject(Object closure) throws EnumeratingException {
		return enumerator().inject(closure);
	}

	public Pair<K, V> inject(Pair<K, V> initial, Object closure) throws EnumeratingException {
		return enumerator().inject(initial, closure);
	}

	public <T> FluentList<T> map(Object closure) throws EnumeratingException {
		return enumerator().map(closure);
	}

	public boolean noneSatisfy(Object closure) throws EnumeratingException {
		return enumerator().noneSatisfy(closure);
	}

	public Pair<K, V> reduce(Object closure) throws EnumeratingException {
		return enumerator().reduce(closure);
	}

	public Pair<K, V> reduce(Pair<K, V> initial, Object closure) throws EnumeratingException {
		return enumerator().reduce(initial, closure);
	}

	public FluentList<Pair<K, V>> reject(Object closure) throws EnumeratingException {
		return enumerator().reject(closure);
	}

	public FluentList<Pair<K, V>> select(Object closure) throws EnumeratingException {
		return enumerator().select(closure);
	}

	public FluentList<Pair<K, V>> sort() {
		return enumerator().sort();
	}

	public FluentList<Pair<K, V>> sort(Object closure) throws EnumeratingException {
		return enumerator().sort(closure);
	}

	public FluentList<Pair<K, V>> toList() {
		return enumerator().toList();
	}

	public FluentSet<Pair<K, V>> toSet() {
		return enumerator().toSet();
	}

	/*
	 * Other Methods
	 */
	private Enumerator<Pair<K, V>> enumerator() {
		return new Enumerator<Pair<K, V>>(this);
	}

}
