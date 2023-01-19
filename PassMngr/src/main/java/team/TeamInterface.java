package team;

import java.util.ArrayList;

import user.User;

public interface TeamInterface {
	public void setTeamName(String tn);
	public void addMember(User u);
	public void setMembers(ArrayList<User> m);
	public String getTeamName();
	public User getMember(int Index);
	public ArrayList<User> getMembers();
	public String generateID();
	public String getTeamID();
	public void setTeamID(String teamID);
}
