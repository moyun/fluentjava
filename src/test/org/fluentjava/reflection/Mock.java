package org.fluentjava.reflection;

/**
 * Simple Mock classe used throughout the mirrors' unit tests.
 */
public class Mock {
	public String publicName;
	private String privateString;

	public Mock(String name) {
		this.publicName = name;
	}

	public String getPrivateString() {
		return privateString;
	}

	public void setPrivateString(String privateString) {
		this.privateString = privateString;
	}
}
