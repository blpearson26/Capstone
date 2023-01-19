package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import credential.Credential;
import database.DBConnection;
import gui.InfoList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import role.Role;
import team.Team;
import user.User;

public class PasswordManagerController implements Initializable {

	// --- User ---
	private String username = "";
	private String password = "";
	public User currentUser = null;
	private boolean loggedIn = false;
	private ArrayList<Credential> credentials = null;
	private ArrayList<HBox> listGui;
	private boolean accessed = false;
	public Credential currentCredential;
	public Team currentTeam;
	private ArrayList<String> roleNames;
	private ArrayList<Credential> creds;
	private ArrayList<CheckBox> cbAccess;
	public String teamID;
	
	// --- Category Pane ---
	@FXML
	private Button 	ExpandButton;
	@FXML
	private Label 	UsernameLabel;
	@FXML
	private Button 	CredentialsButton;
	@FXML
	private Button 	TeamsButton;
	@FXML
	private Button 	InboxButton;
	@FXML
	private Button AuditButton;
	@FXML
	private Button LogOutButton;
	@FXML
	private TextField UsernameTextField;
	@FXML
	private TextField PasswordTextField;
	@FXML
	private Button LogInButton;
	@FXML
	private Button RegisterButton;
	@FXML
	private Text RegistrationText;
	
	// --- Top Bar ---
	@FXML
	private Button BackButton;
	@FXML
	private Button ForwardButton;
	@FXML
	public TextField SearchField;
	@FXML
	private Button NewCredentialButton;
	@FXML
	private Button MinimizeButton;
	@FXML
	private Button FullScreenButton;
	@FXML
	private Button CloseButton;
	@FXML
	private Button NewTeamButton;
	
	
	// --- List Pane ---
	@FXML
	private ScrollPane ListScrollPane;
	@FXML
	private Text SortText;
	@FXML
	private Button SortButton;
	@FXML 
	public VBox ListVBox;
	
	// --- Details Pane ---
	@FXML
	private Button EditButton;
	@FXML
	private Button DeleteButton;
	@FXML
	private ImageView LogoImage;
	@FXML 
	public Text WebTitle;
	@FXML
	private VBox InfoBox;
	@FXML
	public Text UsernameText;
	@FXML
	public Text PasswordText;
	@FXML
	public Label PasswordStrengthLabel;
	@FXML
	public ImageView PasswordStrengthIcon;
	@FXML
	private Button CopyUserButton;
	@FXML
	private Button CopyPassButton;
	@FXML
	private Button AccessCredentialButton;
	@FXML
	public Text WebsiteText;
	@FXML
	private TextArea NotesTextArea;
	@FXML
	public Label ModifiedLabel;
	@FXML
	public Label CreatedLabel;
	@FXML
	private Label WebsiteLabel;
	@FXML
	private Label NoteLabel;
	@FXML
	private Button SubmitEditButton;
	
	// --- Add Credentials ---
	@FXML
	private TextField CredentialNameTextField;
	@FXML
	private Text CredentialNameText;
	@FXML
	private TextField UsernameCredTextField;
	@FXML
	private Text UsernameCredText;
	@FXML
	private TextField PasswordCredTextField;
	@FXML
	private Text PasswordCredText;
	@FXML
	private TextField WebsiteCredTextField;
	@FXML
	private Text WebsiteCredText;
	@FXML
	private Button SubmitCredentialButton;
	@FXML
	private Text NewCredentialText;
	
	// --- Add New Team ---
	@FXML
	private Text TeamNameText;
	@FXML
	private TextField TeamNameTextField;
	@FXML
	private Button SubmitTeamButton;
	
	// --- Team View ---
	@FXML
	private ScrollPane MemberCredSP;
	@FXML
	public VBox MemberCredVBox;
	@FXML
	private ScrollPane MemberListSP;
	@FXML 
	public VBox MemberListVBox;
	@FXML
	private Text TeamUsernameText;
	@FXML
	private Text TeamPasswordText;
	@FXML
	private Text TeamAccessText;
	@FXML
	private Text TeamMemberNameText;
	@FXML
	private Text TeamRoleText;
	@FXML
	private Text TeamMemberSinceText;
	@FXML
	public Text TeamNameTitle;
	@FXML
	public Button AssignRoleButton;
	
