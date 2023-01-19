package invites;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestInvites {
	
	Invite inv = new Invite();
	String TeamID = "123456";
	String Username = "Roger";
	String UserID = "654321";

	@Test
	void testInvite() {
		assertEquals("", inv.TeamID);
		assertEquals("", inv.Username);
		assertEquals("", inv.UserID);
	}

	@Test
	void testInviteStringStringString() {
		Invite invStrings = new Invite(TeamID, Username, UserID);
		assertEquals(TeamID, invStrings.TeamID);
		assertEquals(Username, invStrings.Username);
		assertEquals(UserID, invStrings.UserID);
	}

	@Test
	void testSetTeamID() {
		inv.setTeamID(TeamID);
		assertEquals(TeamID, inv.TeamID);
	}

	@Test
	void testSetUsername() {
		inv.setUsername(Username);
		assertEquals(Username, inv.Username);
	}

	@Test
	void testSetUserID() {
		inv.setUserID(UserID);
		assertEquals(UserID, inv.UserID);
	}

	@Test
	void testGetTeamID() {
		inv.setTeamID(TeamID);
		assertEquals(TeamID, inv.getTeamID());
	}

	@Test
	void testGetUsername() {
		inv.setUsername(Username);
		assertEquals(Username, inv.getUsername());
	}

	@Test
	void testGetUserID() {
		inv.setUserID(UserID);
		assertEquals(UserID, inv.getUserID());
	}

}
