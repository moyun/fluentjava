package org.fluentjava.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.fluentjava.iterators.AbstractExtendedIterator;
import org.fluentjava.iterators.ExtendedIterable;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.Pair;

/**
 * Fluent map.
 * 
 * @param <K>
 * Type of keys.
 * @param <V>
 * Type of Values
 */
public class Dictionary<K, V> extends HashMap<K, V> implements ExtendedIterable<Pair<K, V>> {

	private static final long serialVersionUID = 7553752893723422794L;

	public Dictionary<K, V> putAt(K key, V value) {
		super.put(key, value);
		return this;
	}

	public Dictionary<K, V> insert(Map<? extends K, ? extends V> m) {
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

	public FluentList<Pair<K, V>> toArray() {
		return new Sequence<Pair<K, V>>(iterator());
	}

}
