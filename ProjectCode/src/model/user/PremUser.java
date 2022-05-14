package model.user;

import java.time.LocalDate;
import java.util.List;

import model.SubscriptionPrice;
import model.party.Party;

public class PremUser extends NormUser implements SubscriptionPrice{
	
	
	
	private final int EVITE_LIMIT = 99;
	private final double SUBSCRIPTION_PRICE = 4.99;
	
	
	public PremUser(String email, String name, String password) {
		super(email, name, password);
	}
	
	public PremUser(String email, String name, String password, LocalDate birthday, List<Party> partyList) { // ask about how to change the limits to infinite
		super(email, name, password, birthday, password, partyList);
		// TODO Auto-generated constructor stub
	}
	

	
	public double getSubscriptionPrice() {
		return SUBSCRIPTION_PRICE;
	}

	
	@Override
	public String toString() {
		return "You are a premium user. You have no guest or invite limit. Your subscription price is " + getSubscriptionPrice();
	}

	
	
}
