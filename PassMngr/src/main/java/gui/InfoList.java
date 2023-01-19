package gui;

import java.lang.reflect.Member;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import credential.Credential;
import database.DBConnection;
import invites.Invite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import role.Role;
import team.Team;
import user.User;


public class InfoList {

	private ArrayList<Credential> credList;
	private ArrayList<Team> teamList;
	private ArrayList<Invite> inviteList;
	private ArrayList<HBox> credentials;
	private ArrayList<HBox> teams;
	private ArrayList<HBox> invites;
	private PasswordManagerController controller;
	private User user;
	private ArrayList<String> passwords;
	private ArrayList<String> usernames;
	private ArrayList<Label> passLabels;
	private ArrayList<Button> accessButtons;
	
	public InfoList(PasswordManagerController controller, User user) {
		this.controller = controller;
		this.user = user;
		credList = new ArrayList<Credential>();
		teamList = new ArrayList<Team>();
		credentials = new ArrayList<HBox>();
		teams = new ArrayList<HBox>();
		passwords = new ArrayList<String>();
		passLabels = new ArrayList<Label>();
		accessButtons = new ArrayList<Button>();
		inviteList = new ArrayList<Invite>();
		invites = new ArrayList<HBox>();
		usernames = new ArrayList<String>();
	}
	
	public ArrayList<Credential> GetCredList() {
		return this.credList;
	}
	
	public ArrayList<HBox> GetCred() {
		return this.credentials;
	}
	
	public ArrayList<Team> GetTeamList() {
		return this.teamList;
	}
	
	public ArrayList<HBox> GetTeams() {
		return this.teams;
	}
	
