package org.fluentjava.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that provides reflective facilities over an object. Throughout the documentation,
 * we refer to the subject of reflection as mirrored object.
 */
public class Mirror {
	/*
	 * Variables
	 */
	protected Object mirrored;
	protected boolean privileged = false;
	protected Map<String, Field> allFields = new HashMap<String, Field>();

	/*
	 * Constructors
	 */
	/**
	 * Builds a mirror over mirroredObject
	 * @param mirroredObject subject of reflection
	 */
	public Mirror(Object mirroredObject) {
		this.mirrored = mirroredObject;
		for (Field field : mirroredObject.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			allFields.put(field.getName(), field);
		}
	}

	/*
	 * Public Methods
	 */
	/**
	 * Gets the value of fieldName
	 * @param fieldName
	 * the name of the field 
	 * @return
	 * the value of the field.
	 */
	public Object get(String fieldName) {
		try {
			return getField(fieldName).get(mirrored);
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}
	}

	/**
	 * Fluent setter, that uses reflection to set a value on the mirrored object.
	 * 
	 * @param fieldName
	 * the name of the field
	 * @param value
	 * the new value for the field of obj  being modified
	 * @return
	 * The mirror (fluent style)
	 */
	public Mirror set(String fieldName, Object value) {
		try {
			getField(fieldName).set(mirrored, value);
			return this;
		} catch (Exception e) {
			throw new RuntimeReflectionException(e);
		}

	}

	/**
	 * Makes the mirror "priviliged": able to access private fields and methods.
	 * @return
	 * The mirror (fluent style)
	 */
	public Mirror privileged() {
		privileged = true;
		return this;
	}

	/*
	 * Other Methods
	 */
	private Field getField(String fieldName) throws SecurityException, NoSuchFieldException {
		if (privileged) {
			return getPrivateField(fieldName);
		}
		return getPublicField(fieldName);
	}

	private Field getPrivateField(String fieldName) throws SecurityException, NoSuchFieldException {
		return allFields.get(fieldName);
	}

	private Field getPublicField(String fieldName) throws NoSuchFieldException {
		return mirrored.getClass().getField(fieldName);
	}

}
