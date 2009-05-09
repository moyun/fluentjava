package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static org.fluentjava.iterators.CompositeIterator.fromIterables;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.Pair;
import org.fluentjava.collections.Sequence;
import org.fluentjava.iterators.CountingIterator;

/**
 * Invokes a method based only the name of the method and the class of the object whose
 * method is being invoked. Finds the best fit method if overloaded methods are found.
 */
public class ReflectiveInvoker {

	protected final String methodName;
	protected final Object target;
	protected final List<Object> args;
	private FluentList<Method> candidates = new Sequence<Method>();
	private FluentList<Method> varArgsCandidates = new Sequence<Method>();

	public ReflectiveInvoker(String methodName, Object target, List<Object> args) {
		this.methodName = methodName;
		this.target = target;
		this.args = args;
	}

	public Object invoke() throws IllegalAccessException, InvocationTargetException {
		findCandidatesByNameAndAgs();
		if (candidates.size() >= 1) {
			return candidates.any().invoke(target, args.toArray());
		}
		if (varArgsCandidates.size() == 1) {
			return invokeVarArgs(args, varArgsCandidates.any());
		}
		if (varArgsCandidates.size() > 1) {
			for (Method method : varArgsCandidates) {
				boolean isOk = true;
				Class<?>[] argTypes = method.getParameterTypes();
				Object[] toInvoke = toInvokeList(args, method);
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
					return method.invoke(target, toInvoke);
				}
			}
		}
		throw new IllegalArgumentException("Method of name " + methodName
				+ " could not be found on " + target);
	}

	private void findCandidatesByNameAndAgs() {
		for (Method method : target.getClass().getMethods()) {
			if (methodName.equals(method.getName())) {
				analizeMethod(method);
			}
		}
	}

	private void analizeMethod(Method method) {
		if (method.isVarArgs()) {
			analizeVarArgs(method);
			return;
		}
		analizeNonVarags(method);
	}

	private void analizeVarArgs(Method method) {
		if (args.size() >= (method.getParameterTypes().length - 1)) {
			varArgsCandidates.add(method);
		}
	}

	private void analizeNonVarags(Method method) {
		if (argumentsMatch(method)) {
			candidates.add(method);
		}
	}

	private boolean argumentsMatch(Method method) {
		if (!(method.getParameterTypes().length == args.size())) {
			return false;
		}
		List<Class<?>> argTypes = asList(method.getParameterTypes());
		for (Pair<Class<?>, Object> pair : fromIterables(argTypes, args)) {
			if (!pair.first.isInstance(pair.second)) {
				return false;
			}
		}
		return true;
	}

	private Object invokeVarArgs(List<Object> argList, Method method)
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
		Object[] ret = (Object[]) Array.newInstance(elementsType, arraySize);
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
