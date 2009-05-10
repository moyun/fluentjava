package org.fluentjava.collections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Standard Implementation of {@link FluentMap}.
 * 
 * @param <K>
 * Type of keys.
 * @param <V>
 * Type of Values
 */
public class Dictionary<K, V> extends ForwardingFluentMap<K, V>
		implements
			Cloneable,
			Serializable,
			FluentMap<K, V> {

	private static final long serialVersionUID = 2L;

	/*
	 * Constructors
	 */
	public Dictionary() {
		super(new HashMap<K, V>());
	}

	public Dictionary(Map<? extends K, ? extends V> map) {
		super(new HashMap<K, V>(map));
	}
}
