package model.party;

import java.time.LocalDate;
import java.util.List;

import model.SubscriptionPrice;
import model.user.NormUser;
import model.user.PremUser;

public class PremEvite extends NormEvite implements SubscriptionPrice{

	private Envelope envelopeType;

	private String welcomeMessage;
	private final double SUBSCRIPTION_PRICE = 4.99;
	private final static int GUESTLIMIT = 99;

	
	public PremEvite (PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate, String host,
			List<String> getGuestEmail) {
			super(partyType, partyTheme, partyLocation, partyDate, host, getGuestEmail);
	}
	
	
	public PremEvite(PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate, String host,
			List<String> getGuestEmail, String welcomeMessage, Envelope envelopeType) {
		super(partyType, partyTheme, partyLocation, partyDate, host, getGuestEmail);
		this.welcomeMessage = welcomeMessage;
		this.envelopeType = envelopeType;
	}
	

	public PremEvite(PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate,
			String host) {
		super(partyType, partyTheme, partyLocation, partyDate, host);
	}
	
	
	public PremEvite(PartyType partyType, String partyTheme, String partyLocation, LocalDate partyDate, String host,
			String welcomeMessage) {
		super(partyType, partyTheme, partyLocation, partyDate, host);
		this.welcomeMessage = welcomeMessage;
	}
	
	
	public static int getGuestLimit() {
		return GUESTLIMIT;
	}
	
	/**
	 * @return the envelopeType
	 */
	public Envelope getEnvelopeType() {
		return envelopeType;
	}


	/**
	 * @param envelopeType the envelopeType to set
	 */
	public void setEnvelopeType(Envelope envelopeType) {
		this.envelopeType = envelopeType;
	}


	/**
	 * @return the welcomeMessage
	 */
	public String getWelcomeMessage() {
		return welcomeMessage;
	}


	/**
	 * @param welcomeMessage the welcomeMessage to set
	 */
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}


	public String toFileString() {
		return getClass().getSimpleName() + "\t" + getPartyType() + "\t" +getPartyTheme() + "\t" +getPartyLocation() 
		+ "\t" +getPartyDate() + "\t" +getHost()+ "\t" +getGetGuestEmail();
	}


	@Override
	public double getSubscriptionPrice() {
		// TODO Auto-generated method stub
		return SUBSCRIPTION_PRICE;
	}
	
	
}
