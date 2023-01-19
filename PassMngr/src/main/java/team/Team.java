package team;

import java.util.ArrayList;
import java.util.UUID;

import user.User;

public class Team implements TeamInterface{
	private ArrayList<User> members;
	private String teamName;
	private String teamID;
	
	
	public Team() {
		this.teamName = "";
		this.members = new ArrayList<User>();
		this.teamID = "";
	}
	
	public Team(String tn, ArrayList<User> m, String teamID) {
		this.teamName = tn;
		this.members = m;
		this.teamID = teamID;
	}
	
	@Override
	public void setMembers(ArrayList<User> m) {
		this.members = m;
	}
	
	@Override
	public void addMember(User u) {
		this.members.add(u);
	}
	
	@Override
	public ArrayList<User> getMembers() {
		return this.members;
	}
	
	@Override
	public User getMember(int index) {
		return members.get(index);
	}

	@Override
	public void setTeamName(String tn) {
		this.teamName = tn;
	}

	@Override
	public String getTeamName() {
		return this.teamName;
	}
	
	@Override
	public String getTeamID() {
		return this.teamID;
	}
	
	@Override
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	
	@Override
	public String generateID() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replace("_", "").replace("-", "");
	}
	
}
