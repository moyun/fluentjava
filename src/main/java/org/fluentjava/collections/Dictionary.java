package org.fluentjava.collections;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
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
public class Dictionary<K, V> extends ForwardingFluentMap<K, V> implements Externalizable {

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

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		Map<K, V> inobj = (Map<K, V>) in.readObject();
		delegateMap.putAll(inobj);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(delegateMap);
	}
}
