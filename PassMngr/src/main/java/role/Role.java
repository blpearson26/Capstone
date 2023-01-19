package role;

import java.util.ArrayList;
import java.util.UUID;

import credential.Credential;
import team.Team;

public class Role implements RoleInterface{
	private String roleName;
	private ArrayList<Credential> privileges;
	private String GroupID;
	private String RoleID;
	
	
	public Role() {
		this.roleName = "";
		this.privileges = new ArrayList<Credential>();
	}
	
	public Role(String rn, ArrayList<Credential> privileges) {
		this.roleName = rn;
		this.privileges = privileges;
	}
	
	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String getRoleName() {
		return this.roleName;
	}
	
	@Override
	public void setPrivlages(ArrayList<Credential> privileges) {
		this.privileges = privileges;
	}
	
	@Override
	public ArrayList<Credential> getPrivileges() {
		return this.privileges;
	}
	
	@Override
	public void addPrivilege(Credential credential) {
		this.privileges.add(credential);
	}

	@Override
	public void setGroupID(String GroupID) {
		this.GroupID = GroupID;
	}

	@Override
	public void setRoleID(String RoleID) {
		this.RoleID = RoleID;
	}

	@Override
	public String getGroupID() {
		return this.GroupID;
	}

	@Override
	public String getRoleID() {
		return this.RoleID;
	}

	@Override
	public String generateRoleID() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replace("_", "").replace("-", "");
	}

}
