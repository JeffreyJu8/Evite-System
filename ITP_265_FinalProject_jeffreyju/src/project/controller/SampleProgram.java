package project.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.party.Envelope;
import model.party.NormEvite;
import model.party.Party;
import model.party.PartyType;
import model.party.PremEvite;
import model.user.NormUser;
import model.user.PremUser;
import model.user.User;

public class SampleProgram {
	private UI ui;
	private Helper bff;
	private NormUser user;
	private Map<String, NormUser> userData;
	private Map<String, NormEvite> userParty;
	
	public static final String EVITE_FILE = "src/model/party/userParty.tsv";
	public static final String USER_FILE = "src/model/user/userInfo.tsv";
	// do I create a file for evites?
	
	public SampleProgram() {
		this.bff = new Helper();
		ui = new UIConsole();
		userData = new HashMap<>();
		userParty = new HashMap<>();
		user = null; // null is the default because nobody is logged in
		userData = LoadUserData.readUsersFromFile(USER_FILE);
		userParty = LoadUserData.readUserParty(EVITE_FILE, userData);
	}
	
	
	public void run() {
		ui.print("welcome to Evite where planning parties are fun");
		boolean quit = false;
		while(!quit) {
			Menu userOption = null;
			if(ui instanceof UIConsole) {
				Menu.printMenuOptions();
				int userChoice = ui.inputInt(">", 1, Menu.getNumOptions() );
				ui.print("You chose " + Menu.getOption(userChoice));
				userOption = Menu.getOption(userChoice);
			}
			else { //pop up option
				 ui.print("Invalid Option");
			}
			
			// switch statement for the menu enum
			switch(userOption) {
			case LOGIN: login(); break;
			case LOGOUT: logout(); break;
			case REGISTRATION: registerUser(null); break;
			case CREATE_EVITE: createEvite(); break;
			case VIEW_EXISTING_EVITE: existingEvite(); break;
			case QUIT:  quit = true; saveData(); break;
	
			}
			
		}
	}
	
