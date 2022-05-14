package model.party;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.user.NormUser;
import project.controller.UI;
import project.controller.UIConsole;

public class NormEvite extends Party{
	
	
	private List<String> getGuestEmail; // make a list of guests	

	private final static int GUESTLIMIT = 10;

	
	
	public NormEvite(PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate, String host,
			List<String> getGuestEmail) {
		super(partyType, partyTheme, partyLocation, partyDate, host);
		this.getGuestEmail = getGuestEmail;
	}
	
	
	public NormEvite(PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate, String host) {
		this(partyType, partyTheme, partyLocation, partyDate, host, new ArrayList<>());
		
	}
	

	/**
	 * @return the getGuestEmail
	 */
	public List<String> getGetGuestEmail() {
		return getGuestEmail;
	}

	
	/**
	 * @param getGuestEmail the getGuestEmail to set
	 */
	public void setGetGuestEmail(List<String> getGuestEmail) {
		this.getGuestEmail = getGuestEmail;
	}

	
	/**
	 * @return the guestLimit
	 */
	public static int getGuestLimit() {
		return GUESTLIMIT;
	}


	
	private String getList() {
		String g = "";
		for(String email: getGuestEmail) {
			g += email + "/";
		}
		return g;
	}

	
	
	@Override
	public String toFileString() {
		
		return getClass().getSimpleName() + "\t" + getPartyType() + "\t" +getPartyTheme() + "\t" +getPartyLocation() 
		+ "\t" +getPartyDate() + "\t" +getHost()+ "\t" +getGetGuestEmail() ;
				
		
	}

	
//	@Override
//	public String getGuestEmail() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
}
