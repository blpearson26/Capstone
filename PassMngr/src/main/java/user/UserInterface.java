package user;

import role.Role;

public interface UserInterface {
	public void setUsername(String username);
	public void setPassword(String password);
	public void setUserID(String userID);
	public void setRole(Role role);
	public void setTeamJoinedDate(String date);
	public String getUsername();
	public String getPassword();
	public String getUserID();
	public String generateID();
	public Role getRole();
	public String getTeamJoinedDate();
}
