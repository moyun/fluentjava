package org.fluentjava.collections;

import java.lang.reflect.Array;
import java.util.HashSet;

public class FluentSet<E> extends HashSet<E> {

	private static final long serialVersionUID = 1L;

	public FluentSet(E...elements) {
		insert(elements);
	}

	public static <E> FluentSet<E> set(E...element) {
		return new FluentSet<E>(element);
	}

	public FluentSet<E> insert(E element) {
		add(element);
		return this;
	}

	public FluentSet<E> insert(E... element) {
		for (E e : element) {
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
