package model.party;

import project.controller.Menu;

public enum PartyType {
	BIRTHDAY("Birthday parties"),
	WEDDING("Weddings"),
	HOLIDAYS("Holiday parties (Christmas, Thanksgiving, Saint Patricks Day, etc...)"),
	CEREMONIES("Ceremonies (Baptism, Initiation, graduation, etc...)"),
	SPORTS("Sport parties (Super Bowl, World Cups, Olympics)"),
	QUIT("Quit making Evite");
	
	
	// The code below are from lecture codes written by Kendra Walther
		private String description;
		private PartyType(String description){
			this.description = description;
		}
		
		
		public String getDisplayString(){
			return this.description;
		}
		
		public static int getNumOptions() {
			return PartyType.values().length;
		}
		
		public static PartyType getOption(int num) {
			return PartyType.values()[num-1];
		}
		
		public static String getPartyOptions() {
			String prompt = "*****\t Evite Menu\t*****";

			for(PartyType m : PartyType.values()){ //array from the enum
				prompt += "\n" + (m.ordinal()+1) + ": " + m.getDisplayString();
			}
			prompt+="\n**********************************************\n";
			return prompt;
		}
		
		public static void printPartyOptions() {
			String prompt = getPartyOptions();
			System.out.println(prompt);
		}
	
	
}
