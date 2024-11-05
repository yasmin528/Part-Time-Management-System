package models;

import java.util.ArrayList;
import java.util.List;

public class Job {
	private static List<Job> jobList = new ArrayList<>();

	private String jobName;
	private String jobDescription;
	private String city;
	private String address1;
	private String address2;
	private double payPerHour;
	private List<String> workingDays;
	private String workTime;
	private JobProvider jobProvider;

	public Job(String jobName, String jobDescription, String city, String address1, String address2, double payPerHour,
			List<String> workingDays, String workTime, JobProvider jobProvider) {
		super();
		this.jobName = jobName;
		this.jobDescription = jobDescription;
		this.city = city;
		this.address1 = address1;
		this.address2 = address2;
		this.payPerHour = payPerHour;
		this.workingDays = workingDays;
		this.workTime = workTime;
		this.jobProvider = jobProvider;// to display job applicants of specific job
	}

	@Override
	public String toString() {
		return "Job [jobName=" + jobName + ", jobDescription=" + jobDescription + ", city=" + city + ", address1="
				+ address1 + ", address2=" + address2 + ", payPerHour=" + payPerHour + ", workingDays=" + workingDays
				+ ", workTime=" + workTime + ", jobProvider=" + jobProvider.getOrganizationName() + "]";
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public double getPayPerHour() {
		return payPerHour;
	}

	public void setPayPerHour(double payPerHour) {
		this.payPerHour = payPerHour;
	}

	public List<String> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<String> workingDays) {
		this.workingDays = workingDays;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public JobProvider getJobProvider() {
		return jobProvider;
	}

	public void setJobProvider(JobProvider jobProvider) {
		this.jobProvider = jobProvider;
	}

	public static List<Job> getJobList() {
		return jobList;
	}

	public static boolean addJob(Job job) {
		boolean added = jobList.add(job);
		return added;
	}
	public static boolean editJob(Job currentJob , Job UpdatedJob) {
		int jobIndex = jobList.indexOf(currentJob);
		if (jobIndex != -1) {
	        jobList.set(jobIndex, UpdatedJob); 
	        Application.editAppliction(currentJob, UpdatedJob);
	        return true;
	    } else {
	        System.out.println("Job not found.");
	        return false; 
	    }
	}

	public static boolean deleteJob(Job job) {
		boolean removedJob = jobList.remove(job);
		Application.deleteAppliction(job);

		if (removedJob) {
			return true;
		} else {
			return false;
		}
	}
}
