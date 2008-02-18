package org.fluentjava.reflection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MirrorTest {

	private Mirror mirror;
	private Mock mock;


	@Before
	public void setUp() {
		mock = new Mock("aname");
		mirror = new Mirror(mock);
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
	public void testNeedsPriviledModeToGetPrivateFields() throws Exception {
		mock.setPrivateString("private string value");
		mirror.get("privateString");
	}
	

	@Test
	public void testMirrorDoesSeeChangesInMockedObject() throws Exception {
		mock.publicName = "new public name";
		assertEquals("new public name", mirror.get("publicName"));
	}
	
	@Test
	public void testPriviledgedMirrorDoesAccessPrivateFields() throws Exception {
		mirror.privileged();
		mock.setPrivateString("private string value");
		assertEquals("private string value", mirror.get("privateString"));
	}
	
	@Test(expected = RuntimeReflectionException.class)
	public void testOneMirrorsPriviligedDoesNotMakeAllMirrorsPriviliged() throws Exception {
		mirror.privileged();
		Mirror alternateMirror = new Mirror(mock);
		mock.setPrivateString("private string value");
		alternateMirror.get("privateString");
	}
	
	@Test
	public void testSetsFieldsAsWell() throws Exception {
		mirror.privileged()
			.set("privateString", "mock's private string, set by mirror")
			.set("publicName", "new name, set by mirror");
		assertEquals("mock's private string, set by mirror", mock.getPrivateString());
		assertEquals("new name, set by mirror", mock.publicName);
	}

	
	
	private class Mock {
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

}
