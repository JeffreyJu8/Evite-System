package model.user;

import java.util.Objects;


public class User{
	protected String email;
	private String name;
	private String password;

	
	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public boolean checkPassword(String attempt) {
		return attempt.equals(password);
	}
	public boolean updatePassword(String attempt, String newPword) {
		boolean updated = false;
		if(checkPassword(attempt)) {
			this.password = newPword;
			updated = true;
			
		}
		 return updated;
	}

	@Override
	public int hashCode() {
		//hashCode and equals go together, so if my equals method ends up saying case doesn't matter, then I can't
		//make the case matter for hashCode, so making email and name lowercase when doing hashCode
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.toLowerCase().hashCode());
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return email.equalsIgnoreCase(other.email) && name.equalsIgnoreCase(other.name)
				&& password.equals(other.password);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", password=" + password + "]";
	}

	
	public String toFileString() {
		//userType\temail\tname\tpassword 
		//userType is the class name for this thing
		return getClass().getSimpleName() + "\t" + email + 
				"\t" + this.name + "\t" + this.password;
	}

	
	
	
}