	// --- Roles ---
	@FXML
	public Button RolesButton;
	@FXML
	public Button AddMemberButton;
	@FXML
	public ChoiceBox MemChoiceBox;
	@FXML
	public TextField RoleNameTextField;
	@FXML
	public Button AddRoleNameButton;
	@FXML
	public Button SubmitRoleButton;
	
	// --- Invite to team ---
	@FXML
	private Text InviteTeamText;
	@FXML
	private TextField InviteTeamTextField;
	@FXML
	private Button InviteButton;
	@FXML
	private Text InviteErrorText;
	
	// --- Invitation ---
	@FXML
	public Text InviteText;
	@FXML 
	public Text InviteTeamName;
	@FXML
	public ImageView GreenThing;
	@FXML
	public Text NumMembersText;
	@FXML
	public Button JoinTeamButton;
	@FXML
	public Button DeclineTeamButton;
	
	// --- Assign ---
	@FXML
	public Text AssignMemberText;
	@FXML
	public Text AssignRoleText;
	@FXML
	public Text AssignMainText;
	@FXML
	public Button SubmitAssignButton;
	@FXML
	public ChoiceBox AssignMemChoiceBox;
	@FXML
	public ChoiceBox AssignRoleChoiceBox;
	
	// --- Audit Log ---
	@FXML
	public Text AuditLogText;
	@FXML
	public TextArea AuditLogTextArea;
	@FXML
	public Button ExportAuditButton;
	
	public PasswordManagerController() {
		// nothing
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RegistrationText.setVisible(false);
		LogOutButton.setVisible(false);
		UsernameLabel.setVisible(false);
		ShowCredentialInputPanel(false);
		ShowDetailsPanel(false);
		ShowNewTeamPanel(false);
		ShowTeamView(false);
		ShowInviteView(false);
		ShowInvitation(false);
		ShowAssignView(false);
		ShowAuditLog(false);
		RoleNameTextField.setVisible(false);
		AddRoleNameButton.setVisible(false);
		MemChoiceBox.setVisible(false);
		SubmitRoleButton.setVisible(false);
		SubmitEditButton.setVisible(false);
		AddMemberButton.setVisible(false);
		AssignRoleButton.setVisible(false);
		
	}
	
