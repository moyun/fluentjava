package org.fluentjava.reflection;

/**
 * Simple Mock parent.
 */
public class MockFather {
	private String privateFieldOnParent;
	
	public String publicFieldOnParent;
	

	public String getPrivateFieldOnParent() {
		return privateFieldOnParent;
	}

	public void setPrivateFieldOnParent(String privateFieldOnParent) {
		this.privateFieldOnParent = privateFieldOnParent;
	}
}
