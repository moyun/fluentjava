package org.fluentjava.collections;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Standard implementation of {@link FluentList}.
 * 
 * @param <E>
 * Type of elements
 */
public class Sequence<E> extends AbstractEnumerable<E>
		implements
			FluentList<E>,
			RandomAccess,
			Cloneable,
			Serializable {
	private static final long serialVersionUID = 2L;

	protected final ArrayList<E> delegateList = new ArrayList<E>();

	/*
	 * Constructors
	 */
	/**
	 * Creates an empty Sequence.
	 */
	public Sequence() {
	}

	/**
	 * Creates a Sequence with elements args.
	 * 
	 * @param args
	 */
	public Sequence(E... args) {
		insert(args);
	}

	/**
	 * Creates a Sequence the with the iterable elements.
	 * 
	 * @param iterable
	 */
	public Sequence(Iterable<? extends E> iterable) {
		insert(iterable);
	}

	/*
	 * Public Methods
	 */

	@Override
	public ExtendedIterator<E> iterator() {
		return new ExtendedIteratorAdapter<E>(delegateList.iterator());
	}

	public FluentList<E> insert(E e) {
		add(e);
		return this;
	}

	public FluentList<E> insert(E... list) {
		return insert(asList(list));
	}

	public FluentList<E> insert(Iterable<? extends E> iterable) {
		for (E e : iterable) {
			add(e);
		}
		return this;
	}

	public FluentList<E> delete(E... list) {
		removeAll(asList(list));
		return this;
	}

	public FluentList<E> delete(Iterable<? extends E> iterable) {
		removeAll(new Sequence<E>(iterable));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] array(Class<T> clazz) {
		return toArray((T[]) Array.newInstance(clazz, size()));
	}

	@Override
	public FluentList<Object> flatten() {
		FluentList<Object> ret = new Sequence<Object>();
		recFlatten(ret, new IdentityHashMap<Object, Boolean>(), this);
		return ret;
	}

	/*
	 * Other Methods
	 */
	private void recFlatten(FluentList<Object> ret,
			IdentityHashMap<Object, Boolean> visitedLists,
			List<?> target) {
		if (visitedLists.containsKey(target)) {
			throw new IllegalArgumentException("Circular references");
		}
		visitedLists.put(target, true);
		for (Object e : target) {
			if (e instanceof List) {
				List<?> subList = (List<?>) e;
				recFlatten(ret, visitedLists, subList);
			}
			else {
				ret.add(e);
			}
		}
	}

	/*
	 * Delegate Methods
	 */
	public boolean add(E e) {
		return delegateList.add(e);
	}

	public void add(int index, E element) {
		delegateList.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		return delegateList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return delegateList.addAll(index, c);
	}

	public void clear() {
		delegateList.clear();
	}

	public Object clone() {
		return delegateList.clone();
	}

	public boolean contains(Object o) {
		return delegateList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegateList.containsAll(c);
	}

	public void ensureCapacity(int minCapacity) {
		delegateList.ensureCapacity(minCapacity);
	}

	public boolean equals(Object o) {
		return delegateList.equals(o);
	}

	public E get(int index) {
		return delegateList.get(index);
	}

	public int hashCode() {
		return delegateList.hashCode();
	}

	public int indexOf(Object o) {
		return delegateList.indexOf(o);
	}

	public boolean isEmpty() {
		return delegateList.isEmpty();
	}

	public int lastIndexOf(Object o) {
		return delegateList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return delegateList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return delegateList.listIterator(index);
	}

	public E remove(int index) {
		return delegateList.remove(index);
	}

	public boolean remove(Object o) {
		return delegateList.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return delegateList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return delegateList.retainAll(c);
	}

	public E set(int index, E element) {
		return delegateList.set(index, element);
	}

	public int size() {
		return delegateList.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return delegateList.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return delegateList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return delegateList.toArray(a);
	}

	public String toString() {
		return delegateList.toString();
	}

	public void trimToSize() {
		delegateList.trimToSize();
	}
}
