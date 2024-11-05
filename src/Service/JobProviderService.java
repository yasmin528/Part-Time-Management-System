package Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Application;
import models.EmploymentRecord;
import models.Job;
import models.JobProvider;

public class JobProviderService {
	
	public void options(JobProvider jobProvider) {
		Scanner scanner = new Scanner(System.in);
		int choice;
		do {
			System.out.println("1. Post hour-based jobs");
			System.out.println("2. Display a list of jobs applicants for a specific job");
			System.out.println("3. Edit posted jobs");
			System.out.println("4. Delete posted jobs");
			System.out.println("5. Change status of applicant");
			System.out.println("6. Logout");
			System.out.print("Choose an option: ");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				postJob(jobProvider, scanner);
				break;
			case 2:
				displayJobApplicantOfSpecficJob(jobProvider, scanner);
				break;
			case 3:
				EditPostedJob(jobProvider, scanner);
				break;
			case 4:
				DeletePostedJob(jobProvider, scanner);
				break;
			case 5:
				changeStatus(jobProvider, scanner);
				break;
			case 6:
				System.out.println("log out successfully");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
			System.out.println();
		} while (choice != 6);
	}

	private void changeStatus(JobProvider jobProvider, Scanner  scanner) {
		List<Application>applicants = displayJobApplicantOfSpecficJob(jobProvider, scanner);
		if(applicants.size()>0) {
		System.out.println("Select an applicant that you want to change its status:");
		int applicantNo;
		do {
			applicantNo =scanner.nextInt();
			scanner.nextLine();
			
			if(applicantNo < 1 || applicantNo > applicants.size()) {
				System.out.println("Please enter a valid Number");
			}
		
		}while(applicantNo < 1 || applicantNo > applicants.size());
		
		Application applicant = applicants.get(applicantNo -1);
		
		String[]validStatus = {"accept" , "reject" , "ënd contract"};
		
		int statusNo =-1;
		
		do {
			if(applicant.getStatus() == "accept") {
				System.out.println("Enter new status: 3 for End contract");
				statusNo = scanner.nextInt();
				if(statusNo != 3) {
					System.out.println("Please Enter a valid number");
					statusNo = -1;
				}
			}else if(applicant.getStatus() == "applied") {
				System.out.println("Enter new status: 1 for Accept - 2 for Reject");
				statusNo = scanner.nextInt();
				if(statusNo < 1 || statusNo > 2) {
					System.out.println("Please Enter a valid number");
					statusNo = -1;
				}
			}else if(applicant.getStatus() == "rejected"){
				System.out.println("the applicant has been rejected");
				return;
			}
		}while(statusNo == -1);
		
		applicant.setStatus(validStatus[statusNo-1]);
		
		if(statusNo == 1) {
			EmploymentRecord employmentRecord = new EmploymentRecord(jobProvider, applicant.getJob(), applicant.getSeeker(), LocalDate.now());
			EmploymentRecord.addEmployee(employmentRecord);
		}else if (statusNo == 3) {
			EmploymentRecord.removeEmployee(applicant.getJob(), jobProvider, applicant.getSeeker());
		}
		
		System.out.println("Status changed successfully");
		}
		
	}

	private void DeletePostedJob(JobProvider jobProvider, Scanner scanner) {
		List<Job> jobs = displayJobsPostedByJobsProvider(jobProvider);

		if (jobs.isEmpty()) {
			System.out.println("You have not posted any jobs yet.");
			return;
		}

		System.out.println("Select a job to view applicants:");
		int jobNo = 1;
		for (Job job : jobs) {
			System.out.println(jobNo++ + ". " + job.getJobName());
		}

		System.out.print("Enter the job number to be deleted: ");
		int selectedJobIndex = scanner.nextInt();
		scanner.nextLine();

		if (selectedJobIndex < 1 || selectedJobIndex > jobs.size()) {
			System.out.println("Invalid job number. Please try again.");
			return;
		}

		Job selectedJob = jobs.get(selectedJobIndex - 1);

		boolean removed = Job.deleteJob(selectedJob);
		if (removed == true) {
			System.out.println("Deleted Successfully");
		} else {
			System.out.println();
		}
	}

