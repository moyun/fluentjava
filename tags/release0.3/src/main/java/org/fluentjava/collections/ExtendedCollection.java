package org.fluentjava.collections;

import java.util.Collection;

/**
 * Collection that accepts varargs for: {@link Collection#containsAll(Collection)},
 * {@link Collection#removeAll(Collection)}, {@link Collection#retainAll(Collection)},
 * {@link Collection#addAll(Collection)}. Also, has a toArray method that accepts a Class
 * instead of an array. Also, introduces containsAny, with both varargs and collection
 * arguments.
 * 
 * @param <E>
 */
public interface ExtendedCollection<E> extends Collection<E> {
	/**
	 * Convenient varargs call to {@link #containsAll(Collection)}.
	 * 
	 * @param list
	 * @return
	 */
	boolean containsAll(E... list);

	/**
	 * Convenient varargs call to {@link #containsAny(Collection)}.
	 * 
	 * @param list
	 * @return
	 */
	boolean containsAny(E... list);

	/**
	 * Returns <tt>true</tt> if this collection contains any of the elements in the
	 * specified collection.
	 * 
	 * @param list
	 * @return
	 */
	boolean containsAny(Collection<?> c);

	/**
	 * Convenient varargs call to {@link #removeAll(Collection)}.
	 * 
	 * @param list
	 * @return
	 */
	boolean removeAll(E... list);

	/**
	 * Convenient varargs call to {@link #retainAll(Collection)}.
	 * 
	 * @param list
	 * @return
	 */
	boolean retainAll(E... list);

	/**
	 * Convenient varargs call to {@link #addAll(Collection)}.
	 * 
	 * @param list
	 * @return
	 */
	boolean addAll(E... list);

	/**
	 * Similar to the method toArray, but receives no args and is type safe. Also, the
	 * returned array is just a copy. Note that the class is necessary, as Generics by
	 * themselves cannot solve this problem alone. For more info:
	 * http://www.ibm.com/developerworks/java/library/j-jtp01255.html
	 * 
	 * @return A copy of the list as an array.
	 */
	<T> T[] array(Class<T> clazz);
}
