package org.fluentjava.reflection;

import java.lang.reflect.Field;

/**
 * Represents a field of an instance. Unaccessible fields are not retrieved.
 */
public class InstanceField {
	private Object instance;
	private Field field;

	public InstanceField(Object instance, Field field) {
		this.instance = instance;
		this.field = field;
	}

	public Object value() {
		try {
			return field.get(instance);
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
	}

	public void set(Object value) {
		try {
			field.set(instance, value);
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
	}

	public Field getField() {
		return field;
	}

}
