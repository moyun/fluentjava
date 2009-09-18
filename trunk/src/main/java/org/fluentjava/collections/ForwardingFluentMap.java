package org.fluentjava.collections;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.fromSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.fluentjava.iterators.AbstractExtendedIterator;
import org.fluentjava.iterators.ExtendedIterator;

/**
 * Wraps around any {@link Map} implementation, and delegates all set methods to this
 * {@link #delegateMap} .
 * 
 * @param <K>
 * @param <V>
 */
public class ForwardingFluentMap<K, V> extends AbstractEnumerable<Pair<K, V>>
		implements
			FluentMap<K, V>, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Variables
	 */
	protected final Map<K, V> delegateMap;

	/*
	 * Constructors
	 */
	public ForwardingFluentMap(Map<K, V> delegateMap) {
		this.delegateMap = delegateMap;
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
		put(key, value);
		return this;
	}

	public FluentMap<K, V> insert(Map<? extends K, ? extends V> m) {
		putAll(m);
		return this;
	}

	@Override
	public ExtendedIterator<Pair<K, V>> iterator() {
		return new EntrySetIterator<K, V>(delegateMap.entrySet().iterator());
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

	@Override
	public FluentMap<K, V> insert(java.util.Map.Entry<? extends K, ? extends V> entry) {
		return putAt(entry.getKey(), entry.getValue());
	}

	@Override
	public Object clone() {
		return toMap();
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

	public FluentSet<Entry<K, V>> entrySet() {
		return fromSet(delegateMap.entrySet());
	}

	@Override
	public boolean equals(Object o) {
		return delegateMap.equals(o);
	}

	public V get(Object key) {
		return delegateMap.get(key);
	}

	@Override
	public int hashCode() {
		return delegateMap.hashCode();
	}

	public boolean isEmpty() {
		return delegateMap.isEmpty();
	}

	public FluentSet<K> keySet() {
		return fromSet(delegateMap.keySet());
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

	@Override
	public String toString() {
		return delegateMap.toString();
	}

	public Collection<V> values() {
		return delegateMap.values();
	}

	/**
	 * {@link ForwardingFluentMap} iterator.
	 * @param <K>
	 * @param <V>
	 */
	private static final class EntrySetIterator<K, V> extends
			AbstractExtendedIterator<Pair<K, V>> {
		private final Iterator<Entry<K, V>> iterator;

		public EntrySetIterator(Iterator<Entry<K, V>> i) {
			this.iterator = i;
		}

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public Pair<K, V> next() {
			return new Pair<K, V>(iterator.next());
		}
	}
}
