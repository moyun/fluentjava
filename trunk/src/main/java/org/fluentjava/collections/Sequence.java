package org.fluentjava.collections;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

/**
 * Standard implementation of {@link FluentList}.
 * 
 * @param <E>
 * Type of elements
 */
public class Sequence<E> extends ForwardingFluentList<E>
		implements
			RandomAccess,
			Externalizable {
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

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		List<E> inobj = (List<E>) in.readObject();
		delegateList.addAll(inobj);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(delegateList);
	}

}
