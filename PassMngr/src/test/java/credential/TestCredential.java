package credential;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestCredential {
	Credential cred = new Credential();
	String username = "roger231@yahho.com";
	String password = "p4ssw0rd123";
	String userID = "12c1dd75002a4b8ca2e25af6e22e5f6c";
	String creationTime = "Mon, Nov 14, 2022";
	String modifiedTime = "Mon, Nov 14, 2022";
	String notes = "asdf";
	String URL = "www.yahoo.com";
	String Title = "Email Login";
	int created = 1;

	@Test
	void testCredential() {
		assertEquals("", cred.username);
		assertEquals("", cred.password);
		assertEquals("", cred.userID);
		assertEquals("", cred.creationTime);
		assertEquals("", cred.modifiedTime);
		assertEquals("", cred.notes);
		assertEquals("", cred.URL);
		assertEquals(0, cred.created);
	}

	@Test
	void testCredentialStringStringStringStringStringStringStringIntString() {
		Credential credStrings = new Credential(username, password, userID, creationTime, 
				modifiedTime, notes, URL, created, Title);
		
		assertEquals(username, credStrings.username);
		assertEquals(password, credStrings.password);
		assertEquals(userID, credStrings.userID);
		assertEquals(creationTime, credStrings.creationTime);
		assertEquals(modifiedTime, credStrings.modifiedTime);
		assertEquals(notes, credStrings.notes);
		assertEquals(URL, credStrings.URL);
		assertEquals(created, credStrings.created);
		assertEquals(Title, credStrings.Title);
		
	}

	@Test
	void testSetUsername() {
		cred.setUsername(username);
		assertEquals(username, cred.username);
	}

	@Test
	void testSetPassword() {
		cred.setPassword(password);
		assertEquals(password, cred.password);
	}

	@Test
	void testSetUserID() {
		cred.setUserID(userID);
		assertEquals(userID, cred.userID);
	}

	@Test
	void testSetCreationTime() {
		cred.setCreationTime(creationTime);
		assertEquals(creationTime, cred.creationTime);
	}

	@Test
	void testSetModifiedTime() {
		cred.setModifiedTime(modifiedTime);
		assertEquals(modifiedTime, cred.modifiedTime);
	}

	@Test
	void testSetCreated() {
		cred.setCreated(created);
		assertEquals(created, cred.created);
	}

	@Test
	void testSetNotes() {
		cred.setNotes(notes);
		assertEquals(notes, cred.notes);
	}

	@Test
	void testGetUsername() {
		cred.setUsername(username);
		assertEquals(username, cred.getUsername());
	}

	@Test
	void testGetPassword() {
		cred.setPassword(password);
		assertEquals(password, cred.getPassword());
	}

	@Test
	void testGetCreationTime() {
		cred.setCreationTime(creationTime);
		assertEquals(creationTime, cred.getCreationTime());
	}

	@Test
	void testGetModifiedTime() {
		cred.setModifiedTime(modifiedTime);
		assertEquals(modifiedTime, cred.getModifiedTime());
	}

	@Test
	void testGetCreated() {
		cred.setCreated(created);
		assertEquals(created, cred.getCreated());
	}

	@Test
	void testGetNotes() {
		cred.setNotes(notes);
		assertEquals(notes, cred.getNotes());
	}

	@Test
	void testGetUserID() {
		cred.setUserID(userID);
		assertEquals(userID, cred.getUserID());
	}

	@Test
	void testSetURL() {
		cred.setURL(URL);
		assertEquals(URL, cred.URL);
	}

	@Test
	void testGetURL() {
		cred.setURL(URL);
		assertEquals(URL, cred.getURL());
	}

	@Test
	void testSetTitle() {
		cred.setTitle(Title);
		assertEquals(Title, cred.Title);
	}

	@Test
	void testGetTitle() {
		cred.setTitle(Title);
		assertEquals(Title, cred.getTitle());
	}

}
