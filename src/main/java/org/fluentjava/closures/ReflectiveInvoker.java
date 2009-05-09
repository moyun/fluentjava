package org.fluentjava.closures;

import static java.util.Arrays.asList;
import static org.fluentjava.iterators.CompositeIterator.fromIterables;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
	/*
	 * Variables
	 */
	protected final String methodName;
	protected final Object target;
	protected final List<Object> args;
	private FluentList<Method> candidates = new Sequence<Method>();
	private FluentList<Method> varArgsCandidates = new Sequence<Method>();

	/*
	 * Constructors
	 */
	public ReflectiveInvoker(String methodName, Object target, List<Object> args) {
		this.methodName = methodName;
		this.target = target;
		this.args = args;
	}

	/*
	 * Public Methods
	 */
	public Object invoke() throws IllegalAccessException, InvocationTargetException {
		findCandidatesByNameAndAgs();
		if (candidates.size() >= 1) {
			return candidates.any().invoke(target, args.toArray());
		}
		if (varArgsCandidates.size() >= 1) {
			return invokeVarArgs(varArgsCandidates.any());
		}
		throw new IllegalArgumentException("Method of name " + methodName
				+ " could not be found on " + target);
	}

	/*
	 * Other Methods
	 */
	private List<Object> varArgs(Method method) {
		return args.subList(nonVarArgsSize(method), args.size());
	}

	private List<Object> nonVarArgs(Method method) {
		return args.subList(0, nonVarArgsSize(method));
	}

	private int nonVarArgsSize(Method method) {
		return method.getParameterTypes().length - 1;
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
			if (varArgsMatch(method)) {
				varArgsCandidates.add(method);
			}
			return;
		}
		if (argumentsMatch(method)) {
			candidates.add(method);
		}
	}

	private boolean varArgsMatch(Method method) {
		boolean isAgsSizeConsumable = args.size() >= nonVarArgsSize(method);
		if (!isAgsSizeConsumable) {
			return false;
		}
		boolean nonVarArgsMatch = argumentsMatchParameters(method, nonVarArgs(method));
		if (!nonVarArgsMatch) {
			return false;
		}
		return argumentsMatchVarArgsParameters(method, varArgs(method));
	}

	private boolean argumentsMatchVarArgsParameters(Method method, List<Object> subList) {
		Class<?> elementsType = varArgsType(method);
		for (Object object : subList) {
			if (!elementsType.isInstance(object)) {
				return false;
			}
		}
		return true;
	}

	private Class<?> varArgsType(Method method) {
		Class<?>[] argTypes = method.getParameterTypes();
		Class<?> typeOfVarArgs = argTypes[argTypes.length - 1];
		return typeOfVarArgs.getComponentType();
	}

	private boolean argumentsMatch(Method method) {
		if (!(method.getParameterTypes().length == args.size())) {
			return false;
		}
		return argumentsMatchParameters(method, args);
	}

	private boolean argumentsMatchParameters(Method method, List<Object> arguments) {
		List<Class<?>> argTypes = asList(method.getParameterTypes());
		for (Pair<Class<?>, Object> pair : fromIterables(argTypes, arguments)) {
			if (!pair.first.isInstance(pair.second)) {
				return false;
			}
		}
		return true;
	}

	private Object invokeVarArgs(Method method) throws IllegalAccessException,
			InvocationTargetException {
		List<Object> toInvoke = new ArrayList<Object>(nonVarArgs(method));
		toInvoke.add(createVarArgsArray(method));
		return method.invoke(target, toInvoke.toArray());
	}

	private Object[] createVarArgsArray(Method method) {
		List<Object> varargs = varArgs(method);
		Object[] ret = createArrayOfTheCorrectType(method, varargs);
		copyVarArgsToArray(varargs, ret);
		return ret;
	}

	private Object[] createArrayOfTheCorrectType(Method method, List<Object> varargs) {
		return (Object[]) Array.newInstance(varArgsType(method), varargs.size());
	}

	private void copyVarArgsToArray(List<Object> varargs, Object[] array) {
		CountingIterator<Object> it = new CountingIterator<Object>(varargs);
		for (Object object : it) {
			array[it.iterationIndex()] = object;
		}
	}
}
