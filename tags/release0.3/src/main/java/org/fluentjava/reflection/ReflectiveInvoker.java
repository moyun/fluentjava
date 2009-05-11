package org.fluentjava.reflection;

import static java.util.Arrays.asList;
import static org.fluentjava.FluentUtils.pair;
import static org.fluentjava.iterators.CompositeIterator.fromIterables;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.fluentjava.FluentUtils;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.collections.Pair;
import org.fluentjava.iterators.CountingIterator;

/**
 * Invokes a method based only the name of the method and the class of the object whose
 * method is being invoked. Finds the best fit method if overloaded methods are found.
 * This class is not thread safe.
 */
public class ReflectiveInvoker {
	/*
	 * Constants
	 */
	private static final FluentMap<Class<?>, Class<?>> PrimitiveMap =
		FluentUtils.map(pair(Boolean.TYPE, Boolean.class),
				pair(Byte.TYPE, Byte.class),
				pair(Character.TYPE, Character.class),
				pair(Double.TYPE, Double.class),
				pair(Float.TYPE, Float.class),
				pair(Integer.TYPE, Integer.class),
				pair(Long.TYPE, Long.class),
				pair(Short.TYPE, Short.class));
	
	/*
	 * Variables
	 */
	protected final String methodName;
	protected final Object target;
	private List<Object> args;
	private Method candidate;
	private Method varArgsCandidate;

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
	/**
	 * Actually do all the method lookup and invocation.
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * From {@link Method#invoke(Object, Object...)}
	 * @throws InvocationTargetException
	 * From {@link Method#invoke(Object, Object...)}
	 */
	public Object invoke() throws IllegalAccessException, InvocationTargetException {
		findCandidatesByNameAndAgs();
		if (candidate != null) {
			return candidate.invoke(target, args.toArray());
		}
		if (varArgsCandidate != null) {
			return invokeVarArgs(varArgsCandidate);
		}
		throw new IllegalArgumentException("Method of name " + methodName
				+ " could not be found on " + target);
	}

	/**
	 * Like {@link #invoke()}, but looks up declared methods as well.
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object priviligedInvoke() throws IllegalAccessException,
			InvocationTargetException {
		findCandidatesByNameAndAgs();
		if (!wasMethodFound()) {
			privilegedFindCandidatesByNameAndAgs();
		}
		if (candidate != null) {
			candidate.setAccessible(true);
			return candidate.invoke(target, args.toArray());
		}
		if (varArgsCandidate != null) {
			varArgsCandidate.setAccessible(true);
			return invokeVarArgs(varArgsCandidate);
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

	private void privilegedFindCandidatesByNameAndAgs() {
		findMethodsFromList(target.getClass().getDeclaredMethods());
	}

	private void findCandidatesByNameAndAgs() {
		findMethodsFromList(target.getClass().getMethods());
	}

	private void findMethodsFromList(Method[] methods) {
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				analizeMethod(method);
				if (wasMethodFound()) {
					return;
				}
			}
		}
	}

	private boolean wasMethodFound() {
		return candidate != null || varArgsCandidate != null;
	}

	private void analizeMethod(Method method) {
		if (method.isVarArgs()) {
			if (varArgsMatch(method)) {
				varArgsCandidate = method;
				return;
			}
		}
		if (argumentsMatch(method)) {
			candidate = method;
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
		if (subList.size() == 1) {
			Object object = subList.get(0);
			if (object.getClass().isArray()) {
				return (varArgsArrayType(method).isAssignableFrom(object.getClass()));
			}
		}
		Class<?> elementsType = varArgsType(method);
		for (Object object : subList) {
			if (!canArgumentBeAssignedToType(elementsType, object)) {
				return false;
			}
		}
		return true;
	}

	private Class<?> varArgsType(Method method) {
		return varArgsArrayType(method).getComponentType();
	}

	private Class<?> varArgsArrayType(Method method) {
		Class<?>[] argTypes = method.getParameterTypes();
		return argTypes[argTypes.length - 1];
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
			if (!canArgumentBeAssignedToType(pair.first, pair.second)) {
				return false;
			}
		}
		return true;
	}

	private boolean canArgumentBeAssignedToType(Class<?> type, Object argument) {
		if (type.isPrimitive()) {
			return PrimitiveMap.get(type).isInstance(argument);
		}
		return type.isInstance(argument);
	}

	private Object invokeVarArgs(Method method) throws IllegalAccessException,
			InvocationTargetException {
		List<Object> toInvoke = new ArrayList<Object>(nonVarArgs(method));
		toInvoke.add(createVarArgsArray(method));
		return method.invoke(target, toInvoke.toArray());
	}

	private Object createVarArgsArray(Method method) {
		List<Object> varargs = varArgs(method);
		if (varargs.size() == 1) {
			Object object = varargs.get(0);
			if (object.getClass().isArray()) {
				return object;
			}
		}
		Object ret = createArrayOfTheCorrectType(method, varargs);
		copyVarArgsToArray(varargs, ret);
		return ret;
	}

	private Object createArrayOfTheCorrectType(Method method, List<Object> varargs) {
		Class<?> varArgsType = varArgsType(method);
		return Array.newInstance(varArgsType, varargs.size());
	}

	private void copyVarArgsToArray(List<Object> varargs, Object array) {
		CountingIterator<Object> it = new CountingIterator<Object>(varargs);
		for (Object object : it) {
			Array.set(array, it.iterationIndex(), object);
		}
	}

}
