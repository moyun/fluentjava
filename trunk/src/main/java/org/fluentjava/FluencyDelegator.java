package org.fluentjava;

import org.fluentjava.closures.Closure;

public class FluencyDelegator extends Fluency {

	private Object target;
	
	public FluencyDelegator(Object target) {
		this.target = target;
	}
	
	@Override
	protected Closure my(String methodName) {
		return FluentUtils.my(target, methodName);
	}
	
}
