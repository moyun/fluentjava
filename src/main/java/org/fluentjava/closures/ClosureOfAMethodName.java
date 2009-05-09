package org.fluentjava.closures;

import static java.util.Arrays.asList;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Sequence;
import org.fluentjava.iterators.CountingIterator;

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
		if (candidates.size() > 1) {
			for (Method method : candidates) {
				boolean isOk = true;
				Class<?>[] argTypes = method.getParameterTypes();
				CountingIterator<Object> it = new CountingIterator<Object>(restList);
				for (Object arg : it) {
					Class<?> typeVariable = argTypes[it.iterationIndex()];
					if (!typeVariable.isInstance(arg)) {
						isOk = false;
						break;
					}
				}
				if (isOk) {
					return method.invoke(target, restList.toArray());
				}
			}
		}
		if (varArgsCandidates.size() == 1) {
			return invokeVarArgs(target, restList, varArgsCandidates.any());
		}
		if (varArgsCandidates.size() > 1) {
			for (Method method : varArgsCandidates) {
				boolean isOk = true;
				Class<?>[] argTypes = method.getParameterTypes();
				Object[] toInvoke = toInvokeList(restList, method);
				if (toInvoke == null) {
					continue;
				}
				List<Object> newrestList = asList(toInvoke);
				CountingIterator<Object> it = new CountingIterator<Object>(newrestList);
				for (Object arg : it) {
					Class<?> typeVariable = argTypes[it.iterationIndex()];
					if (!typeVariable.isInstance(arg)) {
						isOk = false;
						break;
					}
				}
				if (isOk) {
					return  method.invoke(target, toInvoke);
				}
			}
		}
		throw new IllegalArgumentException("Method of name " + methodName
				+ " could not be found on " + target);
	}

	private Object invokeVarArgs(Object target, List<Object> argList, Method method)
			throws IllegalAccessException, InvocationTargetException {
		Object[] toInvoke = toInvokeList(argList, method);
		if (toInvoke == null) {
			throw new IllegalArgumentException("Method of name " + methodName
					+ " could not be found on " + target);
		}
		return method.invoke(target, toInvoke);
	}

	private Object[] toInvokeList(List<Object> argList, Method method) {
		Class<?>[] argTypes = method.getParameterTypes();
		Object[] toInvoke = new Object[argTypes.length];
		int nonVarArgsCount = argTypes.length - 1;
		for (int i = 0; i < nonVarArgsCount; i++) {
			toInvoke[i] = argList.get(i);
		}
		toInvoke[nonVarArgsCount] = buildVarArgsArray(argList, argTypes, nonVarArgsCount);
		return toInvoke;
	}

	private Object[] buildVarArgsArray(List<Object> argList,
			Class<?>[] argTypes,
			int nonVarArgsCount) {
		Class<?> typeOfVarArgs = argTypes[nonVarArgsCount];
		Class<?> elementsType = typeOfVarArgs.getComponentType();
		assert (typeOfVarArgs.isArray());
		int arraySize = argList.size() - nonVarArgsCount;
		Object[] ret = (Object[]) Array.newInstance(elementsType,
				arraySize);
		List<Object> restOfArgs = argList.subList(nonVarArgsCount, argList.size());
		CountingIterator<Object> it = new CountingIterator<Object>(restOfArgs);
		for (Object object : it) {
			if (!elementsType.isInstance(object)) {
				return null;
			}
			ret[it.iterationIndex()] = object;
		}
		return ret;
	}

}
