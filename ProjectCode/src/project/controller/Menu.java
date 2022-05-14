package project.controller;


public enum Menu {
	LOGIN("Log into your existing accoutn"),
	LOGOUT("Log out of your account"),
	REGISTRATION("Make an account"),
	CREATE_EVITE("Create an Evite"),
	VIEW_EXISTING_EVITE("View your existing Evite"),
	QUIT("Quit the program");
	
	
	// The code below are from lecture codes written by Kendra Walther
	private String description;
	private Menu(String description){
		this.description = description;
	}
	
	
	public String getDisplayString(){
		return this.description;
	}
	
	public static int getNumOptions() {
		return Menu.values().length;
	}
	
	public static Menu getOption(int num) {
		return Menu.values()[num-1];
	}
	
	public static String getMenuOptions() {
		String prompt = "*****\t Media System Menu\t*****";

		for(Menu m : Menu.values()){ //array from the enum
			prompt += "\n" + (m.ordinal()+1) + ": " + m.getDisplayString();
		}
		prompt+="\n**********************************************\n";
		return prompt;
	}
	
	public static void printMenuOptions() {
		String prompt = getMenuOptions();
		System.out.println(prompt);
	}
	
}