	private void EditPostedJob(JobProvider jobProvider, Scanner scanner) {
		List<Job> jobs = displayJobsPostedByJobsProvider(jobProvider);

		if (jobs.isEmpty()) {
			System.out.println("You have not posted any jobs yet.");
			return;
		}

		System.out.println("Select a job to view applicants:");
		int jobNo = 1;
		for (Job job : jobs) {
			System.out.println(jobNo++ + ". " + job.getJobName());
		}

		System.out.print("Enter the job number to be edited: ");
		int selectedJobIndex = scanner.nextInt();
		scanner.nextLine();

		if (selectedJobIndex < 1 || selectedJobIndex > jobs.size()) {
			System.out.println("Invalid job number. Please try again.");
			return;
		}

		Job selectedJob = jobs.get(selectedJobIndex - 1);
		System.out.println("Current Job Details:");
		System.out.println(selectedJob.toString());

		//Job name
		System.out.print("Enter new job name (or press Enter to keep the same): ");
		String newJobName = scanner.nextLine();
		if (newJobName.isEmpty()) {
			newJobName = selectedJob.getJobName();
		}
		
		//Description
		System.out.print("Enter new job description (or press Enter to keep the same): ");
		String newJobDescription = scanner.nextLine();
		if (newJobDescription.isEmpty()) {
			newJobDescription = selectedJob.getJobDescription();
		}
		
		//City
		System.out.print("Enter new city (or press Enter to keep the same): ");
		String newCity = scanner.nextLine();
		if (newCity.isEmpty()) {
			newCity = selectedJob.getCity();
		}
		
		//Address1
		System.out.print("Enter new address1 (or press Enter to keep the same): ");
		String newAddress1 = scanner.nextLine();
		if (newAddress1.isEmpty()) {
			newAddress1 = selectedJob.getAddress1();
		}

		//Address 2
		System.out.print("Enter new address2 (or press Enter to keep the same): ");
		String newAddress2 = scanner.nextLine();
		if (newAddress2.isEmpty()) {
			newAddress2 = selectedJob.getAddress2();
		}
		
		//Pay Per Hour
		System.out.print("Enter new pay per hour (or press any negative value to keep the same): ");
		double newPayPerHour = scanner.nextDouble();
		scanner.nextLine();
		if (newPayPerHour <= -1) {
			newPayPerHour = selectedJob.getPayPerHour();
		}
		
		//Working Days
		List<String> validDays = Arrays.asList("mon", "tue", "wed", "thu", "fri", "sat", "sun");
		List<String> newWorkingDays = new ArrayList<>();
		boolean allValid = false;

		do {
		    System.out.print("Enter new working Days (comma-separated, e.g., sat,sun)(or press Enter to keep the same): ");
		    String workingDaysInput = scanner.nextLine();
		    if(workingDaysInput.isEmpty()) {
		    	newWorkingDays = selectedJob.getWorkingDays();
		        break;
		    }
		    String[] daysArray = workingDaysInput.split(",");
		    
		    newWorkingDays = new ArrayList<>();
		    for (String day : daysArray) {
		    	newWorkingDays.add(day.trim().toLowerCase());
		    }

		    allValid = true;
		    for (String day : newWorkingDays) {
		        if (!validDays.contains(day)) {
		            System.out.println("Invalid day entered: " + day + ". Please enter valid days only");
		            allValid = false;
		            break;
		        }
		    }
		} while (allValid==false);
		
		//Working Time
		String newWorkTime;
		boolean validTime = false;
		String timePattern = "(1[0-2]|[1-9])\\s?(AM|PM|am|pm|Am|Pm)\\s?-\\s?(1[0-2]|[1-9])\\s?(AM|PM|am|pm|Am|Pm)";

		do {
		    System.out.print("Work Time (e.g., 9 AM - 5 PM): ");
		    newWorkTime = scanner.nextLine().trim();
		    if(newWorkTime.length()==0) {
		    	newWorkTime = selectedJob.getWorkTime();
		    	break;
		    }
		    if (newWorkTime.matches(timePattern)) {
		        validTime = true;
		    } else {
		        System.out.println("Invalid work time format. Please enter in the format '9 AM - 5 PM'.");
		    }
		} while (validTime == false);
		
		//update the Job
		Job updatedJob = new Job(newJobName, newJobDescription, newCity, newAddress1, newAddress2, newPayPerHour,
				newWorkingDays, newWorkTime, jobProvider);
		boolean success = Job.editJob(selectedJob, updatedJob);

		if (success) {
			System.out.println("Job updated successfully.");
		} else {
			System.out.println("Failed to update job.");
		}

	}

