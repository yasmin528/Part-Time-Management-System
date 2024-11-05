package Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Application;
import models.Job;
import models.JobSeeker;

public class JobSeekerService {
	public void options(JobSeeker jobSeeker) {
		Scanner scanner = new Scanner(System.in);
		int choice;
		do {
			System.out.println("1. display a list of jobs");
			System.out.println("2. filter jobs by working day");
			System.out.println("3. filter jobs by pay per hour");
			System.out.println("4. filter jobs by region");
			System.out.println("5. logout");
			System.out.print("Choose an option: ");
			choice = scanner.nextInt();
			scanner.nextLine();

			List<Job> jobs = new ArrayList<>();

			switch (choice) {
			case 1:
				jobs = displayAllJobs();
				break;
			case 2:
				jobs = filterJobsByWorkingDays(scanner);
				break;
			case 3:
				jobs = filterJobsByPayPerHour(scanner);
				break;
			case 4:
				jobs = filterJobsByRegion(scanner);
				break;
			case 5:
				System.out.println("log out successfully");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}

			if (jobs.size() > 0) {
				System.out.println("Do you want to apply for a certain job if so enter its number if not press 0");
				int applyChoice = scanner.nextInt();
				scanner.nextLine();

				if (applyChoice < jobs.size() && applyChoice > 0) {
					Application application = new Application(jobSeeker, jobs.get(applyChoice - 1));
					boolean success = Application.addApplication(application);
					if(success) {
						System.out.println("Applied Successfully");
					}else {
						System.out.println("Failed to apply");
					}
				}
			}else {
				System.out.println("No Jobs Available");
			}
			

			System.out.println();
		} while (choice != 5);
	}

	private List<Job> displayAllJobs() {

		List<Job> jobs = Job.getJobList();

		if (jobs.isEmpty()) {
			System.out.println("No jobs available.");
		}
		int jobNo = 1;
		for (Job job : jobs) {
			System.out.println(jobNo++ + "- " + job.toString());
		}
		return jobs;
	}
	
	private List<Job> filterJobsByRegion(Scanner scanner) {
		System.out.print("Enter the region (city) to filter: ");
		String region = scanner.nextLine();

		List<Job> jobs = Job.getJobList();
		List<Job> filteredJobs = new ArrayList<>();
		
		for (Job job : jobs) {
			if (job.getCity().equalsIgnoreCase(region)) {
				filteredJobs.add(job);
			}
		}

		if (filteredJobs.isEmpty()) {
			System.out.println("No jobs available in the specified region.");
		} else {
			System.out.println("Jobs available in " + region + ":");
			int jobNo = 1;
			for (Job job : filteredJobs) {
				System.out.println(jobNo++ + "- " + job.toString());
			}
		}

		return filteredJobs;
	}
	

	private List<Job> filterJobsByPayPerHour(Scanner scanner) {
		System.out.print("Enter minimum pay per hour: ");
        double minPay = scanner.nextDouble();
        List<Job> jobs = Job.getJobList();
        List<Job> filteredJobs = new ArrayList<>();
		
		for (Job job : jobs) {
			if (job.getPayPerHour() >= minPay) {
				filteredJobs.add(job);
			}
		}

		if (filteredJobs.isEmpty()) {
			System.out.println("No jobs available with the specified pay or higher");
		} else {
			System.out.println("Jobs with pay per hour of $" + minPay + " or more:");
			int jobNo = 1;
			for (Job job : filteredJobs) {
				System.out.println(jobNo++ + "- " + job.toString());
			}
		}

		return filteredJobs;
	}
	
	private List<Job> filterJobsByWorkingDays(Scanner scanner) {
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
		            continue;
		        }
		    }
		} while (allValid == false);
		
		List<Job> allJobs = Job.getJobList();
		List<Job> filteredJobs = new ArrayList<>();
		
	    for(Job job : allJobs) {
	    	for(String day: workingDays) {
	    		if(job.getWorkingDays().contains(day)) {
	    			filteredJobs.add(job);
	    			break;
	    		}
	    	}
	    }
	    if (!filteredJobs.isEmpty()) {
		
			System.out.println("Jobs available in working time " + workingDays + ":");
			int jobNo = 1;
			for (Job job : filteredJobs) {
				System.out.println(jobNo++ + "- " + job.toString());
			}
		}

		return filteredJobs;
	}
}
