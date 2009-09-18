package org.fluentjava.collections;

import java.util.ArrayList;
import java.util.RandomAccess;

/**
 * Standard implementation of {@link FluentList}.
 * 
 * @param <E>
 * Type of elements
 */
public class Sequence<E> extends ForwardingFluentList<E>
		implements
			RandomAccess {
	private static final long serialVersionUID = 2L;

	/*
	 * Constructors
	 */
	/**
	 * Creates an empty Sequence.
	 */
	public Sequence() {
		super(new ArrayList<E>());
	}

	/**
	 * Creates a Sequence with elements args.
	 * 
	 * @param args
	 */
	public Sequence(E... args) {
		this();
		insert(args);
	}

	/**
	 * Creates a Sequence the with the iterable elements.
	 * 
	 * @param iterable
	 */
	public Sequence(Iterable<? extends E> iterable) {
		this();
		insert(iterable);
	}

}
