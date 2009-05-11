package org.fluentjava.reflection;

/**
 * Simple Mock classe used throughout the mirrors' unit tests.
 */
public class Mock extends MockFather {
	public String publicName;
	private String privateString;

	private static String ClassField = "classValue";

	public static String getValue() {
		return Mock.ClassField;
	}

	public static void setValue(String value) {
		Mock.ClassField = value;
	}

	public Mock(String name) {
		this.publicName = name;
	}

	public String getPrivateString() {
		return privateString;
	}

	public void setPrivateString(String privateString) {
		this.privateString = privateString;
	}

	public String aTimesn(int n) {
		StringBuilder ret = new StringBuilder();
		while (n-- > 0) {
			ret.append("a");
		}
		return ret.toString();
	}

	public String aTimesn() {
		return aTimesOne();
	}

	private String aTimesOne() {
		return aTimesn(1);
	}

	protected String getPrivateName() {
		return "Private> " + publicName;
	}
}
