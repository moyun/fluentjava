package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.fluentjava.iterators.AbstractExtendedIterator;
import org.fluentjava.iterators.ExtendedIterator;

/**
 * Standard Implementation of {@link FluentMap}.
 * 
 * @param <K>
 * Type of keys.
 * @param <V>
 * Type of Values
 */
public class Dictionary<K, V> extends AbstractEnumerable<Pair<K, V>>
		implements
			Cloneable,
			Serializable,
			FluentMap<K, V> {

	private static final long serialVersionUID = 2L;

	protected final HashMap<K, V> delegateMap;

	/*
	 * Constructors
	 */
	public Dictionary() {
		delegateMap = new HashMap<K, V>();
	}

	public Dictionary(Map<? extends K, ? extends V> map) {
		delegateMap = new HashMap<K, V>(map);
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

	public Dictionary<K, V> putAt(K key, V value) {
		put(key, value);
		return this;
	}

	public Dictionary<K, V> insert(Map<? extends K, ? extends V> m) {
		putAll(m);
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
	
	@Override
	public FluentList<V> valuesAt(Iterable<? extends K> keys) {
		FluentList<V> ret = new Sequence<V>();
		for (K key : keys) {
			ret.add(get(key));
		}
		return ret;
	}

	@Override
	public FluentList<V> valuesAt(K... keys) {
		return valuesAt(asList(keys));
	}

	public Object clone() {
		return new Dictionary<K, V>(delegateMap);
	}

	/*
	 * Delegate Methods
	 */
	public void clear() {
		delegateMap.clear();
	}

	public boolean containsKey(Object key) {
		return delegateMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return delegateMap.containsValue(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return delegateMap.entrySet();
	}

	public boolean equals(Object o) {
		return delegateMap.equals(o);
	}

	public V get(Object key) {
		return delegateMap.get(key);
	}

	public int hashCode() {
		return delegateMap.hashCode();
	}

	public boolean isEmpty() {
		return delegateMap.isEmpty();
	}

	public Set<K> keySet() {
		return delegateMap.keySet();
	}

	public V put(K key, V value) {
		return delegateMap.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		delegateMap.putAll(m);
	}

	public V remove(Object key) {
		return delegateMap.remove(key);
	}

	public int size() {
		return delegateMap.size();
	}

	public String toString() {
		return delegateMap.toString();
	}

	public Collection<V> values() {
		return delegateMap.values();
	}



}