	// This code is largely based on code did in class written by Kendra Walther
	// this method saves the user data to a file
	private void saveData() {
		ui.print("Saving data to file before quitting " + USER_FILE);

		try(FileOutputStream fos = new FileOutputStream(USER_FILE);
				PrintWriter pw = new PrintWriter(fos)){
			// save all the user data
			pw.println("userType\temail\tname\tpassword "); // print header
			for(User u: userData.values()) {
				//userType\temail\tname\tpassword 
				//TODO: maybe extra data
				pw.println(u.toFileString());
			}
		} catch (FileNotFoundException e) {
			// print a message
			System.err.println("Error writing to file: " + e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try-with-resource autosaves for us! yay!

	}
	
	// this method saves the party data to a file
	private void savePartyData() {
		ui.print("Saving data to file before quitting " + EVITE_FILE);

		try(FileOutputStream fos = new FileOutputStream(EVITE_FILE);
				PrintWriter pw = new PrintWriter(fos)){
			// save all the user data
			pw.println("PartyType\tPartyTheme\tPartyLocation\tPartyDate\tHost\tguestList "); // format of header
			for(Party p: userParty.values()) {
				//TODO: maybe extra data
				pw.println(p.toFileString());
			}
		} catch (FileNotFoundException e) {
			// print a message
			System.err.println("Error writing to file: " + e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try-with-resource autosaves for us! yay!

	}
	
	
	
	private void login() {
		if(user == null) { //nobody is logged in
			String userEmail = ui.inputWord("Enter email: ");
			if(userData.containsKey(userEmail)) {
				//case 1: user is in system, and can log in
				boolean success = login(userEmail); // check credentials
				if(success) {
					user = userData.get(userEmail);
					ui.print("Welcome back, " + user.getName());
				}
				else {
					ui.print("Sorry, wrong email or password");
				}
			}
			else {  //case 2: user is not registered yet
				//TODO: ask if they want to create account
				ui.print("Looks like you don't have an account with us. We are registering you right now");
				registerUser(userEmail);
				ui.print("Thanks for creating an account" );
			}
		}
		else {
			ui.print("Sorry, someone else is already logged in.");
		}
	}
	
	
	
	
	private void registerUser(String email) {
		NormUser u = null;
		String userEmail = ui.inputLine("Please enter your email: ");
		ui.print("Creating an account for email: " + userEmail);
		String pword = ui.inputLine("Enter password: " );
		//TODO: verify password by typing again
		String name = ui.inputLine("Enter your name: ");

		//what kind of User is this? 
		String type = ui.inputWord("What kind of user (NormUser, PremUser)", 
				"NormUser", "PremUser");
		if(type.equalsIgnoreCase("NormUser")) { //if they chose NormUser
			u = new NormUser(userEmail, name, pword); //makes the user
		}
		else if(type.equalsIgnoreCase("PremUser")) {
			boolean prem = ui.inputYesNo("Do you want to pay the price of $4.99 for a premium account? ");
			if(prem ==true) {
			u = new PremUser (userEmail, name, pword); //makes the user subclass
		}
			else if(prem == false) { // if the user does not want to pay for subscription
				ui.print("In that case, we are making you a normal user");
				u = new NormUser(userEmail, name, pword);
			}
			
		}

		userData.put(userEmail, u); // add to map
		user = u;
	}
	
	private boolean login(String email) {
		Map<String, NormUser> map = LoadUserData.readUsersFromFile(SampleProgram.USER_FILE);
		//System.out.println(map);
		User u = map.get(email);
		String pwordAttempt = ui.inputLine("Enter password:");
		boolean matched = false;
		//TODO allow 3 attempts on password
		if(u.checkPassword(pwordAttempt)) {
			matched = true;
		}
		return matched;
	}
	
	
	private void logout() {
		if(user == null) {
			ui.print("There is no current user to logout");
		}
		else {
			ui.print("Thanks, " + user.getName() + " see you again soon");
			user = null; 
		}
	}
	
	
	private void createEvite() {
		Party p = null;
		boolean isLoggedin = false;
		boolean quit = false;
		
		if(user == null) {  // if nobody is logged in 
			isLoggedin = false;
			boolean loggingIn = ui.inputYesNo("You are not loggedin. Would you like to? (Y/N)"); // ask user to login
			if(loggingIn == true) {
				login();
			}
			else if(loggingIn == false) {
				System.out.println("Goodbye");
				Menu.printMenuOptions();
			}
		}
		
		
		while(!quit) {
			PartyType userParty = null;
			if(ui instanceof UIConsole) {
				PartyType.printPartyOptions();
				int userChoice = ui.inputInt(">", 1, PartyType.getNumOptions() );
				ui.print("You chose " + PartyType.getOption(userChoice));
				userParty = PartyType.getOption(userChoice);
			}
			else { //pop up option
				 ui.print("Invalid Option");
			}
			
			switch(userParty) { // need to make a list of people inviting
			case BIRTHDAY: makeEvites(userParty, user); break;
			case WEDDING: makeEvites(userParty, user); break;
			case HOLIDAYS: makeEvites(userParty, user); break;
			case CEREMONIES: makeEvites(userParty, user); break;
			case SPORTS: makeEvites(userParty, user); break;
			case QUIT:  quit = true; savePartyData(); break;
			}
		
		}
		
		
	}
	
	public void chooseColor() {
		String color = ui.inputWord("What color do you want? (red, blue, yellow, green) ", "red", "blue", "yellow", "green");
	}
	
	
	public void chooseStamp() {
		String stamp = ui.inputWord("What kind of stamp do you want? (clown, beach, trainStation)", 
				"clown", "beach", "trainStation");
	}
	
	public void chooseLiner() {
		String liner = ui.inputWord("What kind of liners do you want? (starPattern, linePatter, shapes)", 
				"starPattern", "linePattern", "shapes");
	}
	
	public void chooseMessage() {
		String message = ui.inputLine("What message do you want on your Envelope? ");
	}
	
	
	public Envelope makeEnvelope() {
		Envelope userEnvelope = null;
		boolean quit = false;
		while(!quit) {
			
			if(ui instanceof UIConsole) {
				Envelope.printEnvelopeOptions();
				int userChoice = ui.inputInt(">", 1, Envelope.getNumOptions() );
				ui.print("You chose " + Envelope.getOption(userChoice));
				userEnvelope = Envelope.getOption(userChoice);
			}
			else { //pop up option
				 ui.print("Invalid Option");
			}
			
			switch(userEnvelope) { // need to make a list of people inviting
			case COLOR: chooseColor(); break;
			case STAMPS: chooseStamp(); break;
			case LINERS: chooseLiner(); break;
			case MESSAGE: chooseMessage(); break;
			case QUIT:  quit = true; break;
				}
			}
		return userEnvelope;
	}
	
	
	
	
	public void makeEvites(PartyType partyType, NormUser u) {
		// this method asks for the information on the Evite
		
		ArrayList<String> guestEmail = new ArrayList<>();
		int count = 0;
		boolean quit = false;
		NormEvite p = null;
		String eviteType = ui.inputWord("What kind of Evite? (NormEvite, PremEvite)", 
				"NormEvite", "PremEvite");
		if(eviteType.equalsIgnoreCase("normevite")) {
			String partyTheme = ui.inputWord("What is the theme of this party? ");
			String partyLoc = ui.inputLine("What is the location of this party? ");
			LocalDate getDate = ui.getDate("What is the date of this party? ");
			String host = ui.inputWord("Who is the host");
			while(count < NormEvite.getGuestLimit() && !quit) {
				boolean quitOption = ui.inputYesNo("Do you want to enter a guest? Y/N ");
				if(quitOption == true) {
				String getGuestEmail = ui.inputLine("Please enter the email of the guests you are inviting ");
				guestEmail.add(getGuestEmail);
				count ++;
				}
				else if (quitOption == false){
					System.out.println("Roger! ");
					quit = true;
				}
				
			}
			p = new NormEvite(partyType, partyTheme, partyLoc, getDate, host, guestEmail); 
			userParty.put(user.getEmail(), p);
			
		}
		else if(eviteType.equalsIgnoreCase("premevite")) {
			if (u.equals("normuser")) {
				System.out.println("You are a normal user, you cannot make a premium Evite!");
			}
			else {
				String partyTheme = ui.inputWord("What is the theme of this party? ");
				
				String partyLoc = ui.inputLine("What is the location of this party? ");
				LocalDate getDate = ui.getDate("What is the date of this party? ");
				String specialMessage = ui.inputLine("What message would you like to put on your Evite? ");
				String host = ui.inputWord("Who is the host for the party? ");
				Envelope userEnvelope = makeEnvelope();
				while(count < PremEvite.getGuestLimit() && !quit) {
					boolean quitOption = ui.inputYesNo("Do you want to enter a guest? Y/N ");
					if(quitOption == true) {
					String getGuestEmail = ui.inputLine("Please enter the email of the guests you are inviting ");
					guestEmail.add(getGuestEmail);
					count ++;
					}
					else if (quitOption == false){
						System.out.println("Roger! ");
						quit = true;
					}
					
				}
				p = new PremEvite(partyType, partyTheme, partyLoc, getDate, host, guestEmail, specialMessage, userEnvelope);
				userParty.put(user.getEmail(), p);
			}
		}
		
	}
	
	
	
	
	
	private void existingEvite() {
		Map<String, NormEvite> partyMap = new HashMap<>();
		String userEmail = user.getEmail();
		boolean loggedin = false;

		if(user == null) {
			loggedin = false;
			boolean loggingIn = ui.inputYesNo("You are not loggedin. Would you like to? (Y/N)");
			if(loggingIn == true) {
				login();
			}
			else if(loggingIn == false){
				System.out.println("Goodbye");
				Menu.printMenuOptions();
			}
		}
		else {
			loggedin = true;
			System.out.println(partyMap.get(userEmail));
		}
		
	}
	
	public static void main(String[] args) {
		SampleProgram p = new SampleProgram();
		p.run();
	}

}
