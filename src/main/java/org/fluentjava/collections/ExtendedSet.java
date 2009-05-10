package org.fluentjava.collections;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Standard implementation of FluentSet.
 * 
 * @param <E>
 * Type of elements
 */
public class ExtendedSet<E> extends ForwardingFluentSet<E> implements

Cloneable, Serializable {
	private static final long serialVersionUID = 2L;

	/*
	 * Constructors
	 */
	/**
	 * Creates an empty ExtendedSet.
	 */
	public ExtendedSet() {
		super(new HashSet<E>());
	}

	/**
	 * Creates an ExtendedSet with elements args.
	 * 
	 * @param args
	 */
	public ExtendedSet(E... args) {
		this();
		insert(args);
	}

	/**
	 * Creates an ExtendedSet the with the iterable elements.
	 * 
	 * @param iterable
	 */
	public ExtendedSet(Iterable<? extends E> iterable) {
		this();
		insert(iterable);
	}
}
