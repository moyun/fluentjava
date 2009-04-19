package org.fluentjava.collections;

import java.util.Map;

import org.fluentjava.iterators.Pair;

/**
 * Map that has a fluent interface on methods. All Enumerable methods are also implemented.
 * @param <K>
 * Type of keys.
 * @param <V>
 * Type of Values
 */
public interface FluentMap<K, V> extends Enumerable<Pair<K, V>>, Map<K, V> {

	/**
	 * Fluent put.
	 * @param key
	 * @param value
	 * @return
	 */
	FluentMap<K, V> putAt(K key, V value);

	/**
	 * Fluent putAll.
	 * @param m
	 * a map
	 * @return
	 */
	FluentMap<K, V> insert(Map<? extends K, ? extends V> m);
}