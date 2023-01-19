package team;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import user.User;

class TestTeam {
	
	Team team = new Team();
	ArrayList<User> members = new ArrayList<User>();
	String teamName = "testTeam";
	String teamID = "12345";
	User user = new User("username", "password", "54321");

	

	@Test
	void testTeam() {
		assertEquals("", team.getTeamName());
		assertEquals("", team.getTeamID());
		assertTrue(team.getMembers().isEmpty());
	}

	@Test
	void testTeamStringArrayListOfUserString() {
		members.add(user);
		Team teamStrings = new Team(teamName, members, teamID);
		assertEquals(teamName, teamStrings.getTeamName());
		assertEquals(teamID, teamStrings.getTeamID());
		assertFalse(teamStrings.getMembers().isEmpty());
	}

	@Test
	void testGetSetMembers() {
		team.setMembers(members);
		assertEquals(members, team.getMembers());
	}

	@Test
	void testAddMember() {
		team.addMember(user);
		assertEquals(user, team.getMember(0));
	}

	@Test
	void testGetSetTeamName() {
		team.setTeamName(teamName);
		assertEquals(teamName, team.getTeamName());
	}

	@Test
	void testGetSetTeamID() {
		team.setTeamID(teamID);
		assertEquals(teamID, team.getTeamID());
	}


}
