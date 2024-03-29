package org.fluentjava.reflection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MirrorTest {

	private Mirror mirror;
	private Mock mock;
	private Mirror priviligedMirror;

	@Before
	public void setUp() {
		mock = new Mock("aname");
		mirror = new Mirror(mock);
		priviligedMirror = Mirror.priviligedMirror(mock);
		Mock.setValue("classValue");
	}

	@Test
	public void testGetFieldValue() {
		assertEquals("aname", mirror.get("publicName"));

	}

	@Test(expected = RuntimeReflectionException.class)
	public void testGetNonExisitingField() throws Exception {
		mirror.get("notaNAttribute");
	}

	@Test(expected = RuntimeReflectionException.class)
	public void testCannotAccessPrivateFields() throws Exception {
		mock.setPrivateString("private string value");
		mirror.get("privateString");
	}

	@Test
	public void testMirrorDoesSeeChangesInMockedObject() throws Exception {
		mock.publicName = "new public name";
		assertEquals("new public name", mirror.get("publicName"));
	}

	@Test(expected = RuntimeReflectionException.class)
	public void testOneMirrorsPriviligedDoesNotMakeAllMirrorsPriviliged()
			throws Exception {
		Mirror.priviligedMirror(mock);
		mock.setPrivateString("private string value");
		mirror.get("privateString");
	}

	@Test
	public void testGetInstanceField() throws Exception {
		assertEquals("aname", mirror.field("publicName").value());
	}

	@Test
	public void testSetInstanceField() throws Exception {
		mirror.field("publicName").set("new name, set by mirror");
		assertEquals("new name, set by mirror", mock.publicName);
	}

	@Test
	public void testSetsFieldsAsWell() throws Exception {
		priviligedMirror.set("privateString", "mock's private string, set by mirror").set(
				"publicName", "new name, set by mirror");
		assertEquals("mock's private string, set by mirror", mock.getPrivateString());
		assertEquals("new name, set by mirror", mock.publicName);
	}

	@Test
	public void testPriviledgedMirrorDoesAccessPrivateFields() throws Exception {
		mock.setPrivateString("private string value");
		assertEquals("private string value", priviligedMirror.get("privateString"));
	}

	@Test
	public void testInstanceFieldGetsMirrorsPrivileges() throws Exception {
		priviligedMirror.field("privateString").set(
				"mock's private string, set by mirror");
		assertEquals("mock's private string, set by mirror", mock.getPrivateString());
	}

	@Test(expected = RuntimeReflectionException.class)
	public void testCannotGetAFieldThatDoesNotExist() throws Exception {
		priviligedMirror.get("doesNotExist!");
	}

	@Test(expected = RuntimeReflectionException.class)
	public void testCannotGetPrivateFieldsOnParentOrAncestors() throws Exception {
		priviligedMirror.field("privateFieldOnParent");
	}

	@Test
	public void testPublicFieldsDefinedOnParentsAreAccessible() throws Exception {
		priviligedMirror.field("publicFieldOnParent").set("aValue");
		assertEquals("aValue", mock.publicFieldOnParent);
	}

	@Test
	public void testCanAcessStaticFields() throws Exception {
		priviligedMirror.field("ClassField").set("aValue");
		assertEquals("aValue", Mock.getValue());
	}
	
	@Test
	public void testInvoke() throws Exception {
		mirror.invoke("setPrivateString", "changed on test");
		assertEquals(mock.getPrivateString(), "changed on test");
	}

}
