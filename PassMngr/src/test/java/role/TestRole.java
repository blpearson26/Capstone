package role;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import credential.Credential;

class TestRole {
	
	Role role = new Role();
	String roleName = "testRole";
	ArrayList<Credential> privileges = new ArrayList<Credential>();
	String GroupID = "12345";
	String RoleID = "54321";
	Credential c = new Credential("username", "password", "77777", "Mon, Nov 8", 
			"Mon, Nov 10", "asdf", "www.netflix.com", 1, "Netflix Login");

	@Test
	void testRole() {
		assertEquals("", role.getRoleName());
		assertTrue(role.getPrivileges().isEmpty());
		assertEquals("", role.getGroupID());
		assertEquals("", role.getRoleID());
	}

	@Test
	void testRoleStringArrayListOfCredential() {
		privileges.add(c);
		Role roleStrings = new Role(roleName, privileges);
		assertEquals(roleName, roleStrings.getRoleName());
		assertFalse(roleStrings.getPrivileges().isEmpty());
	}

	@Test
	void testGetSetRoleName() {
		role.setRoleName(roleName);
		assertEquals(roleName, role.getRoleName());
	}

	@Test
	void testGetSetPrivileges() {
		role.setPrivlages(privileges);
		assertEquals(privileges, role.getPrivileges());
	}

	@Test
	void testAddPrivilege() {
		role.addPrivilege(c);
		assertEquals(c, role.getPrivileges().get(0));
	}

	@Test
	void testGetSetGroupID() {
		role.setGroupID(GroupID);
		assertEquals(GroupID, role.getGroupID());
	}

	@Test
	void testGetSetRoleID() {
		role.setRoleID(RoleID);
		assertEquals(RoleID, role.getRoleID());
	}

}
