package org.fluentjava.collections;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.as;
import static org.fluentjava.FluentUtils.fromList;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ListIterator;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Wraps around any {@link List} implementation, and delegates all set methods to this
 * {@link #delegateList} .
 * 
 * @param <E>
 */
public class ForwardingFluentList<E> extends AbstractEnumerable<E>
		implements
			FluentList<E>, Cloneable {
	/*
	 * Variables
	 */
	protected final List<E> delegateList;

	/*
	 * Constructors
	 */
	public ForwardingFluentList(List<E> delegateList) {
		this.delegateList = delegateList;
	}

	/*
	 * Public Methods
	 */
	@Override
	public boolean containsAny(E... list) {
		return containsAny(asList(list));
	}

	@Override
	public boolean containsAny(Collection<?> c) {
		for (Object object : c) {
			if (contains(object)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addAll(E... list) {
		return addAll(asList(list));
	}

	@Override
	public boolean containsAll(E... list) {
		return containsAll(asList(list));
	}

	@Override
	public boolean removeAll(E... list) {
		return removeAll(asList(list));
	}

	@Override
	public boolean retainAll(E... list) {
		return retainAll(asList(list));
	}

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

	@Override
	public Object clone() {
		return toList();
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
				List<?> subList = as(e);
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

	public boolean contains(Object o) {
		return delegateList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegateList.containsAll(c);
	}

	@Override
	public boolean equals(Object o) {
		return delegateList.equals(o);
	}

	public E get(int index) {
		return delegateList.get(index);
	}

	@Override
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

	public FluentList<E> subList(int fromIndex, int toIndex) {
		return fromList(delegateList.subList(fromIndex, toIndex));  
	}

	public Object[] toArray() {
		return delegateList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return delegateList.toArray(a);
	}

	@Override
	public String toString() {
		return delegateList.toString();
	}
	
	@Override
	public FluentList<E> subListTo(int toIndex) {
		return subList(0, toIndex);
	}
	
	@Override
	public FluentList<E> subList(int fromIndex) {
		return subList(fromIndex, size());
	}

}
