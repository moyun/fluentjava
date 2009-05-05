package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Standard implementation of FluentSet.
 * 
 * @param <E>
 * Type of elements
 */
public class ExtendedSet<E> extends AbstractEnumerable<E> implements FluentSet<E>, Cloneable, Serializable {
	private static final long serialVersionUID = 2L;

	protected final HashSet<E> delegateSet = new HashSet<E>();
	
	/*
	 * Constructors
	 */
	/**
	 * Creates an empty ExtendedSet.
	 */
	public ExtendedSet() {
	}

	/**
	 * Creates an ExtendedSet with elements args.
	 * 
	 * @param args
	 */
	public ExtendedSet(E... args) {
		insert(args);
	}

	/**
	 * Creates an ExtendedSet the with the iterable elements.
	 * 
	 * @param iterable
	 */
	public ExtendedSet(Iterable<? extends E> iterable) {
		insert(iterable);
	}

	/*
	 * Public Methods
	 */

	@Override
	public ExtendedIterator<E> iterator() {
		return new ExtendedIteratorAdapter<E>(delegateSet.iterator());
	}

	public FluentSet<E> insert(E element) {
		add(element);
		return this;
	}

	public FluentSet<E> insert(E... set) {
		return insert(asList(set));
	}

	public FluentSet<E> insert(Iterable<? extends E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}

	public FluentSet<E> delete(E... elements) {
		removeAll(asList(elements));
		return this;
	}

	public FluentSet<E> delete(Iterable<? extends E> iterable) {
		removeAll(new Sequence<E>(iterable));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return toArray((T[]) Array.newInstance(clazz, size()));
	}

	public boolean add(E e) {
		return delegateSet.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		return delegateSet.addAll(c);
	}

	public void clear() {
		delegateSet.clear();
	}

	public Object clone() {
		return delegateSet.clone();
	}

	public boolean contains(Object o) {
		return delegateSet.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegateSet.containsAll(c);
	}

	public boolean equals(Object o) {
		return delegateSet.equals(o);
	}

	public int hashCode() {
		return delegateSet.hashCode();
	}

	/*
	 * Delegate Methods
	 */
	public boolean isEmpty() {
		return delegateSet.isEmpty();
	}

	public boolean remove(Object o) {
		return delegateSet.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return delegateSet.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return delegateSet.retainAll(c);
	}

	public int size() {
		return delegateSet.size();
	}

	public Object[] toArray() {
		return delegateSet.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return delegateSet.toArray(a);
	}

	public String toString() {
		return delegateSet.toString();
	}

}
