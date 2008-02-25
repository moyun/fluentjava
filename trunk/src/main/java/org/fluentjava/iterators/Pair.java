package org.fluentjava.iterators;

/**
 * Simple Pair of types F and S. Similar to Map.Entry, but does not have the same
 * semantics: it is not necesseryly a key and a value.
 * 
 * @param <F>
 * first type
 * @param <S>
 * second type
 */
public class Pair<F, S> {
	public F first;
	public S second;
	
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}
}
