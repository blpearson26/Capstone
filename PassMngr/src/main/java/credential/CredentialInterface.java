package credential;

public interface CredentialInterface {
	public void setUsername(String username);
	public void setPassword(String password);
	public void setUserID(String userID);
	public void setCreationTime(String creationTime);
	public void setModifiedTime(String modifiedTime);
	public void	setCreated(int bool);
	public void setURL(String URL);
	public void setNotes(String notes);
	public void setTitle(String Title);
	public String getUsername();
	public String getPassword();
	public String getUserID();
	public String getCreationTime();
	public String getModifiedTime();
	public int getCreated();
	public String getNotes();
	public String getURL();
	public String getTitle();
}
