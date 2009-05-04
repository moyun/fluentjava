package org.fluentjava;

import org.fluentjava.closures.Closure;

/**
 * Fluency that delegates my to a target.
 */
public class FluencyDelegator extends Fluency {

	protected final Object target;

	public FluencyDelegator(Object target) {
		this.target = target;
	}

	@Override
	protected Closure my(String methodName) {
		return FluentUtils.my(target, methodName);
	}

}