	public ArrayList<HBox> GetInvites() {
		return this.invites;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void loadUserCredentials() {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		credList = databaseConnection.GetCredentials(user);
		ArrayList<Credential> credListFiltered = new ArrayList<>();
		controller.SearchField.setPromptText("Search for Credentials");
		
		if(!(controller.SearchField.getText().equals(""))) {
			for(Credential c: credList) {
				if(c.getTitle().toLowerCase().contains( controller.SearchField.getText().toLowerCase() )) {
					credListFiltered.add(c);
				}	
			}
		}else {
			credListFiltered = credList;
		}
		
		for (Credential c : credListFiltered) {
			HBox credential = new HBox();
			Button cred = new Button(c.getTitle());	
			cred.setMinWidth(controller.ListVBox.getPrefWidth());
			
			// Event handling
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					controller.ShowCredentialInputPanel(false);
					controller.ShowDetailsPanel(false);
					controller.ShowNewTeamPanel(false);
					controller.ShowTeamView(false);
					
					controller.WebTitle.setText(c.getTitle());
					controller.UsernameText.setText(c.getUsername());
					controller.PasswordText.setText("●".repeat(c.getPassword().length()));
					controller.WebsiteText.setText(c.getURL());
					controller.ModifiedLabel.setText("Modified Time: " + c.getModifiedTime());
					controller.CreatedLabel.setText("Creation Time: " + c.getCreationTime());
					
					if (c.getPassword().length() > 12) {
						Image image = null;
						try {
							image = new Image(getClass().getResource("good.png").toURI().toString());
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
						controller.PasswordStrengthIcon.setImage(image);
						controller.PasswordStrengthLabel.setText("good");
					} else if (c.getPassword().length() > 6) {
						Image image = null;
						try {
							image = new Image(getClass().getResource("weak.png").toURI().toString());
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
						controller.PasswordStrengthIcon.setImage(image);
						controller.PasswordStrengthLabel.setText("weak");
					} else {
						Image image = null;
						try {
							image = new Image(getClass().getResource("bad.png").toURI().toString());
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
						controller.PasswordStrengthIcon.setImage(image);
						controller.PasswordStrengthLabel.setText("bad");
					}
					
					controller.currentCredential = c;
					controller.ShowDetailsPanel(true);
				} 
	        }; 
	        
	        // When button is pressed
	        cred.setOnAction(event);
			
			credential.getChildren().addAll(cred);
			credentials.add(credential);
		}
	}
	
	public void updateTeamView(User currentUser, Team currentTeam) {
		controller.MemberListVBox.getChildren().clear();
		controller.MemberCredVBox.getChildren().clear();
		passwords.clear();
		passLabels.clear();
		usernames.clear();
		controller.ShowCredentialInputPanel(false);
		controller.ShowDetailsPanel(false);
		controller.ShowNewTeamPanel(false);
		controller.ShowInviteView(false);
		controller.TeamNameTitle.setText(currentTeam.getTeamName());
		
		//top VBox
		ArrayList<User> members = new ArrayList<User>();
		DBConnection databaseConnection = DBConnection.MakeConnection();
		members = databaseConnection.getMembers(currentTeam);
		
		Collections.sort(members, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getUsername().compareToIgnoreCase(o2.getUsername());
			}	
		}); 
		int counter = 0;
		for (User m : members) {
			HBox h = new HBox();
			if (counter % 2 == 0) {
				h.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
			}
			if (m.getUsername().equals(currentUser.getUsername()) && m.getRole().getRoleName().equals("Owner")) {
				controller.RolesButton.setVisible(true);
				controller.AddMemberButton.setVisible(true);
				controller.AssignRoleButton.setVisible(true);
			}
			Label number = new Label(String.valueOf(++counter));
			number.setMinWidth(18);
			Label username = new Label(m.getUsername());
			username.setMinWidth(118);
			Label role = new Label(m.getRole().getRoleName());
			role.setMinWidth(95);
			Label joinedDate = new Label(m.getTeamJoinedDate());
			joinedDate.setMinWidth(100);
			h.getChildren().addAll(number, username, role, joinedDate);
			controller.MemberListVBox.getChildren().add(h);
		}
		
		//bottom VBox
		Role r = new Role();
		r = databaseConnection.getMemberRole(controller.currentTeam, controller.currentUser);
		if (r.getRoleName().equals("Owner")) {
			counter = 0;
			for (Credential c : databaseConnection.GetCredentials(controller.currentUser)) {
				HBox h = new HBox();
				if (counter % 2 == 0) {
					h.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
				}
				Label number = new Label(String.valueOf(++counter));
				number.setMinWidth(18);
				Label username = new Label(c.getUsername());
				usernames.add(c.getUsername());
				username.setMinWidth(150);
				passwords.add(c.getPassword());
				Label password = new Label("●".repeat(c.getPassword().length()));
				password.setMinWidth(150);
				passLabels.add(password);
				Button access = new Button("Access Credential " + String.valueOf(counter));
				EventHandler<ActionEvent> accessEvent = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String[] splitted = access.getText().split("\\s+");
						int credNum = Integer.parseInt(splitted[2]) - 1;
						passLabels.get(credNum).setText(passwords.get(credNum));
						DBConnection databaseConnection = DBConnection.MakeConnection();
						databaseConnection.addAudit(currentUser, currentTeam, usernames.get(credNum));
					}
				};
				access.setOnAction(accessEvent);
				h.getChildren().addAll(number, username, password, access);
				controller.MemberCredVBox.getChildren().add(h);
			}
		} else {
			counter = 0;
			for (Credential c : databaseConnection.getCredsFromRoleName(controller.currentTeam, r.getRoleName())) {
				HBox h = new HBox();
				if (counter % 2 == 0) {
					h.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
				}
				Label number = new Label(String.valueOf(++counter));
				number.setMinWidth(18);
				Label username = new Label(c.getUsername());
				usernames.add(c.getUsername());
				username.setMinWidth(150);
				passwords.add(c.getPassword());
				Label password = new Label("●".repeat(c.getPassword().length()));
				password.setMinWidth(150);
				passLabels.add(password);
				Button access = new Button("Access Credential " + String.valueOf(counter));
				EventHandler<ActionEvent> accessEvent = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String[] splitted = access.getText().split("\\s+");
						int credNum = Integer.parseInt(splitted[2]) - 1;
						passLabels.get(credNum).setText(passwords.get(credNum));
						databaseConnection.addAudit(currentUser, currentTeam, usernames.get(credNum));
					}
				};
				access.setOnAction(accessEvent);
				h.getChildren().addAll(number, username, password, access);
				controller.MemberCredVBox.getChildren().add(h);
			}
		}
		
		controller.ShowTeamView(true);
	}
	
	public void loadUserTeams(User currentUser) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		teamList = databaseConnection.GetTeams(user);
		ArrayList<Team> teamListFiltered = new ArrayList<Team>();
		controller.SearchField.setPromptText("Search for Teams");
		if(!(controller.SearchField.getText().equals(""))) {
			for(Team t: teamList) {
				if(t.getTeamName().toLowerCase().contains( controller.SearchField.getText().toLowerCase())) {
					teamListFiltered.add(t);
				}
			}
		} else {
			teamListFiltered = teamList;
		}
		
		for (Team t : teamListFiltered) {
			HBox team = new HBox();
			Button tm = new Button(t.getTeamName());	
			tm.setMinWidth(controller.ListVBox.getPrefWidth());
			
			// add button functionality
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.currentTeam = t;
					controller.MemberListVBox.getChildren().clear();
					controller.MemberCredVBox.getChildren().clear();
					passwords.clear();
					usernames.clear();
					passLabels.clear();
					controller.ShowCredentialInputPanel(false);
					controller.ShowDetailsPanel(false);
					controller.ShowNewTeamPanel(false);
					controller.ShowInviteView(false);
					controller.TeamNameTitle.setText(t.getTeamName());
					
					//top VBox
					ArrayList<User> members = new ArrayList<User>();
					DBConnection databaseConnection = DBConnection.MakeConnection();
					members = databaseConnection.getMembers(t);
					
					Collections.sort(members, new Comparator<User>() {
						@Override
						public int compare(User o1, User o2) {
							return o1.getUsername().compareToIgnoreCase(o2.getUsername());
						}	
					}); 
					int counter = 0;
					for (User m : members) {
						HBox h = new HBox();
						if (counter % 2 == 0) {
							h.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
						}
						if (m.getUsername().equals(currentUser.getUsername()) && m.getRole().getRoleName().equals("Owner")) {
							controller.RolesButton.setVisible(true);
							controller.AddMemberButton.setVisible(true);
							controller.AssignRoleButton.setVisible(true);
						}
						Label number = new Label(String.valueOf(++counter));
						number.setMinWidth(18);
						Label username = new Label(m.getUsername());
						username.setMinWidth(118);
						Label role = new Label(m.getRole().getRoleName());
						role.setMinWidth(95);
						Label joinedDate = new Label(m.getTeamJoinedDate());
						joinedDate.setMinWidth(100);
						h.getChildren().addAll(number, username, role, joinedDate);
						controller.MemberListVBox.getChildren().add(h);
					}
					
					//bottom VBox
					Role r = new Role();
					r = databaseConnection.getMemberRole(controller.currentTeam, controller.currentUser);
					if (r.getRoleName().equals("Owner")) {
						counter = 0;
						for (Credential c : databaseConnection.GetCredentials(controller.currentUser)) {
							HBox h = new HBox();
							if (counter % 2 == 0) {
								h.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
							}
							Label number = new Label(String.valueOf(++counter));
							number.setMinWidth(18);
							Label username = new Label(c.getUsername());
							usernames.add(c.getUsername());
							username.setMinWidth(150);
							passwords.add(c.getPassword());
							Label password = new Label("●".repeat(c.getPassword().length()));
							password.setMinWidth(150);
							passLabels.add(password);
							Button access = new Button("Access Credential " + String.valueOf(counter));
							EventHandler<ActionEvent> accessEvent = new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									String[] splitted = access.getText().split("\\s+");
									int credNum = Integer.parseInt(splitted[2]) - 1;
									passLabels.get(credNum).setText(passwords.get(credNum));
									databaseConnection.addAudit(currentUser, controller.currentTeam, usernames.get(credNum));
								}
							};
							access.setOnAction(accessEvent);
							h.getChildren().addAll(number, username, password, access);
							controller.MemberCredVBox.getChildren().add(h);
						}
					} else {
						counter = 0;
						for (Credential c : databaseConnection.getCredsFromRoleName(controller.currentTeam, r.getRoleName())) {
							HBox h = new HBox();
							if (counter % 2 == 0) {
								h.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
							}
							Label number = new Label(String.valueOf(++counter));
							number.setMinWidth(18);
							Label username = new Label(c.getUsername());
							usernames.add(c.getUsername());
							username.setMinWidth(150);
							passwords.add(c.getPassword());
							Label password = new Label("●".repeat(c.getPassword().length()));
							password.setMinWidth(150);
							passLabels.add(password);
							Button access = new Button("Access Credential " + String.valueOf(counter));
							EventHandler<ActionEvent> accessEvent = new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									String[] splitted = access.getText().split("\\s+");
									int credNum = Integer.parseInt(splitted[2]) - 1;
									passLabels.get(credNum).setText(passwords.get(credNum));
									databaseConnection.addAudit(currentUser, controller.currentTeam, usernames.get(credNum));
								}
							};
							access.setOnAction(accessEvent);
							h.getChildren().addAll(number, username, password, access);
							controller.MemberCredVBox.getChildren().add(h);
						}
					}
					
					controller.ShowTeamView(true);
				}
			};
			
			tm.setOnAction(event);
			team.getChildren().addAll(tm);
			teams.add(team);
		}
	}
	
	public void loadUserInbox(User user) {
		DBConnection databaseConnection = DBConnection.MakeConnection();
		inviteList = databaseConnection.GetInvites(user);
		
		for (Invite i : inviteList) {
			HBox invite = new HBox();
			Button inv = new Button("Invitation to " + databaseConnection.GetTeamName(i.getTeamID()));	
			inv.setMinWidth(controller.ListVBox.getPrefWidth());
			controller.teamID = i.getTeamID();
			
			// Event handling
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					controller.InviteTeamName.setText(databaseConnection.GetTeamName(i.getTeamID()));
					Team t = new Team();
					t.setTeamID(i.getTeamID());
					ArrayList<User> members = databaseConnection.getMembers(t);
					controller.NumMembersText.setText(String.valueOf(members.size()) + " Member(s)");
					controller.ShowInvitation(true);
				} 
	        }; 
	        
	        // When button is pressed
	        inv.setOnAction(event);
			
			invite.getChildren().addAll(inv);
			invites.add(invite);
		}
		
	}
	
}
