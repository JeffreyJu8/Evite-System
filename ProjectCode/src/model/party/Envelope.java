package model.party;

public enum Envelope {
	
	COLOR("Color"),
	STAMPS("Stamps"),
	LINERS("Linears"),
	MESSAGE("Message"),
	QUIT("Quit making Envelope");
	
	private String description;
	private Envelope(String description){
		this.description = description;
	}
	
	
	public String getDisplayString(){
		return this.description;
	}
	
	public static int getNumOptions() {
		return Envelope.values().length;
	}
	
	public static Envelope getOption(int num) {
		return Envelope.values()[num-1];
	}
	
	public static String getEnvelopeOptions() {
		String prompt = "*****\t Envelope Menu\t*****";

		for(Envelope m : Envelope.values()){ //array from the enum
			prompt += "\n" + (m.ordinal()+1) + ": " + m.getDisplayString();
		}
		prompt+="\n**********************************************\n";
		return prompt;
	}
	
	public static void printEnvelopeOptions() {
		String prompt = getEnvelopeOptions();
		System.out.println(prompt);
	}
}
