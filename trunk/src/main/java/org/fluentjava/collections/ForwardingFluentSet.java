package org.fluentjava.collections;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.as;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;

import org.fluentjava.iterators.ExtendedIterator;
import org.fluentjava.iterators.ExtendedIteratorAdapter;

/**
 * Wraps around any {@link Set} implementation, and delegates all set methods to this
 * {@link #delegateSet} .
 * 
 * @param <E>
 */
public class ForwardingFluentSet<E> extends AbstractEnumerable<E>
		implements
			FluentSet<E>,
			Cloneable {
	/*
	 * Variables
	 */
	protected final Set<E> delegateSet;

	/*
	 * Constructors
	 */
	public ForwardingFluentSet(Set<E> delegateSet) {
		this.delegateSet = delegateSet;
	}

	/*
	 * Public Methods
	 */
	@Override
	public ExtendedIterator<E> iterator() {
		return new ExtendedIteratorAdapter<E>(delegateSet.iterator());
	}

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

	public boolean contains(Object o) {
		return delegateSet.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegateSet.containsAll(c);
	}

	@Override
	public FluentSet<E> intersect(Iterable<? extends E> iterable) {
		FluentSet<E> clone = toSet();
		clone.retainAll(iterableToCollection(iterable));
		return clone;
	}

	@Override
	public FluentSet<E> and(Iterable<? extends E> iterable) {
		return intersect(iterable);
	}

	@Override
	public FluentSet<E> union(Iterable<? extends E> iterable) {
		FluentSet<E> clone = toSet();
		clone.addAll(iterableToCollection(iterable));
		return clone;
	}

	@Override
	public FluentSet<E> or(Iterable<? extends E> iterable) {
		return union(iterable);
	}

	@Override
	public FluentSet<E> symmetricDifference(Iterable<? extends E> iterable) {
		FluentSet<E> union = union(iterable);
		union.removeAll(intersect(iterable));
		return union;
	}

	@Override
	public FluentSet<E> xor(Iterable<? extends E> iterable) {
		return symmetricDifference(iterable);
	}

	@Override
	public FluentSet<E> difference(Iterable<? extends E> iterable) {
		FluentSet<E> clone = toSet();
		clone.removeAll(iterableToCollection(iterable));
		return clone;
	}

	@Override
	public FluentSet<E> minus(Iterable<? extends E> iterable) {
		return difference(iterable);
	}
	
	@Override
	public Object clone() {
		return toSet();
	}

	/*
	 * Delegate Methods
	 */
	@Override
	public boolean equals(Object o) {
		return delegateSet.equals(o);
	}

	@Override
	public int hashCode() {
		return delegateSet.hashCode();
	}

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

	@Override
	public String toString() {
		return delegateSet.toString();
	}

	/*
	 * Other Methods
	 */
	private Collection<? extends E> iterableToCollection(Iterable<? extends E> iterable) {
		if (iterable instanceof Collection) {
			return as(iterable);
		}
		return new ExtendedSet<E>(iterable);
	}

}
