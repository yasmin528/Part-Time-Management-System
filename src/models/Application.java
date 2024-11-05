package models;

import java.util.ArrayList;
import java.util.List;

public class Application {
	private static List<Application> application = new ArrayList<>();

	private JobSeeker seeker;
	private Job job;
	private String status; // "applied," "accepted," "rejected," "ended"

	public Application(JobSeeker seeker, Job job) {
		this.seeker = seeker;
		this.job = job;
		this.status = "applied";
	}

	@Override
	public String toString() {
		return "Application [seeker=" + seeker.getFulName() + ", job=" + job.getJobName() + ", status=" + status + "]";
	}

	public JobSeeker getSeeker() {
		return seeker;
	}

	public void setSeeker(JobSeeker seeker) {
		this.seeker = seeker;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static List<Application> getAllApplicationsList() {
		return application;
	}

	// to get applicants of specific job
	public static List<Application> getApplicationsListOfSpecificJob(Job job) {
		List<Application> applications = getAllApplicationsList();
		List<Application> FilteredApplications = new ArrayList<>();
		for (Application application : applications) {
			if (application.getJob() == job) {
				FilteredApplications.add(application);
			}
		}
		return FilteredApplications;
	}
	public static boolean addApplication(Application currentApplication) {
        for (Application app : application) {
            if (app.getSeeker().equals(currentApplication.getSeeker()) && 
                app.getJob().equals(currentApplication.getJob())) {
                System.out.println("Application already exists for this job seeker and job.");
                return false; 
            }
        }
        
        application.add(currentApplication);
        return true;
    }
	public static boolean deleteAppliction(Job job) {
		for (Application applicant : application) {
			if (applicant.getJob() == job) {
				application.remove(applicant);
				return true;
			}
		}
		return false;
	}
	public static boolean editAppliction(Job currentJob , Job UpdatedJob) {
		boolean flag = false;
		for (Application applicant : application) {
			if (applicant.getJob() == currentJob) {
				applicant.setJob(UpdatedJob);
				flag = true;
			}
		}
		return flag;
	}
	
}
