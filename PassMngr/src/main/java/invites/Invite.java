package invites;

public class Invite implements InviteInterface{
	String TeamID;
	String Username;
	String UserID;
	
	
	public Invite() {
		TeamID = "";
		Username = "";
		UserID = "";
	}
	
	public Invite(String TeamID, String Username, String UserID) {
		this.TeamID = TeamID;
		this.Username = Username;
		this.UserID = UserID;
	}
	
	@Override
	public void setTeamID(String TeamID) {
		this.TeamID = TeamID;
	}

	@Override
	public void setUsername(String username) {
		this.Username = Username;
	}

	@Override
	public void setUserID(String userID) {
		this.UserID = userID;
	}

	@Override
	public String getTeamID() {
		return this.TeamID;
	}

	@Override
	public String getUsername() {
		return this.Username;
	}

	@Override
	public String getUserID() {
		return this.UserID;
	}

}
