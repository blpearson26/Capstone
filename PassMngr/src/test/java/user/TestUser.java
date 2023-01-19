package user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUser {
	
	User user = new User();
	String name = "Roger";
	String password = "password";
	String userID = "1234";

	@Test
	void testUser() {
		assertEquals("", user.username);
		assertEquals("", user.password);
		assertEquals("", user.userID);
	}

	@Test
	void testUserStringStringString() {
		User userParams = new User(name, password, userID);
		assertEquals(name, userParams.username);
		assertEquals(password, userParams.password);
		assertEquals(userID, userParams.userID);		
	}

	@Test
	void testSetUsername() {
		user.setUsername(name);
		assertEquals(name, user.username);
	}
	
	@Test
	void testSetPassword() {
		user.setPassword(password);
		assertEquals(password, user.password);
	}

	@Test
	void testGetUsername() {
		user.setUsername(name);
		assertEquals(name, user.getUsername());
	}
	
	@Test
	void testGetPassword() {
		user.setPassword(password);
		assertEquals(password, user.getPassword());
	}

	@Test
	void testSetUserID() {
		user.setUserID(userID);
		assertEquals(userID, user.userID);
	}

	@Test
	void testGetUserID() {
		user.setUserID(userID);
		assertEquals(userID, user.getUserID());
	}

	@Test
	void testGenerateID() {
		user.setUserID(userID);
		String newID = user.generateID();
		assertNotEquals(newID, user.getUserID());
	}
}
