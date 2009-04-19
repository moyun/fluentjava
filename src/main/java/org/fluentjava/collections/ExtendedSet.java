package org.fluentjava.collections;

import java.lang.reflect.Array;
import java.util.HashSet;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Standard implementation of FluentSet.
 * @param <E>
 * Type of elements
 */
public class ExtendedSet<E> extends HashSet<E> implements FluentSet<E> {
	private static final long serialVersionUID = 1L;

	/*
	 * Factory Methods
	 */
	public static <E> ExtendedSet<E> set(E...element) {
		return new ExtendedSet<E>(element);
	}
	
	public static <E> ExtendedSet<E> set(Iterable<E> iterable) {
		return new ExtendedSet<E>(iterable);
	}

	/*
	 * Constructors
	 */
	public ExtendedSet() { }
	
	public ExtendedSet(E...elements) {
		insert(elements);
	}
	
	public ExtendedSet(Iterable<E> element) {
		insert(element);
	}

	/*
	 * Public Methods
	 */
	
	@Override
	public ExtendedIterator<E> iterator() {
	return new ExtendedIteratorAdapter<E>(super.iterator());
	}

	public FluentSet<E> insert(E element) {
		add(element);
		return this;
	}
	
	public FluentSet<E> insert(E... set) {
		for (E e : set) {
			add(e);
		}
		return this;
	}
	
	public FluentSet<E> insert(Iterable<E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}

	public FluentSet<E> delete(E...elements) {
		removeAll(set(elements));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return 	toArray((T[]) Array.newInstance(clazz, size()));
	}
	
	
}
