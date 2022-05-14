package model.party;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Party {
	
	private PartyType partyType;
	private String partyTheme;
	private String partyLocation;
	private LocalDate partyDate;
	private String host;
	
	
	public Party(PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate, String host) {
		this.setPartyType(partyType);
		this.partyTheme = partyTheme;
		this.partyLocation = partyLocation;
		this.partyDate = partyDate;
		this.host = host;
	}
	
	/**
	 * @return the partyTheme
	 */
	public String getPartyTheme() {
		return partyTheme;
	}

	/**
	 * @param partyTheme the partyTheme to set
	 */
	public void setPartyTheme(String partyTheme) {
		this.partyTheme = partyTheme;
	}
	
	
	public String getHost() {
		return host;
	}
	
	
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the partyLocation
	 */
	public String getPartyLocation() {
		return partyLocation;
	}

	/**
	 * @param partyLocation the partyLocation to set
	 */
	public void setPartyLocation(String partyLocation) {
		this.partyLocation = partyLocation;
	}

	/**
	 * @return the partyDate
	 */
	public LocalDate getPartyDate() {
		return partyDate;
	}

	/**
	 * @param partyDate the partyDate to set
	 */
	public void setPartyDate(LocalDate partyDate) {
		this.partyDate = partyDate;
	}
	

	public PartyType getPartyType() { // do I need this. What if I were to use the switch statement
		return partyType;
	}
	

	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}
	
	
	
	public String toString() {
		return "This is a " + getPartyType() + "party, and the theme is " + getPartyTheme() + ". It is going to be on "
				+ getPartyDate() + " at " + getPartyLocation();
	}
	
	
	public String toFileString() {
		//partyType\tpartyTheme\tpartyLocation\tpartyDate 
		return getClass().getSimpleName() + getPartyType() + "\t" + getPartyTheme() + 
				"\t" + getPartyLocation() + "\t" + getPartyDate() + "\t" + getHost();
	}

	
	
}
