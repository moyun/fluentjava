package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

public class Sequence<E> extends ArrayList<E> implements FluentList<E> {
	private static final long serialVersionUID = 1L;
	

	/*
	 * Factory Methods
	 */
	public static <T> FluentList<T> list(T... list) {
		return new Sequence<T>(list);
	}
	
	public static <T> FluentList<T> list(Iterable<T> iterable) {
		return new Sequence<T>(iterable);
	}
	
	/*
	 * Constructors
	 */
	public Sequence() {
	}

	public Sequence(E... list) {
		insert(list);
	}

	public Sequence(Iterable<E> iterable) {
		insert(iterable);
	}

	/*
	 * Public Methods
	 */


	@Override
	public ExtendedIterator<E> iterator() {
		return new ExtendedIteratorAdapter<E>(super.iterator());
	}

	public FluentList<E> insert(E... list) {
		return insert(asList(list));
	}

	public FluentList<E> insert(Iterable<E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}


	public FluentList<E> delete(E... list) {
		removeAll(asList(list));
		return this;
	}

	public FluentList<E> delete(Iterable<E> iterable) {
		return delete(new Sequence<E>(iterable));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return toArray((T[]) Array.newInstance(clazz, size()));
	}
}
