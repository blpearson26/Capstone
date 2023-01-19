package credential;

public class Credential implements CredentialInterface{
	String username;
	String password;
	String userID;
	String creationTime;
	String modifiedTime;
	String notes;
	String URL;
	String Title;
	int created;
	
	public Credential() {
		username = "";
		password = "";
		userID = "";
		creationTime = "";
		modifiedTime = "";
		notes = "";
		URL = "";
		created = 0;
	}
	
	public Credential(String username, String password, String userID, String creationTime,
			String modifiedTime, String notes, String URL, int created, String Title) {
		this.username = username;
		this.password = password;
		this.userID = userID;
		this.creationTime = creationTime;
		this.modifiedTime = modifiedTime;
		this.notes = notes;
		this.URL = URL;
		this.created = created;
		this.Title = Title;
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
	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Override
	public void setCreated(int bool) {
		this.created = bool;
	}

	@Override
	public void setNotes(String notes) {
		this.notes = notes;
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
	public String getCreationTime() {
		return this.creationTime;
	}

	@Override
	public String getModifiedTime() {
		return this.modifiedTime;
	}

	@Override
	public int getCreated() {
		return this.created;
	}

	@Override
	public String getNotes() {
		return this.notes;
	}

	@Override
	public String getUserID() {
		return this.userID;
	}

	@Override
	public void setURL(String URL) {
		this.URL = URL;
	}

	@Override
	public String getURL() {
		return this.URL;
	}

	@Override
	public void setTitle(String Title) {
		this.Title = Title;
	}

	@Override
	public String getTitle() {
		return this.Title;
	}

}
