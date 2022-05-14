package project.controller;

import java.io.IOException;

public class BadUserDataException extends IOException {


	public BadUserDataException(String msg) {
		super(msg);
	}
	
}
