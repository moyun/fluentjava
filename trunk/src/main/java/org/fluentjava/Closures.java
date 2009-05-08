package org.fluentjava;

import org.fluentjava.closures.Closure;

/**
 * Class with static methods that allows ease access to pre-made closures.
 */
public class Closures {
	public static Closure identity() {
		return new Identity();
	}

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
}
