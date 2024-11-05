package models;

import java.util.ArrayList;
import java.util.List;

public class JobProvider {
	private static List<JobProvider> jobProviders = new ArrayList<>();

	private String organizationName;
	private String organizationType;
	private String sector;
	private String email;
	private String password;
	private boolean isVerified;

	public JobProvider() {
	}

	public JobProvider(String organizationName, String organizationType, String sector, String email, String password) {
		this.organizationName = organizationName;
		this.organizationType = organizationType;
		this.sector = sector;
		this.email = email;
		this.password = password;
		this.isVerified = false;

	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public static boolean addProvider(JobProvider provider) {
		for (JobProvider existingProvider : jobProviders) {
			if (existingProvider.getEmail().equals(provider.getEmail())) {
				return true;
			}
		}
		jobProviders.add(provider);
		return false;
	}

	public static void editProvider(JobProvider oldProvider,JobProvider newProvider) {
		jobProviders.set(jobProviders.indexOf(oldProvider), newProvider);
		EmploymentRecord.editEmploymentRecord(oldProvider, newProvider);
		return;

	}

	@Override
	public String toString() {
		return "JobProvider [organizationName=" + organizationName + ", organizationType=" + organizationType
				+ ", sector=" + sector + ", email=" + email + ", password=" + password + ", isVerified=" + isVerified
				+ "]";
	}

	public static List<JobProvider> getAllProviders() {
		return jobProviders;
	}
}
