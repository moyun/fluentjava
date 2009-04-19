package org.fluentjava.collections;

import java.lang.reflect.Array;
import java.util.HashSet;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

public class ExtendedSet<E> extends HashSet<E> implements FluentSet<E> {

	private static final long serialVersionUID = 1L;

	public ExtendedSet() { }
	
	public ExtendedSet(E...elements) {
		insert(elements);
	}
	
	public ExtendedSet(Iterable<E> element) {
		insert(element);
	}

	public static <E> ExtendedSet<E> set(E...element) {
		return new ExtendedSet<E>(element);
	}

	/* (non-Javadoc)
	 * @see org.fluentjava.collections.FluentSet#insert(E)
	 */
	public FluentSet<E> insert(E... element) {
		for (E e : element) {
			add(e);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see org.fluentjava.collections.FluentSet#insert(E)
	 */
	public FluentSet<E> insert(E element) {
		add(element);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.fluentjava.collections.FluentSet#insert(java.lang.Iterable)
	 */
	public FluentSet<E> insert(Iterable<E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see org.fluentjava.collections.FluentSet#delete(E)
	 */
	public FluentSet<E> delete(E...elements) {
		removeAll(set(elements));
		return this;
	}

	/* (non-Javadoc)
	 * @see org.fluentjava.collections.FluentSet#array(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return 	toArray((T[]) Array.newInstance(clazz, size()));
	}
	
	/* (non-Javadoc)
	 * @see org.fluentjava.collections.FluentSet#iterator()
	 */
	@Override
	public ExtendedIterator<E> iterator() {
	return new ExtendedIteratorAdapter<E>(super.iterator());
	}
}
