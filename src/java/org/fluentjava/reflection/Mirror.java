package org.fluentjava.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that that provides reflective facilities over an object. Throughout the
 * documentation, we refer to the subject of reflection as mirrored object. A mirror can
 * be priviliged (with access to private methods and fields) or unpriviliged (without).
 */
public class Mirror {
	/*
	 * Factory Methods
	 */
	public static Mirror priviligedMirror(Object mirroredObject) {
		return new Mirror(mirroredObject, true);
	}

	public static Mirror mirror(Object mirroredObject) {
		return new Mirror(mirroredObject);
	}

	/*
	 * Variables
	 */
	/**
	 * The mirrored object.
	 */
	protected Object mirrored;
	protected Map<String, InstanceField> allFields = new HashMap<String, InstanceField>();

	/*
	 * Constructors
	 */
	/**
	 * Builds a mirror over mirroredObject
	 * 
	 * @param mirroredObject
	 * subject of reflection
	 * @param isPriviliged
	 * True if the mirror is priviliged
	 */
	private Mirror(Object mirroredObject, boolean isPriviliged) {
		this.mirrored = mirroredObject;
		for (Field field : mirroredObject.getClass().getFields()) {
			allFields.put(field.getName(), new InstanceField(mirroredObject, field));
		}
		if (isPriviliged) {
			for (Field field : mirroredObject.getClass().getDeclaredFields()) {
				if (!allFields.containsKey(field.getName())) {
					field.setAccessible(true);
					allFields.put(field.getName(), new InstanceField(mirroredObject, field));
				}
			}
		}
	}

	/**
	 * Builds an unpriviliged mirror over mirroredObject
	 * 
	 * @param mirroredObject
	 * subject of reflection
	 */
	public Mirror(Object mirroredObject) {
		this(mirroredObject, false);
	}

	/*
	 * Public Methods
	 */
	/**
	 * Gets the value of fieldName
	 * 
	 * @param fieldName
	 * the name of the field
	 * @return the value of the field.
	 */
	public Object get(String fieldName) {
		return field(fieldName).value();
	}

	/**
	 * Fluent setter, that uses reflection to set a value on the mirrored object.
	 * 
	 * @param fieldName
	 * the name of the field
	 * @param value
	 * the new value for the field of obj being modified
	 * @return The mirror (fluent style)
	 */
	public Mirror set(String fieldName, Object value) {
		field(fieldName).set(value);
		return this;
	}

	/**
	 * Retrieves an instance field from the mirrored Object
	 * 
	 * @param fieldName
	 * The name of the field
	 * @return An instance field of the mirrored obejct and of the corresponding field
	 * name.
	 */
	public InstanceField field(String fieldName) {
		if (allFields.containsKey(fieldName)) {
			return allFields.get(fieldName);
		}
		throw new RuntimeReflectionException("Unknown field: " + fieldName);
	}

	public Object invoke(String methodName, Object... args) {
		try {
			return mirrored.getClass().getMethod(methodName).invoke(mirrored, args);
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
	}

}