	@FXML
	private void OnCloseClicked(MouseEvent e) {
		final Node source = (Node)e.getSource();
		final Stage stage = (Stage)source.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void OnMinimizeClicked(MouseEvent e) {
		final Node source = (Node)e.getSource();
		final Stage stage = (Stage)source.getScene().getWindow();
		stage.setIconified(true);
	}
	
	@FXML
	private void OnMaximizeClicked(MouseEvent e) {
		final Node source = (Node)e.getSource();
		final Stage stage = (Stage)source.getScene().getWindow();
		if (stage.isFullScreen()) {
			stage.setFullScreen(false);
		} else {
			stage.setFullScreen(true);
		}
	}
	
	@FXML
	private void OnLoginClicked(ActionEvent e) {
		username = UsernameTextField.getText();
		password = PasswordTextField.getText();
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		if (checkUserExists(user)) {
			if (validatePassword(user)) {
				// password is correct
				loggedIn = true;
				RegistrationText.setVisible(false);
				UsernameTextField.setVisible(false);
				PasswordTextField.setVisible(false);
				LogInButton.setVisible(false);
				RegisterButton.setVisible(false);
				LogOutButton.setVisible(true);
				UsernameLabel.setText(currentUser.getUsername());
				UsernameLabel.setVisible(true);
			} else {
				// password is incorrect
				RegistrationText.setText("Incorrect Password");
				RegistrationText.setVisible(true);
				UsernameTextField.clear();
				PasswordTextField.clear();
				username = "";
				password = "";
			}
		} else {
			// username does not exist
			RegistrationText.setText("Username not registered");
			RegistrationText.setVisible(true);
			UsernameTextField.clear();
			PasswordTextField.clear();
			username = "";
			password = "";
		}
	}
	
	@FXML
	private void OnRegisterClicked(ActionEvent e) {
		username = UsernameTextField.getText();
		password = PasswordTextField.getText();
		
		boolean containsNum = false;
		boolean containsAlpha = false;
		boolean containsSpcChar = false;
		boolean meetsMinLength = false;
		final int MIN_LENGTH = 8;
		char aceptedSpcChars[] = {'!', '@', '#', '$', '%', '^', '&', '*'};
		char usernameBanedChars[] = {' ', '(', ')', '"', '\'', '\\', '{', '}'};
		final int MIN_UN_LENGTH = 4;
		
		
		if(username.length() < MIN_UN_LENGTH) {
			RegistrationText.setText("Your Username is not long enough");
			RegistrationText.setVisible(true);
			UsernameTextField.clear();
			PasswordTextField.clear();
			return;
		}
		
		for(int k = 0; k < username.length(); k++) {
			for(int l = 0; l < usernameBanedChars.length; l++) {
				if(username.charAt(k) == usernameBanedChars[l]) {
					RegistrationText.setText("Invalid Username");
					RegistrationText.setVisible(true);
					UsernameTextField.clear();
					PasswordTextField.clear();
					return;
				}
			}
		}
		
		if(password.length() < MIN_LENGTH) {
			RegistrationText.setText("Your Password is not long enough");
			RegistrationText.setVisible(true);
			UsernameTextField.clear();
			PasswordTextField.clear();
			return;
		}
		
		for(int i = 0; i < password.length(); i++) {
			if(!containsNum) {
				if(Character.isDigit(password.charAt(i))) {
					containsNum = true;
				}
			}
			
			if(!containsAlpha) {
				if(Character.isLetter(password.charAt(i))) {
					containsAlpha = true;
				}
			}
			
			if(!containsSpcChar) {
				for(int j = 0; j < aceptedSpcChars.length; j++) {
					if(password.charAt(i) == aceptedSpcChars[j]) {
						containsSpcChar = true;
						break;
					}
				}
			}
		}
		
		if(!(containsNum && containsAlpha && containsSpcChar)) {
			RegistrationText.setText("Invalid Password");
			RegistrationText.setVisible(true);
			UsernameTextField.clear();
			PasswordTextField.clear();
			return;
		}
		
		
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		if (!checkUserExists(user)) {
			user.setUserID(user.generateID());
			DBConnection databaseConnection = DBConnection.MakeConnection();
			databaseConnection.AddUser(user);

			RegistrationText.setText("Registration complete");
			RegistrationText.setVisible(true);
			UsernameTextField.clear();
			PasswordTextField.clear();
			
		} else {
			RegistrationText.setText("Username taken");
			RegistrationText.setVisible(true);
			UsernameTextField.clear();
			PasswordTextField.clear();
			username = "";
			password = "";
		}
	}
	
	@FXML
	private void onUsernameTextFieldClicked(MouseEvent e) {
		if (RegistrationText.isVisible()) {
			UsernameTextField.clear();
			PasswordTextField.clear();
			RegistrationText.setVisible(false);
		}
	}
	
	@FXML
	private void onPasswordTextFieldClicked(MouseEvent e) {
		if (RegistrationText.isVisible()) {
			UsernameTextField.clear();
			PasswordTextField.clear();
			RegistrationText.setVisible(false);
		}
	}
	
	@FXML
	private void onLogOutClicked(ActionEvent e) {
		loggedIn = false;
		currentUser = null;
		username = "";
		password = "";
		
		// To be completed as more features are implemented
		UsernameTextField.clear();
		PasswordTextField.clear();
		UsernameTextField.setVisible(true);
		PasswordTextField.setVisible(true);
		LogInButton.setVisible(true);
		RegisterButton.setVisible(true);
		LogOutButton.setVisible(false);
		UsernameLabel.setText("");
		UsernameLabel.setVisible(false);
		ListVBox.getChildren().clear();
		ShowCredentialInputPanel(false);
		ShowDetailsPanel(false);
		ShowTeamView(false);
		ShowInviteView(false);
		ShowAssignView(false);
		ShowInvitation(false);
		ShowAuditLog(false);
		RoleNameTextField.setVisible(false);
		AddRoleNameButton.setVisible(false);
		MemChoiceBox.setVisible(false);
		SubmitRoleButton.setVisible(false);
		SubmitEditButton.setVisible(false);
		AddMemberButton.setVisible(false);
		
	}
	
	@FXML
	private void onNewCredentialClicked(ActionEvent e) {
		if (loggedIn) {
			ShowDetailsPanel(false);
			ShowNewTeamPanel(false);
			ShowInviteView(false);
			ShowCredentialInputPanel(true);
			ShowInvitation(false);
			ShowAssignView(false);
			ShowAuditLog(false);
		}
	}
	
	@FXML
	private void SubmitCredentialClicked(ActionEvent e) {
		if (loggedIn) {
			String credentialName = CredentialNameTextField.getText();
			String username = UsernameCredTextField.getText();
			String password = PasswordCredTextField.getText();
			String website = WebsiteCredTextField.getText();
			String webTitle = CredentialNameTextField.getText();
			
			if (credentialName != "" && username != "" && password != "" && website != "") {
				String userID = currentUser.getUserID();
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
				String strDate = formatter.format(date);
				Credential c = new Credential(username, password, userID, strDate, strDate, "nothing", website, 1, webTitle);
				
				DBConnection databaseConnection = DBConnection.MakeConnection();
				databaseConnection.AddCredential(c);
				ShowCredentialInputPanel(false);
			} else {
				NewCredentialText.setText("One or more invalid fields.");
				NewCredentialText.setVisible(true);
			}
			
			InfoList il = new InfoList(this, currentUser);
			il.loadUserCredentials();
			UpdateListColumn(il.GetCred(), 1);
		}
	}
	
	@FXML
	private void onCredentialsButtonClicked(ActionEvent e) {
		if (loggedIn) {
			SortText.setText("Sort Credentials");
			ListVBox.getChildren().clear();
			ShowDetailsPanel(false);
			ShowCredentialInputPanel(false);
			ShowInviteView(false);
			ShowInvitation(false);
			ShowAssignView(false);
			ShowInvitation(false);
			ShowTeamView(false);
			ShowAuditLog(false);
			InfoList il = new InfoList(this, currentUser);
			il.loadUserCredentials();
			UpdateListColumn(il.GetCred(), 1);
		}
	}
	
	@FXML
	private void onCopyUserButtonClicked(ActionEvent e) {
		StringSelection stringSelection = new StringSelection(UsernameText.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	@FXML
	private void onCopyPassButtonClicked(ActionEvent e) {
		if (accessed) {
			DBConnection databaseConnection = DBConnection.MakeConnection();
			ArrayList<User> users = databaseConnection.GetUsers();
			for (User u : users) {
				if (u.getUsername().equalsIgnoreCase(UsernameText.getText())) {
					StringSelection stringSelection = new StringSelection(u.getPassword());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
					break;
				}
			}
		}
	}
	
	@FXML
	private void onEditButtonClicked(ActionEvent e) {
		SubmitEditButton.setVisible(true);
		ShowDetailsPanel(false);
		ShowNewTeamPanel(false);
		ShowCredentialInputPanel(true);
		ShowInvitation(false);
		ShowAuditLog(false);
		ShowTeamView(false);
		ShowInviteView(false);
		CredentialNameTextField.setText(currentCredential.getTitle());
		UsernameCredTextField.setText(currentCredential.getUsername());
		PasswordCredTextField.setText(currentCredential.getPassword());
		WebsiteCredTextField.setText(currentCredential.getURL());
	}
	
	@FXML
	private void SubmitEditClicked(ActionEvent e) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		String strDate = formatter.format(date);
		Credential cred = new Credential();
		cred.setTitle(CredentialNameTextField.getText());
		cred.setUsername(UsernameCredTextField.getText());
		cred.setPassword(PasswordCredTextField.getText());
		cred.setURL(WebsiteCredTextField.getText());
		cred.setModifiedTime(strDate);
		DBConnection databaseConnection = DBConnection.MakeConnection();
		databaseConnection.UpdateCredential(currentCredential, cred);
		ShowCredentialInputPanel(false);
		SubmitEditButton.setVisible(false);
		InfoList il = new InfoList(this, currentUser);
		il.loadUserCredentials();
		UpdateListColumn(il.GetCred(), 1);
	}
	
	@FXML
	private void onDeleteButtonClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		databaseConnection.DeleteCredential(currentCredential);
		ShowDetailsPanel(false);
		InfoList il = new InfoList(this, currentUser);
		il.loadUserCredentials();
		UpdateListColumn(il.GetCred(), 1);
	}
	
	@FXML
	private void onNewTeamClicked(ActionEvent e) {
		if (loggedIn) {
			ListVBox.getChildren().clear();
			ShowNewTeamPanel(true);
			ShowDetailsPanel(false);
			ShowCredentialInputPanel(false);
			ShowInvitation(false);
			ShowInviteView(false);
			ShowAssignView(false);
			ShowAuditLog(false);
		}
	}
	
	@FXML
	private void onTeamsButtonClicked(ActionEvent e) {
		if (loggedIn) {
			ClearListColumn();
			SortText.setText("Sort Teams");
			ShowDetailsPanel(false);
			ShowCredentialInputPanel(false);
			ShowInviteView(false);
			ShowAssignView(false);
			ShowAuditLog(false);
			ShowInvitation(false);
			InfoList il = new InfoList(this, currentUser);
			il.loadUserTeams(currentUser);
			UpdateListColumn(il.GetTeams(), 2);
		}
	}
	
	@FXML
	private void SubmitTeamClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		Team team = new Team();
		team.setTeamName(TeamNameTextField.getText());
		team.setTeamID(team.generateID());
		databaseConnection.AddTeam(team, currentUser);
		ShowNewTeamPanel(false);
	}
	
