package user;

import java.util.UUID;

import role.Role;

public class User implements UserInterface{
	String	username;
	String	password;
	String	userID;
	Role 	role;
	String	joinedDate;
	
	public User() {
		this.username = "";
		this.password = "";
		this.userID = "";
	}
	
	public User(String username, String password, String userID) {
		this.username = username;
		this.password = password;
		this.userID = userID;
	}
	
	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setUserID(String userID) {
		this.userID = userID;
		
	}

	@Override
	public String getUserID() {
		return this.userID;
	}

	@Override
	public String generateID() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replace("_", "").replace("-", "");
	}

	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public Role getRole() {
		return this.role;
	}

	@Override
	public void setTeamJoinedDate(String date) {
		this.joinedDate = date;
	}

	@Override
	public String getTeamJoinedDate() {
		return this.joinedDate;
	}

}
