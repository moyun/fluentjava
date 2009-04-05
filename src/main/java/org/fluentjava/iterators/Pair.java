package org.fluentjava.iterators;

import java.util.Map.Entry;

/**
 * Simple Pair of types F and S. Similar to Map.Entry, but does not have the same
 * semantics: it is not necesseryly a key and a value.
 * 
 * @param <F>
 * first type
 * @param <S>
 * second type
 */
public class Pair<F, S> implements Entry<F, S> {
	public F first;
	public S second;

	public static <F, S> Pair<F, S> pair(F first, S second) {
		return new Pair<F, S>(first, second);
	}

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
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		}
		else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		}
		else if (!second.equals(other.second))
			return false;
		return true;
	}

}
