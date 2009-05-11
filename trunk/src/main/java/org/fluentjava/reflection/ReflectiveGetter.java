package org.fluentjava.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Utility class that finds getters and fields. {@link RuntimeReflectionException} is
 * thrown if any exceptions are caught while reflecting upon the object.
 */
public class ReflectiveGetter {

	/*
	 * Public Methods
	 */
	/**
	 * Reflectively searches one public attribute called fieldName and get its value.
	 * 
	 * @param target
	 * @param fieldName
	 * @return
	 */
	public Object getFieldAttribute(Object target, String fieldName) {
		try {
			Field field = target.getClass().getField(fieldName);
			return field.get(target);
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
	}

	/**
	 * 
	 * Searches a public getter <b>methods</b> and invoke it on target. That is: public
	 * methods starting with <i>is</i> or <i>get</i>, receiving no arguments and returning
	 * anything. If two getters exist, one will be picked arbitrarily.
	 * 
	 * @param target
	 * @param getterName
	 * @return
	 */
	public Object getFromGetter(Object target, String getterName) {
		try {
			for (Method method : target.getClass().getMethods()) {
				String methodName = method.getName();
				if (isGetter(method, methodName, getterName)) {
					return method.invoke(target);
				}
			}
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
		throw new RuntimeReflectionException(String.format(
				"No getter called %s found on %s", getterName, target));

	}

	/**
	 * Tries to get fieldName by using a getter, and if i can't, will try public fields.
	 * In other words, it combines the functionalities of
	 * {@link #getFieldAttribute(Object, String)} and
	 * {@link #getFromGetter(Object, String)}.
	 * 
	 * @param target
	 * @param fieldName
	 * @return
	 */
	public Object getFieldOrGetter(Object target, String fieldName) {
		try {
			for (Method method : target.getClass().getMethods()) {
				String methodName = method.getName();
				if (isGetter(method, methodName, fieldName)) {
					return method.invoke(target);
				}
			}
			return getFieldAttribute(target, fieldName);
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
	}

	/*
	 * Other Methods
	 */
	private boolean isGetter(Method method, String methodName, String getterName) {
		return method.getParameterTypes().length == 0
				&& hasGetterName(methodName, getterName) && doesNotReturnVoid(method);
	}

	private boolean doesNotReturnVoid(Method method) {
		return !void.class.isAssignableFrom(method.getReturnType());
	}

	private boolean hasGetterName(String methodName, String getterName) {
		return startsWithIs(methodName, getterName)
				|| startsWithGet(methodName, getterName);
	}

	private boolean startsWithGet(String methodName, String getterName) {
		return methodName.equals("get" + capitalize(getterName));
	}

	private boolean startsWithIs(String methodName, String getterName) {
		return methodName.equals("is" + capitalize(getterName));
	}

	private String capitalize(String s) {
		if (s.length() == 0) {
			return s;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
