package model.user;

import java.time.LocalDate;
import java.util.List;

import model.party.Party;

public class NormUser extends User{
	
	private LocalDate birthday;
	private String language;
	private List<Party> partyList; 
	
	private final int EVITE_LIMIT = 3;
	

	public NormUser(String email, String name, String password) {
		super(email, name, password);
	}
	
	public NormUser(String email, String name, String password, LocalDate birthday, String language, List<Party> partyList) {
		super(email, name, password);
		this.birthday = birthday;
		this.language = language;
		this.partyList = partyList;
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the birthday
	 */
	public LocalDate getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	
	public String getEmail() {
		return email;
	}

	
	@Override
	public String toString() {
		return "You are a normal user. Your birthday is: " + getBirthday() + " Your default language is " + getLanguage() + 
				" Your guest limit is: " + ". Your invite limit is: 3";  
	}
	
	

}
