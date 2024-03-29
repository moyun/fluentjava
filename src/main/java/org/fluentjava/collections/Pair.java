package org.fluentjava.collections;

import java.util.Map.Entry;

/**
 * Simple Pair of types F and S. Similar to {@link Entry}, but does not have the same
 * semantics: it is not necessarily a key and a value. But, for backwards compatibility,
 * it implements Entry.
 * 
 * @param <F>
 * first type
 * @param <S>
 * second type
 */
public class Pair<F, S> implements Entry<F, S> {
	public F first;
	public S second;

	public Pair(Entry<F, S> entry) {
		this(entry.getKey(), entry.getValue());
	}

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F getKey() {
		return first;
	}

	public S getValue() {
		return second;
	}

	public S setValue(S value) {
		S ret = second;
		this.second = value;
		return ret;
	}

	@Override
	public String toString() {
		return String.format("Pair: (%s, %s)", first, second);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (first != null) {
			result = prime * result + first.hashCode();
		}
		if (second != null) {
			result = prime * result + second.hashCode();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Entry)) {
			return false;
		}
		final Entry other = (Entry) obj;
		if (getKey() == null) {
			if (other.getKey() != null) {
				return false;
			}
		}
		else if (!getKey().equals(other.getKey())) {
			return false;
		}
		if (getValue() == null) {
			if (other.getValue() != null) {
				return false;
			}
		}
		else if (!getValue().equals(other.getValue())) {
			return false;
		}
		return true;
	}

}