	private List<Application> displayJobApplicantOfSpecficJob(JobProvider jobProvider, Scanner scanner) {

		List<Job> jobs = displayJobsPostedByJobsProvider(jobProvider);
		List<Application> applicants = new ArrayList<Application>();

		if (jobs.isEmpty()) {
			System.out.println("You have not posted any jobs yet.");
			return applicants;
		}

		System.out.println("Select a job to view applicants:");
		int jobNo = 1;
		for (Job job : jobs) {
			System.out.println(jobNo++ + ". " + job.getJobName());
		}

		System.out.print("Enter the job number to view applicants: ");
		int selectedJobIndex = scanner.nextInt();
		scanner.nextLine();

		if (selectedJobIndex < 1 || selectedJobIndex > jobs.size()) {
			System.out.println("Invalid job number. Please try again.");
			return applicants;
		}

		Job selectedJob = jobs.get(selectedJobIndex - 1);

		applicants = Application.getApplicationsListOfSpecificJob(selectedJob);

		if (applicants == null || applicants.isEmpty()) {
			System.out.println("No applicants have applied for this job yet.");
		} else {
			System.out.println("Applicants for the job \"" + selectedJob.getJobName() + "\":");
			int applicantNo = 1;
			for (Application applicant : applicants) {
				System.out.println(applicantNo++ + ". " + applicant.toString());
			}
		}
		
		return applicants;
	}

	private void postJob(JobProvider jobProvider, Scanner scanner) {
		System.out.println("Enter job details:");
		//job name
		String jobName;
		do {
			System.out.print("Job Name: ");
			jobName = scanner.nextLine();
		}while(jobName.isEmpty());
		
		//description
		String jobDescription;
		do {
			System.out.print("Job Description: ");
			jobDescription = scanner.nextLine();
		}while (jobDescription.isEmpty());
		
		//city
		String city;
		do {
			System.out.print("City: ");
			city = scanner.nextLine();
		}while(city.isEmpty());
		
		//Address 1
		String address1;
		do {
			System.out.print("Address Line 1: ");
			address1 = scanner.nextLine();
		}while(address1.isEmpty());
		
		//address2 (optional)
		System.out.print("Address Line 2 (optional): ");
		String address2 = scanner.nextLine();
		
		//Pay Per hour
		double payPerHour;
		do {
			System.out.print("Pay per Hour: ");
			payPerHour = scanner.nextDouble();
			scanner.nextLine();
		}while(payPerHour <= -1 );
		
		//Working Days
		//Enter all the days not from to
		List<String> validDays = Arrays.asList("mon", "tue", "wed", "thu", "fri", "sat", "sun");
		List<String> workingDays = new ArrayList<>();
		boolean allValid = false;

		do {
		    System.out.print("Working Days (comma-separated, e.g., sat,sun): ");
		    String workingDaysInput = scanner.nextLine();
		    if(workingDaysInput.isEmpty()) {
		    	System.out.println("Please Enter a valid day");
		        continue;
		    }
		    String[] daysArray = workingDaysInput.split(",");
		    
		    workingDays = new ArrayList<>();
		    for (String day : daysArray) {
		        workingDays.add(day.trim().toLowerCase());
		    }

		    allValid = true;
		    for (String day : workingDays) {
		        if (!validDays.contains(day)) {
		            System.out.println("Invalid day entered: " + day + ". Please enter valid days only");
		            allValid = false;
		            break;
		        }
		    }
		} while (allValid==false);
		
		//Working Time
		String workTime;
		boolean validTime = false;
		String timePattern = "(1[0-2]|[1-9])\\s?(AM|PM|am|pm|Am|Pm)\\s?-\\s?(1[0-2]|[1-9])\\s?(AM|PM|am|pm|Am|Pm)";

		do {
		    System.out.print("Work Time (e.g., 9 AM - 5 PM): ");
		    workTime = scanner.nextLine().trim();
		    
		    if(workTime.length()==0) {
		    	System.out.println("please Enter a valid time");
		    	continue;
		    }
		    if (workTime.matches(timePattern)) {
		        validTime = true;
		    } else {
		        System.out.println("Invalid work time format. Please enter in the format '9 AM - 5 PM'.");
		    }
		} while (validTime == false);
		
		//Create New Job
		Job newJob = new Job(jobName, jobDescription, city, address1, address2, payPerHour, workingDays, workTime,
				jobProvider);
		
		boolean success = Job.addJob(newJob);
		if (success) {
			System.out.println("Job posted successfully!");
		} else {
			System.out.println("Job posted failed!");
		}
	}

	private List<Job> displayJobsPostedByJobsProvider(JobProvider jobProvider) {
		List<Job> jobs = Job.getJobList();
		List<Job> filteredJobs = new ArrayList<>();
		if (jobs.isEmpty()) {
			System.out.println("No jobs available.");
		}
		for (Job job : jobs) {
			if (job.getJobProvider() == jobProvider) {
				filteredJobs.add(job);
			}
		}

		return filteredJobs;
	}
}
