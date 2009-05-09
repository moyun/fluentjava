package org.fluentjava.closures;

import static java.util.Arrays.asList;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Sequence;

/**
 * Whenever this {@link #call(Object...)} is invoked, it reflectively invokes the
 * {@link #methodName} into the first argument passed, passing the rest of the arguments
 * to the actual method.
 */
public class ClosureOfAMethodName extends Closure {

	protected final String methodName;

	public ClosureOfAMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public Object call(Object... args) throws Exception {
		Object target = first(args);
		List<Object> restList = asList(args).subList(1, args.length);
		FluentList<Method> candidates = new Sequence<Method>();
		FluentList<Method> varArgsCandidates = new Sequence<Method>();
		for (Method method : target.getClass().getMethods()) {
			if (methodName.equals(method.getName())) {
				if (method.isVarArgs()) {
					if (restList.size() >= (method.getParameterTypes().length - 1)) {
						varArgsCandidates.add(method);
					}
				}
				else if (method.getParameterTypes().length == restList.size()) {
					candidates.add(method);
				}
			}
		}
		if (candidates.size() == 1) {
			return candidates.any().invoke(target, restList.toArray());
		}
		if (candidates.size() >= 1) {
			for (Method method : candidates) {
				Class<?>[] argTypes = method.getParameterTypes();
				for (int i = 0; i < argTypes.length; i++) {
					Class<?> typeVariable = argTypes[i];
					Object arg = restList.get(i);
					if (!typeVariable.isInstance(arg)) {
						break;
					}
					return method.invoke(target, restList.toArray());
				}
			}
		}
		if (varArgsCandidates.size() >= 1) {
			return invokeVarArgs(target, restList, varArgsCandidates.any());
		}
		throw new IllegalArgumentException("Method of name " + methodName
				+ " could not be found on " + target);
	}

	private Object invokeVarArgs(Object target, List<Object> argList, Method method)
			throws IllegalAccessException, InvocationTargetException {
		Class<?>[] argTypes = method.getParameterTypes();
		Object[] toInvoke = new Object[argTypes.length];
		int nonVarArgsCount = argTypes.length - 1;
		for (int i = 0; i < nonVarArgsCount; i++) {
			toInvoke[i] = argList.get(i);
		}
		toInvoke[nonVarArgsCount] = buildVarArgsArray(argList, argTypes, nonVarArgsCount);
		return method.invoke(target, toInvoke);
	}

	private Object[] buildVarArgsArray(List<Object> argList,
			Class<?>[] argTypes,
			int nonVarArgsCount) {
		Class<?> typeOfVarArgs = argTypes[nonVarArgsCount];
		assert (typeOfVarArgs.isArray());
		return (Object[]) Array.newInstance(typeOfVarArgs.getComponentType(),
				argList.size() - argList.size());
	}

}
