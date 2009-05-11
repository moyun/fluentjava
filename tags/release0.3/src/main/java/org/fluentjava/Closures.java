package org.fluentjava;

import org.fluentjava.closures.Closure;
import org.fluentjava.reflection.ReflectiveGetter;

/**
 * Class with static methods that allows ease access to pre-made closures.
 */
public class Closures {
	/*
	 * Class Methods
	 */
	/**
	 * Returns and identity closure, which returns the first element of the args.
	 * 
	 * @return
	 */
	public static Closure identity() {
		return new Identity();
	}

	/**
	 * Returns a Closure that reflectively searches one public attribute and get its
	 * value.
	 * 
	 * @param fieldName
	 * @return
	 */
	public static Closure fieldGetter(String fieldName) {
		return new ReflectiveFieldGetter(fieldName);
	}

	/**
	 * Returns a Closure that reflectively searches public getter <b>methods</b>. That is:
	 * public methods starting with <i>is</i> or <i>get</i>, receiving no arguments and
	 * returning anything. If two getters exist, one will be picked arbitrarily.
	 * 
	 * @param fieldName
	 * @return
	 */
	public static Closure getter(String fieldName) {
		return new ReflectiveMethodGetter(fieldName);
	}

	/**
	 * Returns a Closure that first tries to get fieldName by using a getter, and if i
	 * can't, will try public fields. In other words, it combines the functionalities of
	 * {@link #fieldGetter(String)} and {@link #getter(String)}.
	 * 
	 * @param string
	 * @return
	 */
	public static Closure get(String fieldName) {
		return new ReflectiveAnyGetter(fieldName);
	}

	/*
	 * Constructors
	 */
	private Closures() {
	}

	/**
	 * Returns the first element of the args.
	 */
	private static class Identity extends Closure {
		@Override
		public Object call(Object... args) throws Exception {
			return first(args);
		}
	}

	/**
	 * Reflectively searches public attributes.
	 */
	private static class ReflectiveFieldGetter extends Closure {
		protected final String fieldName;

		public ReflectiveFieldGetter(String fieldName) {
			this.fieldName = fieldName;
		}

		@Override
		public Object call(Object... args) throws Exception {
			Object target = first(args);
			return new ReflectiveGetter().getFieldAttribute(target, fieldName);
		}
	}

	private static class ReflectiveMethodGetter extends Closure {
		protected final String getterName;

		public ReflectiveMethodGetter(String getterName) {
			this.getterName = getterName;
		}

		@Override
		public Object call(Object... args) throws Exception {
			Object target = first(args);
			return new ReflectiveGetter().getFromGetter(target, getterName);
		}
	}

	/**
	 * Reflectively searches public attributes.
	 */
	private static class ReflectiveAnyGetter extends Closure {
		protected final String fieldName;

		public ReflectiveAnyGetter(String fieldName) {
			this.fieldName = fieldName;
		}

		@Override
		public Object call(Object... args) throws Exception {
			Object target = first(args);
			return new ReflectiveGetter().getFieldOrGetter(target, fieldName);
		}
	}
}
