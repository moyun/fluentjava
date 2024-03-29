package org.fluentjava.collections;

import java.util.Map;
import java.util.Set;

/**
 * {@link Map} that has a fluent interface on methods. All {@link Enumerable} methods are
 * also implemented. Also, return FluentSets where Map would return {@link Set}.
 * 
 * @param <K>
 * Type of keys.
 * @param <V>
 * Type of Values
 */
public interface FluentMap<K, V> extends Enumerable<Pair<K, V>>, Map<K, V> {

	/**
	 * Fluent put.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	FluentMap<K, V> putAt(K key, V value);

	/**
	 * Fluent putAll.
	 * 
	 * @param m
	 * a map
	 * @return
	 */
	FluentMap<K, V> insert(Map<? extends K, ? extends V> m);

	/**
	 * Alias to putAt(entry.getKey(), entry.getValue()).
	 * 
	 * @param entry
	 * @return
	 */
	FluentMap<K, V> insert(Entry<? extends K, ? extends V> entry);

	/**
	 * Like keySet(), but returns a copy of the set, which is safe to iterate. Also
	 * returns a fluent version of set.
	 * 
	 * @return
	 */
	FluentSet<K> keys();

	/**
	 * Like values(), but returns a copy of the values, which is safe to iterate. Also
	 * returns a fluent version instead of a simple Collection.
	 * 
	 * @return
	 */
	FluentList<V> allValues();

	/**
	 * Return a list with the values from all the keys. nulls will appear where the map
	 * does not find the keys.
	 * 
	 * @param keys
	 * @return
	 */
	FluentList<V> valuesAt(Iterable<? extends K> keys);

	/**
	 * Varargs version of {@link #valuesAt(Iterable)}.
	 * @param keys
	 * @return
	 */
	FluentList<V> valuesAt(K... keys);
	
	FluentSet<Entry<K, V>> entrySet();
	
	FluentSet<K> keySet();
}