package database;

import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import credential.Credential;
import invites.Invite;
import role.Role;
import team.Team;
import user.User;

public class DBConnection {
	private static DBConnection DBC;
	private Connection 			dbCon;
	private String 				uri;
    private String keystoreFileLocation = "src\\main\\java\\database\\aes-keystore.jck";
    private String storePass = "mystorepass";
    private String alias = "jceksaes";
    private String keyPass = "mykeypass";
    
	private DBConnection() {
		uri = "jdbc:mysql://localhost/passwordmanager?useSSL=false&allowPublicKeyRetrieval=true";
		try {
			dbCon = DriverManager.getConnection(uri, "root", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBConnection GetConnection() {
		if (DBC == null) {
			MakeConnection();
		}
		return DBC;
	}
	
	public static DBConnection MakeConnection() {
		if (DBC != null) {
			return DBC;
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			DBC = new DBConnection();
			return DBC;
		}
	}
	
	public ArrayList<User> GetUsers() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM user_info");
			while (result.next()) {
				User u = new User(result.getString("username"), cipher.getDecryptedMessage(result.getString("password")), result.getString("ID"));
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public User GetUser(String username) {
		User u = new User();
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM user_info WHERE username = " + "\"" + username + "\"");
			if (result.next()) {
				u.setUsername(result.getString("username"));
				u.setPassword(cipher.getDecryptedMessage(result.getString("password")));
				u.setUserID(result.getString("ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public void AddUser(User user) {
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			Statement insert = dbCon.createStatement();
			String password = cipher.getEncryptedMessage(user.getPassword());
			insert.executeUpdate("INSERT INTO user_info VALUES (\"" + user.getUsername() + "\",\"" + password + "\",\"" + user.getUserID() + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AddTeam(Team team, User user) {
		try {
			Statement insert = dbCon.createStatement();
			insert.executeUpdate("INSERT INTO teams VALUES (\"" + team.getTeamID() + "\",\"" + team.getTeamName() + "\",\"" + user.getUserID() + "\");");
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			String strDate = formatter.format(date);
			insert.executeUpdate("INSERT INTO members VALUES (\"" + team.getTeamID() + "\",\"" + user.getUsername() + "\",\"" + user.getUserID() + "\",\"Owner\",\"" + team.getTeamName() + "\",\"" + strDate + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void AddCredential(Credential credential) {
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
	        String password = cipher.getEncryptedMessage(credential.getPassword());
			Statement insert = dbCon.createStatement();
			insert.executeUpdate("INSERT INTO credentials VALUES (\"" + credential.getUserID() + "\",\"" + credential.getUsername() + "\",\"" + password +
					"\",\"" + credential.getURL() + "\",\"" + credential.getNotes() + "\"," + credential.getCreated() + ",\"" + credential.getCreationTime() + "\",\"" + credential.getModifiedTime() + "\",\"" + credential.getTitle() + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Credential> GetCredentials(User user) {
		ArrayList<Credential> credentials = new ArrayList<Credential>();
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM credentials WHERE UserID = " + "\"" + user.getUserID() + "\"");
			while (result.next()) {
				String password = cipher.getDecryptedMessage(result.getString("Password"));
				Credential c = new Credential(result.getString("Username"), password, result.getString("UserID"), result.getString("CreationDate"), result.getString("ModifiedDate"), result.getString("Notes"), result.getString("URL"), result.getInt("Created"), result.getString("Title"));
				credentials.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return credentials;
	}
	
	public void UpdateCredential(Credential curr, Credential cred) {
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			String password = cipher.getEncryptedMessage(cred.getPassword());
			Statement update = dbCon.createStatement();
			update.executeUpdate("UPDATE credentials SET Title = \"" + cred.getTitle() + "\", Username = \"" + cred.getUsername() + "\", Password = \"" + password + "\", URL = \"" + cred.getURL() + "\", ModifiedDate = \"" + cred.getModifiedTime() + "\" WHERE UserID = \"" + curr.getUserID() + "\" AND Title = \"" + curr.getTitle() + "\";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void DeleteCredential(Credential cred) {
		try {
			Statement delete = dbCon.createStatement();
			delete.executeUpdate("DELETE FROM credentials WHERE UserID = \"" + cred.getUserID() + "\" AND Username = \"" + cred.getUsername() + "\";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Team> GetTeams(User user) {
		ArrayList<Team> teams = new ArrayList<Team>();
		try {
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM members WHERE UserID = " + "\"" + user.getUserID() + "\"");
			while (result.next()) {
				ArrayList<User> members = new ArrayList<User>();
				ResultSet result2 = dbCon.createStatement().executeQuery("SELECT * FROM members WHERE GroupID = " + "\"" + result.getString("GroupID") + "\"");
				while (result2.next()) {
					User u = new User();
					u.setUsername(result2.getString("Username"));
					u.setUserID(result2.getString("UserID"));
					members.add(u);
				}
				Team t = new Team(result.getString("GroupName"), members, result.getString("GroupID"));
				teams.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teams;
	}
	
	public ArrayList<User> getMembers(Team team) {
		ArrayList<User> members = new ArrayList<User>();
		try {
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM members WHERE GroupID = \"" + team.getTeamID() + "\";");
			while (result.next()) {
				User u = new User();
				u.setUsername(result.getString("Username"));
				u.setUserID(result.getString("UserID"));
				u.setTeamJoinedDate(result.getString("Joined"));
				Role r = new Role();
				r.setRoleName(result.getString("Role"));
				u.setRole(r);
				members.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return members;
	}
	
	public void addMember(User u, Team t) {
		try {
			Statement insert = dbCon.createStatement();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			String strDate = formatter.format(date);
			insert.executeUpdate("INSERT INTO members VALUES (\"" + t.getTeamID() + "\",\"" + u.getUsername() + "\",\"" + u.getUserID() + "\",\"None\",\"" + t.getTeamName() + "\",\"" + strDate + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMemberRole(String u, Team t, String role) {
		try {
			Statement update = dbCon.createStatement();
			update.executeUpdate("UPDATE members SET Role = \"" + role + "\" WHERE Username = \"" + u + "\" AND GroupID = \"" + t.getTeamID() + "\";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Role getMemberRole(Team team, User user) {
		Role r = new Role();
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM members WHERE GroupID = \"" + team.getTeamID() + "\" AND UserID = \"" + user.getUserID() + "\";");
			if (result.next()) {
				r.setRoleName(result.getString("Role"));
				ResultSet result2 = dbCon.createStatement().executeQuery("SELECT * FROM roles WHERE RoleName = \"" + r.getRoleName() + "\" AND GroupID = \"" + team.getTeamID() + "\";");
				while (result2.next()) {
					Credential c = new Credential();
					c.setUsername(cipher.getDecryptedMessage(result2.getString("Username")));
					c.setPassword(cipher.getDecryptedMessage(result2.getString("Password")));
					r.addPrivilege(c);
				}
				r.setGroupID(result.getString("GroupID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public ArrayList<String> getRoles(Team team) {
		ArrayList<String> rn = new ArrayList<String>();
		try {
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM roles WHERE GroupID = \"" + team.getTeamID() + "\";");
			while (result.next()) {
				rn.add(result.getString("RoleName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rn;
	}
	
	public ArrayList<String> getRolesFromRoleName(Team team, String roleName) {
		ArrayList<String> rn = new ArrayList<String>();
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM roles WHERE GroupID = \"" + team.getTeamID() + "\" AND RoleName = \"" + roleName + "\";");
			while (result.next()) {
				rn.add(cipher.getDecryptedMessage(result.getString("Username")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rn;
	}
	
	public ArrayList<Credential> getCredsFromRoleName(Team team, String roleName) {
		ArrayList<Credential> creds = new ArrayList<Credential>();
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM roles WHERE GroupID = \"" + team.getTeamID() + "\" AND RoleName = \"" + roleName + "\";");
			while (result.next()) {
				Credential c = new Credential();
				c.setUsername(cipher.getDecryptedMessage(result.getString("Username")));
				c.setPassword(cipher.getDecryptedMessage(result.getString("Password")));
				creds.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return creds;
	}
	
	public void DeleteAllRoles(String role) {
		try {
			Statement delete = dbCon.createStatement();
			delete.executeUpdate("DELETE FROM roles WHERE RoleName = \"" + role + "\";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void AddRole(Credential cred, Team team, Role role) {
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
	        String username = cipher.getEncryptedMessage(cred.getUsername());
	        String password = cipher.getEncryptedMessage(cred.getPassword());
			Statement insert = dbCon.createStatement();
			insert.executeUpdate("INSERT INTO roles VALUES (\"" + team.getTeamID() + "\",\"" + role.getRoleID() + "\",\"" + role.getRoleName() + "\",\"" + username + "\",\"" + password + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void AddInvite(User u, Team team) {
		try {
			Statement insert = dbCon.createStatement();
			insert.executeUpdate("INSERT INTO invites VALUES (\"" + team.getTeamID() + "\",\"" + u.getUserID() + "\",\"" + u.getUsername() + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Invite> GetInvites(User u) {
		ArrayList<Invite> invites = new ArrayList<Invite>();
		try  {
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM invites WHERE Username = \"" + u.getUsername() + "\";");
			while (result.next()) {
				Invite i = new Invite(result.getString("GroupID"), result.getString("Username"), result.getString("UserID"));
				invites.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invites;
	}
	
	public void DeleteInvite(User u, Team t) {
		try {
			Statement delete = dbCon.createStatement();
			delete.executeUpdate("DELETE FROM invites WHERE GROUPID = \"" + t.getTeamID() + "\" AND UserID = \"" + u.getUserID() + "\";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String GetTeamName(String TeamID) {
		String teamName = "";
		try {
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM teams WHERE GroupID = \"" + TeamID + "\";");
			if (result.next()) {
				teamName = result.getString("GroupName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teamName;
	}
	
	public String getPassword(User u, Credential c) {
		String pass = "";
		try {
			Key keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(keystoreFileLocation, storePass, alias, keyPass);
	        AESCipher cipher = new AESCipher(keyFromKeyStore);
			ResultSet result = dbCon.createStatement().executeQuery("Select * FROM credentials WHERE UserID = \"" + u.getUserID() + "\" AND Username = \"" + c.getUsername() + "\";");
			if (result.next()) {
				pass = cipher.getDecryptedMessage(result.getString("Password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	public void addAudit(User u, Team t, String username) {
		try {
			Statement update = dbCon.createStatement();
			String groupID = "";
			if (t == null) {
				groupID = "None";
			} else {
				groupID = t.getTeamID();
			}
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			String strDate = formatter.format(date);
			update.executeUpdate("INSERT INTO audit VALUES (\"" + groupID + "\",\"" + u.getUsername() + "\",\"" + username + "\",\"" + strDate + "\");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getAudits(User u) {
		ArrayList<String> audits = new ArrayList<String>();
		try {
			ResultSet result = dbCon.createStatement().executeQuery("SELECT * FROM audit;");
			while (result.next()) {
				if (result.getString("Username").equals(u.getUsername())) {
					String audit = "You accessed the credential with username \"" + result.getString("CredentialUN") + "\" at " + result.getString("AccessTime");
					audits.add(audit);
				} else {
					ResultSet result2 = dbCon.createStatement().executeQuery("SELECT * FROM teams WHERE GroupID = \"" + result.getString("GroupID") + "\";");
					if (result2.next()) {
						if (result2.getString("OwnerID").equals(u.getUserID())) {
							String audit = result.getString("Username") + " accessed the credential with username \"" + result.getString("CredentialUN") + "\" at " + result.getString("AccessTime");
							audits.add(audit);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audits;
	}
	
}
