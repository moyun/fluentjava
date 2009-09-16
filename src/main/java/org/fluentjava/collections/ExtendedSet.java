package org.fluentjava.collections;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.Set;

/**
 * Standard implementation of FluentSet.
 * 
 * @param <E>
 * Type of elements
 */
public class ExtendedSet<E> extends ForwardingFluentSet<E> implements Externalizable {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		Set<E> inobj = (Set<E>) in.readObject();
		delegateSet.addAll(inobj);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(delegateSet);
	}
}
