package models;

import java.util.ArrayList;
import java.util.List;

public class Administrator {
	private static List<Administrator> administrators = new ArrayList<Administrator>() {
	    {
	        add(new Administrator("a1@example.com", "123"));
	        add(new Administrator("a2@example.com", "456"));
	    }
	};

	
	private String email;
	private String password;
	
	public Administrator() {
	}
	public Administrator(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public static List<Administrator> getAdministrators() {
		return administrators;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
