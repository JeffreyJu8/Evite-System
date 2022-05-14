package project.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import model.party.Envelope;
import model.party.NormEvite;
import model.party.Party;
import model.party.PartyType;
import model.party.PremEvite;
import model.user.NormUser;
import model.user.User;


public class LoadUserData {


	
	public static Map<String, NormUser> readUsersFromFile(String userFile) { // this method maps email to user
		// Read file from scratch, make map, return map of users 
		Map<String, NormUser> users = new HashMap<>();

		// Let's try to RECOVER from not finding the file, let's try up to 3 times
		boolean success = false;
		int times = 0; 
		do {
			try(FileInputStream fis = new FileInputStream(userFile);
					Scanner fs = new Scanner(fis)){
				fs.nextLine(); // SKIP header row
				while(fs.hasNextLine()) { //while there is data to be read....
					String line = fs.nextLine();
					//System.out.println(line);
					try {
						NormUser u = parseUserData(line); // THIS is risky
						// one fix: if (u!= null) --> then add
						users.put(u.getEmail(), u);
					}catch(BadUserDataException e) {
						System.out.println("Skipping bad line " + e);
						//TODO: think about how to recover - in version 2
					}

				}
				success = true;
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find file named: " + userFile);
				System.out.println(e);
				// let's get a new one to try
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter new file name to try: ");
				userFile = sc.nextLine();
				times++;
			} catch (IOException e) {
				e.printStackTrace();
				times++;
			}
		}while(!success && times < 3);

		return users;
	}

	
	// this method parses through the user data file
	private static NormUser parseUserData(String line) throws BadUserDataException {
		// TODO Auto-generated method stub
		NormUser u = null;
		Scanner sc = new Scanner(line);
		sc.useDelimiter("\t");
		String type = sc.next();
		//if(! type.startsWith("userType")) {
			String email = sc.next();
			String name = sc.next();
			String pword = sc.next();
			//TODO: make based on type
			u = new NormUser(email, name, pword);
		//}
		return u;
	}
	
	
	
	
	public static Map<String, NormEvite> readUserParty(String partyFile, Map<String, NormUser> users){ // make a list of parties
		// making a map that reads the party Evites that a user created
		
		Map<String, NormEvite> parties = new HashMap<>();
		
		try(FileInputStream fis = new FileInputStream(partyFile);
				Scanner fs = new Scanner(fis)){
			fs.nextLine();
			while(fs.hasNextLine()) { //while there is data to be read....
				String line = fs.nextLine();
				
				//System.out.println(line);
				NormEvite u = parsePartyData(line, users);
				if(u != null) {
					parties.put(u.getHost(), u);
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parties;
	}
	
	
	
	private static NormEvite parsePartyData(String line, Map<String, NormUser> users) {
		// TODO Auto-generated method stub
		ArrayList<String> guestEmail = new ArrayList<>();
		NormEvite u = null;
		Scanner sc = new Scanner(line);
		sc.useDelimiter("\t");
	
		String type = sc.next();
		
		// asks for the information needed on an Evite
		PartyType partyType = PartyType.valueOf(type.toUpperCase());
		String partyTheme = sc.next();
		String partyLocation = sc.next();
		String partyDateStr = sc.next();
		String hostEmail = sc.next();
		String host = sc.next();
		String welcomeMessage = sc.next();
		String guestListStr = sc.next();
		String envelopeStr = sc.next();
		Envelope envelope = Envelope.valueOf(type.toUpperCase());
		guestEmail.add(guestListStr);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate partyDate = LocalDate.parse(partyDateStr, dateFormatter);
		
		//making the Evite itself
		// different format depending on norm or prem Evite
		if(type.equalsIgnoreCase("normevite")) {
			u = new NormEvite(partyType, partyTheme, partyLocation, partyDate, host, guestEmail);
		}
		else if(type.equalsIgnoreCase("premevite")) {
			u = new PremEvite(partyType, partyTheme, partyLocation, partyDate, host, guestEmail);
		}
		return u;
	}

	
	
	public static void main(String[] args) {
		Map<String, NormUser> map = readUsersFromFile(SampleProgram.USER_FILE);
		Map<String, NormEvite> partyMap = readUserParty(SampleProgram.EVITE_FILE, map);
	}
	
}
