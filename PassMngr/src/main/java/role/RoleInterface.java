package role;

import java.util.ArrayList;

import credential.Credential;

public interface RoleInterface {
	public void setRoleName(String rn);
	public void setPrivlages(ArrayList<Credential> privileges);
	public void setGroupID(String GroupID);
	public void setRoleID(String RoleID);
	public String getRoleName();
	public ArrayList<Credential> getPrivileges();
	public void addPrivilege(Credential credential);
	public String getGroupID();
	public String getRoleID();
	public String generateRoleID();
}
