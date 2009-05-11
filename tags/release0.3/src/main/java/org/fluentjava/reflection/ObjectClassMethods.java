package org.fluentjava.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fluentjava.collections.Pair;

/**
 * Class that tells if a method has was inherited from object (ignoring overriding).
 * Mostly used on Coercing to closures the interfaces that have Object methods on their
 * signature (such as {@link Comparator}).
 */
public class ObjectClassMethods {
	/*
	 * Variables
	 */
	private final Map<MethodSignature, Method> mapa;

	/*
	 * Constructors
	 */
	public ObjectClassMethods() {
		this.mapa = buildMapOfMethods();
	}

	/*
	 * Public Methods
	 */
	/**
	 * Says if a method has the same signature of a method defined on the class Object.
	 * @param method
	 * @return
	 */
	public boolean fromObject(Method method) {
		Method objectMethod = mapa.get(signature(method));
		if (objectMethod == null) {
			return false;
		}
		return sameReturnTypes(objectMethod, method);
	}

	/**
	 * Filters the arguments into list of the methods that are from Object class.
	 * @param methods
	 * @return
	 */
	public List<Method> filterObjectMethods(Method... methods) {
		List<Method> ret = new ArrayList<Method>();
		for (Method method : methods) {
			if (!fromObject(method)) {
				ret.add(method);
			}
		}
		return ret;
	}

	/*
	 * Other Methods
	 */

	private Map<MethodSignature, Method> buildMapOfMethods() {
		Map<MethodSignature, Method> mapOfMethods =
				new HashMap<MethodSignature, Method>();
		for (Method method : Object.class.getMethods()) {
			if (!Modifier.isFinal(method.getModifiers())) {
				mapOfMethods.put(signature(method), method);
			}
		}
		return mapOfMethods;
	}

	private boolean sameReturnTypes(Method m1, Method m2) {
		Class<?>[] params1 = m1.getParameterTypes();
		Class<?>[] params2 = m2.getParameterTypes();
		if (params1.length != params2.length) {
			return false;
		}
		for (int i = 0; i < params1.length; i++) {
			if (!params1[i].equals(params2[i])) {
				return false;
			}
		}
		return true;
	}

	private MethodSignature signature(Method method) {
		return new MethodSignature(method);
	}

	private static class MethodSignature extends Pair<String, Class<?>> {
		public MethodSignature(Method method) {
			super(method.getName(), method.getReturnType());
		}
	}
}