	@FXML
	private void AddMemberButtonClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		if (databaseConnection.getMemberRole(currentTeam, currentUser).getRoleName().equals("Owner")) {
			ShowTeamView(false);
			ShowInviteView(true);
			ShowInvitation(false);
			ShowAssignView(false);
			ShowAssignView(false);
			ShowAuditLog(false);
		}
	}
	
	@FXML
	private void RolesButtonClicked(ActionEvent e) {
		cbAccess = new ArrayList<CheckBox>();
		MemberListSP.setVisible(false);
		TeamRoleText.setVisible(false);
		TeamMemberSinceText.setVisible(false);
		TeamMemberNameText.setVisible(false);
		TeamNameTitle.setVisible(true);
		MemChoiceBox.setVisible(true);
		RoleNameTextField.setVisible(true);
		AddRoleNameButton.setVisible(true);
		SubmitRoleButton.setVisible(true);
		
		DBConnection databaseConnection = DBConnection.MakeConnection();
		creds = databaseConnection.GetCredentials(currentUser);
		
		MemberCredVBox.getChildren().clear();
		int counter = 0;
		for (Credential c : creds) {
			HBox cred = new HBox();
			if (counter % 2 == 0) {
				cred.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
			}
			Label number = new Label(String.valueOf(++counter));
			number.setMinWidth(15);
			Label username = new Label(c.getUsername());
			username.setMinWidth(150);
			Label password = new Label(c.getPassword());
			password.setMinWidth(175);
			CheckBox select = new CheckBox();
			select.setMinWidth(30);
			cbAccess.add(select);
			cred.getChildren().addAll(number, username, password, select);
			MemberCredVBox.getChildren().add(cred);
		}
		
		roleNames = databaseConnection.getRoles(currentTeam);
		LinkedHashSet<String> hashSet = new LinkedHashSet<>(roleNames);
		ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
		for (String rn : listWithoutDuplicates) {
			MemChoiceBox.getItems().add(rn);
		}
		
	}
	
	@FXML
	private void AddRoleNameButtonClicked(ActionEvent e) {
		MemChoiceBox.getItems().add(RoleNameTextField.getText());
		RoleNameTextField.clear();
	}
	
	@FXML
	private void SubmitRoleButtonClicked(ActionEvent e) {
		String choice = (String) MemChoiceBox.getValue();
		
		DBConnection databaseConnection = DBConnection.MakeConnection();
		databaseConnection.DeleteAllRoles(choice);
		for (int i = 0; i < cbAccess.size(); i++) {
			if (cbAccess.get(i).isSelected()) {
				Role r = new Role();
				r.setRoleName(choice);
				r.setRoleID(r.generateRoleID());
				databaseConnection.AddRole(creds.get(i), currentTeam, r);
			}
		}
		
		MemberCredVBox.getChildren().clear();
		RoleNameTextField.setVisible(false);
		AddRoleNameButton.setVisible(false);
		MemChoiceBox.getItems().clear();
		MemChoiceBox.setVisible(false);
		SubmitRoleButton.setVisible(false);
		
		MemberListSP.setVisible(true);
		TeamRoleText.setVisible(true);
		TeamMemberSinceText.setVisible(true);
		TeamMemberNameText.setVisible(true);
		
		InfoList i = new InfoList(this, currentUser);
		i.updateTeamView(currentUser, currentTeam);
	}
	
	@FXML
	private void MemChoiceBoxClicked(MouseEvent e) {
		String choice = (String) MemChoiceBox.getValue();
		ArrayList<String> rn = new ArrayList<String>();
		
		DBConnection databaseConnection = DBConnection.MakeConnection();
		rn = databaseConnection.getRolesFromRoleName(currentTeam, choice);
		
		for (int i = 0; i < rn.size(); i++) {
			if (rn.get(i).equals(creds.get(i).getUsername())) {
				cbAccess.get(i).setSelected(true);
			}
		}
	}
	
	@FXML
	private void InviteButtonClicked(ActionEvent e) {
		String username = InviteTeamTextField.getText();
		DBConnection databaseConnection = DBConnection.MakeConnection();
		User u = databaseConnection.GetUser(username);
		if (username.equals(u.getUsername())) {
			databaseConnection.AddInvite(u, currentTeam);
			ShowInviteView(false);
		} else {
			InviteTeamTextField.clear();
			InviteErrorText.setVisible(true);
		}
	}
	
	@FXML
	private void InboxButtonClicked(ActionEvent e) {
		if (loggedIn) {
			ClearListColumn();
			SortText.setText("Sort Inbox");
			ShowDetailsPanel(false);
			ShowCredentialInputPanel(false);
			ShowInviteView(false);
			ShowTeamView(false);
			ShowAssignView(false);
			ShowAuditLog(false);
			InfoList il = new InfoList(this, currentUser);
			il.loadUserInbox(currentUser);
			UpdateListColumn(il.GetInvites(), 3);
		}
	}
	
	@FXML
	private void JoinTeamButtonClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		Team t = new Team();
		t.setTeamID(teamID);
		t.setTeamName(databaseConnection.GetTeamName(teamID));
		databaseConnection.addMember(currentUser, t);
		databaseConnection.DeleteInvite(currentUser, t);
		ShowInvitation(false);
		UpdatingList();
	}
	
	@FXML
	private void DeclineTeamButtonClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		Team t = new Team();
		t.setTeamID(teamID);
		t.setTeamName(databaseConnection.GetTeamName(teamID));
		databaseConnection.DeleteInvite(currentUser, t);
		ShowInvitation(false);
		UpdatingList();
	}
	
	@FXML
	private void AssignRoleButtonClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		if (databaseConnection.getMemberRole(currentTeam, currentUser).getRoleName().equals("Owner")) {
			ShowTeamView(false);
			ShowInviteView(false);
			ShowInvitation(false);
			ShowAuditLog(false);
			AssignMemChoiceBox.getItems().clear();
			AssignRoleChoiceBox.getItems().clear();
			ArrayList<User> members = databaseConnection.getMembers(currentTeam);
			for (User u : members) {
				AssignMemChoiceBox.getItems().add(u.getUsername());
			}
			ArrayList<String> roles = databaseConnection.getRoles(currentTeam);
			LinkedHashSet<String> hashSet = new LinkedHashSet<>(roles);
			ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
			for (String role : listWithoutDuplicates) {
				AssignRoleChoiceBox.getItems().add(role);
			}
			ShowAssignView(true);
		}
	}
	
	@FXML
	private void SubmitAssignButtonClicked(ActionEvent e) {
		String mem = (String)AssignMemChoiceBox.getValue();
		String role = (String)AssignRoleChoiceBox.getValue();
		DBConnection databaseConnection = DBConnection.MakeConnection();
		databaseConnection.updateMemberRole(mem, currentTeam, role);
		ShowAssignView(false);
		InfoList i = new InfoList(this, currentUser);
		i.updateTeamView(currentUser, currentTeam);
	}
	
	@FXML
	private void AccessCredentialButtonClicked(ActionEvent e) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		PasswordText.setText(databaseConnection.getPassword(currentUser, currentCredential));
		databaseConnection.addAudit(currentUser, currentTeam, UsernameText.getText());
	}
	
	@FXML
	private void ExportAuditButtonClicked(ActionEvent e) {
		Stage s = new Stage();
		FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = 
            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(s);
        
        if(file != null){
        	try {
                FileWriter fileWriter;
                 
                fileWriter = new FileWriter(file);
                fileWriter.write(AuditLogTextArea.getText());
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	@FXML
	private void AuditButtonClicked(ActionEvent e) {
		if (loggedIn) {
			ClearListColumn();
			ShowDetailsPanel(false);
			ShowNewTeamPanel(false);
			ShowCredentialInputPanel(false);
			ShowInvitation(false);
			ShowTeamView(false);
			ShowAuditLog(true);
			DBConnection databaseConnection = DBConnection.MakeConnection();
			ArrayList<String> audits = databaseConnection.getAudits(currentUser);
			AuditLogTextArea.clear();
			AuditLogTextArea.setWrapText(true);
			StringBuilder fieldContent = new StringBuilder();
			for (String audit : audits) {
				fieldContent.append(audit + "\n");
			}
			AuditLogTextArea.setText(fieldContent.toString());
		}
	}
	
	@FXML
	private void onSearchBarClicked(ActionEvent e) {
		if(loggedIn) {
			if(!( SearchField.getPromptText().isBlank() )){
				if(SearchField.getPromptText().equals("Search for Credentials")) {
					InfoList il = new InfoList(this, currentUser);
					il.loadUserCredentials();
					UpdateListColumn(il.GetCred(), 1);
				}else if(SearchField.getPromptText().equals("Search for Teams")) {
					InfoList il = new InfoList(this, currentUser);
					il.loadUserTeams(currentUser);
					UpdateListColumn(il.GetTeams(), 2);
				}
			}
		}
	}
	
	private void UpdatingList() {
		ClearListColumn();
		SortText.setText("Sort Teams");
		ShowDetailsPanel(false);
		ShowCredentialInputPanel(false);
		ShowInviteView(false);
		ShowAuditLog(false);
		InfoList il = new InfoList(this, currentUser);
		il.loadUserTeams(currentUser);
		UpdateListColumn(il.GetTeams(), 2);
	}
	
    private void UpdateListColumn(ArrayList<HBox> List, int type){
    	ClearListColumn();
    	switch (type) {
    		case 1:
    	    	for (HBox credential : List) {
    	    		ListVBox.getChildren().add(credential);
    	    	}
    	    	break;
    		case 2:
    	    	for (HBox team : List) {
    	    		ListVBox.getChildren().add(team);
    	    	}
    	    	break;
    		case 3:
    			for (HBox invite : List) {
    				ListVBox.getChildren().add(invite);
    			}
    			break;
    		default:
    			break;
    	}
    }
    
    private void ClearListColumn(){
    	ListVBox.getChildren().clear();
    }
	
	private boolean checkUserExists(User user) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		ArrayList<User> users = databaseConnection.GetUsers();
		
		for (User u : users) {
			if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validatePassword(User user) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		ArrayList<User> users = databaseConnection.GetUsers();
		
		for (User u : users) {
			if (u.getUsername().equals(user.getUsername())) {
				if (u.getPassword().equals(user.getPassword())) {
					currentUser = u;
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public void ShowAuditLog(boolean bool) {
		if (bool == true) {
			AuditLogText.setVisible(true);
			AuditLogTextArea.setVisible(true);
			ExportAuditButton.setVisible(true);
		} else {
			AuditLogText.setVisible(false);
			AuditLogTextArea.setVisible(false);
			AuditLogTextArea.clear();
			ExportAuditButton.setVisible(false);
		}
	}
	
	public void ShowAssignView(boolean bool) {
		if (bool == true) {
			AssignMemberText.setVisible(true);
			AssignRoleText.setVisible(true);
			AssignMainText.setVisible(true);
			SubmitAssignButton.setVisible(true);
			AssignMemChoiceBox.setVisible(true);
			AssignRoleChoiceBox.setVisible(true);
		} else {
			AssignMemberText.setVisible(false);
			AssignRoleText.setVisible(false);
			AssignMainText.setVisible(false);
			SubmitAssignButton.setVisible(false);
			AssignMemChoiceBox.setVisible(false);
			AssignRoleChoiceBox.setVisible(false);
		}
	}
	
	public void ShowInviteView(boolean bool) {
		if (bool == true) {
			InviteTeamText.setVisible(true);
			InviteTeamTextField.setVisible(true);
			InviteButton.setVisible(true);
			InviteErrorText.setVisible(false);
		} else {
			InviteTeamTextField.clear();
			InviteTeamText.setVisible(false);
			InviteTeamTextField.setVisible(false);
			InviteButton.setVisible(false);
			InviteErrorText.setVisible(false);
		}
	}
	
	public void ShowTeamView(boolean bool) {
		if (bool == true) {
			MemberCredSP.setVisible(true);
			MemberListSP.setVisible(true);
			TeamUsernameText.setVisible(true);
			TeamPasswordText.setVisible(true);
			TeamAccessText.setVisible(true);
			TeamMemberNameText.setVisible(true);
			TeamRoleText.setVisible(true);
			TeamMemberSinceText.setVisible(true);
			TeamNameTitle.setVisible(true);
		} else {
			MemberCredSP.setVisible(false);
			MemberListSP.setVisible(false);
			TeamUsernameText.setVisible(false);
			TeamPasswordText.setVisible(false);
			TeamAccessText.setVisible(false);
			TeamMemberNameText.setVisible(false);
			TeamRoleText.setVisible(false);
			TeamMemberSinceText.setVisible(false);
			TeamNameTitle.setVisible(false);
			RolesButton.setVisible(false);
			AddMemberButton.setVisible(false);
			AssignRoleButton.setVisible(false);
		}
	}
	
	public void ShowNewTeamPanel(boolean bool) {
		if (bool == true) {
			TeamNameText.setVisible(true);
			TeamNameTextField.setVisible(true);
			SubmitTeamButton.setVisible(true);
			TeamNameTextField.clear();
		} else {
			TeamNameText.setVisible(false);
			TeamNameTextField.setVisible(false);
			SubmitTeamButton.setVisible(false);
			TeamNameTextField.clear();
		}
	}
	
	public void ShowCredentialInputPanel(boolean bool) {
		if (bool == true) {
			CredentialNameTextField.clear();
			UsernameCredTextField.clear();
			PasswordCredTextField.clear();
			WebsiteCredTextField.clear();
			NewCredentialText.setText("");
			CredentialNameTextField.setVisible(true);
			CredentialNameText.setVisible(true);
			UsernameCredTextField.setVisible(true);
			UsernameCredText.setVisible(true);
			PasswordCredTextField.setVisible(true);
			PasswordCredText.setVisible(true);
			WebsiteCredTextField.setVisible(true);
			WebsiteCredText.setVisible(true);
			SubmitCredentialButton.setVisible(true);
			NewCredentialText.setVisible(true);
		} else {
			CredentialNameTextField.clear();
			UsernameCredTextField.clear();
			PasswordCredTextField.clear();
			WebsiteCredTextField.clear();
			NewCredentialText.setText("");
			CredentialNameTextField.setVisible(false);
			CredentialNameText.setVisible(false);
			UsernameCredTextField.setVisible(false);
			UsernameCredText.setVisible(false);
			PasswordCredTextField.setVisible(false);
			PasswordCredText.setVisible(false);
			WebsiteCredTextField.setVisible(false);
			WebsiteCredText.setVisible(false);
			SubmitCredentialButton.setVisible(false);
			NewCredentialText.setVisible(false);
		}
	}
	
	public void ShowInvitation(boolean bool) {
		if (bool == true) {
			InviteText.setVisible(true);
			InviteTeamName.setVisible(true);
			GreenThing.setVisible(true);
			NumMembersText.setVisible(true);
			JoinTeamButton.setVisible(true);
			DeclineTeamButton.setVisible(true);
			GreenThing.setVisible(true);
		} else {
			InviteText.setVisible(false);
			InviteTeamName.setVisible(false);
			GreenThing.setVisible(false);
			NumMembersText.setVisible(false);
			JoinTeamButton.setVisible(false);
			DeclineTeamButton.setVisible(false);	
			GreenThing.setVisible(false);
		}
	}
	
	public void ShowDetailsPanel(boolean bool) {
		if (bool == true) {
			EditButton.setVisible(true);
			DeleteButton.setVisible(true);
			LogoImage.setVisible(true);
			WebTitle.setVisible(true);
			InfoBox.setVisible(true);
			UsernameText.setVisible(true);
			PasswordText.setVisible(true);
			PasswordStrengthLabel.setVisible(true);
			PasswordStrengthIcon.setVisible(true);
			CopyUserButton.setVisible(true);
			CopyPassButton.setVisible(true);
			AccessCredentialButton.setVisible(true);
			WebsiteText.setVisible(true);
			NotesTextArea.setVisible(true);
			ModifiedLabel.setVisible(true);
			CreatedLabel.setVisible(true);
			WebsiteLabel.setVisible(true);
			NoteLabel.setVisible(true);
		} else {
			EditButton.setVisible(false);
			DeleteButton.setVisible(false);
			LogoImage.setVisible(false);
			WebTitle.setVisible(false);
			InfoBox.setVisible(false);
			UsernameText.setVisible(false);
			PasswordText.setVisible(false);
			PasswordStrengthLabel.setVisible(false);
			PasswordStrengthIcon.setVisible(false);
			CopyUserButton.setVisible(false);
			CopyPassButton.setVisible(false);
			AccessCredentialButton.setVisible(false);
			WebsiteText.setVisible(false);
			NotesTextArea.setVisible(false);
			ModifiedLabel.setVisible(false);
			CreatedLabel.setVisible(false);
			WebsiteLabel.setVisible(false);
			NoteLabel.setVisible(false);
		}
	}
	

}
