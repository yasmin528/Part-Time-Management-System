package models;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobSeeker{
	private static List<JobSeeker> jobSeekers = new ArrayList<>();
	
	private String fulName;
	private String email;
	private String phoneNumber;
	private LocalDate birthDate;
	private char gender;
	private List<String> skills;
	private String password;
	
	public JobSeeker() {
		
	}
	public JobSeeker(String fulName, String email, String phoneNumber, LocalDate birthDate, char gender, List<String> skills,
			String password) {
		this.fulName = fulName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.skills = skills;
		this.password = password;
		
		
	}

	@Override
	public String toString() {
		return "JobSeeker [fulName=" + fulName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", birthDate="
				+ birthDate + ", gender=" + gender + ", skills=" + skills + ", password=" + password + "]";
	}
	public String getFulName() {
		return fulName;
	}

	public void setFulName(String fulName) {
		this.fulName = fulName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static boolean addJobSeeker(JobSeeker seeker) {
		for (JobSeeker existingSeeker : jobSeekers) {
            if (existingSeeker.getEmail().equalsIgnoreCase(seeker.getEmail())) {
            	return true;
            }
        }
		jobSeekers.add(seeker);
        return false;
	}
	
	public static List<JobSeeker> getAllJobSeekers() {
		return jobSeekers;
	}
	
}
