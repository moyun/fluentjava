package org.fluentjava.collections;

import java.lang.reflect.Array;
import java.util.HashSet;

import org.fluentjava.iterators.ExtendedIterable;
import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

public class ExtendedSet<E> extends HashSet<E> implements ExtendedIterable<E> {

	private static final long serialVersionUID = 1L;

	public ExtendedSet(E...elements) {
		insert(elements);
	}

	public static <E> ExtendedSet<E> set(E...element) {
		return new ExtendedSet<E>(element);
	}

	public ExtendedSet<E> insert(E element) {
		add(element);
		return this;
	}

	public ExtendedSet<E> insert(E... element) {
		for (E e : element) {
			add(e);
		}
		return this;
	}

	public ExtendedSet<E> insert(Iterable<E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}

	public ExtendedSet<E> delete(E...elements) {
		removeAll(set(elements));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return 	toArray((T[]) Array.newInstance(clazz, size()));
	}
	
	@Override
	public ExtendedIterator<E> iterator() {
	return new ExtendedIteratorAdapter<E>(super.iterator());
	}
}
