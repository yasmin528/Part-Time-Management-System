package partTime;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.List;

import Service.AdministratorService;
import Service.AuthorizationService;
import Service.JobProviderService;
import Service.JobSeekerService;
import models.Administrator;
import models.Job;
import models.JobProvider;
import models.JobSeeker;

class Part {
    public static void main(String args[]) {
    	JobProvider provider1 = new JobProvider("TechCorp", "Company", "Technology", "contact@techcorp.com", "password123" );
    	JobProvider.addProvider(provider1);
    	
        JobProvider provider2 = new JobProvider("EduInstitute", "Institute", "Education", "info@edu.com", "securePass");
        JobProvider.addProvider(provider2);
        
        JobSeeker seeker1 = new JobSeeker("Alice Johnson", "alice@example.com", "123-456-7890",
                                          LocalDate.of(1990, 5, 20), 'F', Arrays.asList("Java", "Python", "SQL"), "alicePass");
        JobSeeker.addJobSeeker(seeker1);
        JobSeeker seeker2 = new JobSeeker("Bob Smith", "bob@example.com", "987-654-3210",
                                          LocalDate.of(1988, 3, 15), 'M', Arrays.asList("Data Analysis", "R", "Python"), "bobPass");
        JobSeeker.addJobSeeker(seeker2);
        
        Job job1 = new Job("Software Engineer", "Develop and maintain software solutions", "New York",
                           "123 Main St", "Suite 200", 30.0, Arrays.asList("Mon", "Wed", "Fri"),
                           "9am-5pm", provider1);
        Job.addJob(job1);
        Job job2 = new Job("Data Scientist", "Analyze data and build models", "San Francisco",
                           "456 Market St", "Floor 3", 35.0, Arrays.asList("Tue", "Thu"),
                           "10am-6pm", provider1);
        Job job3 = new Job("Teacher", "Teach math and science", "Boston",
                           "789 Broadway", "", 25.0, Arrays.asList("Mon", "Tue", "Thur"),
                           "8am-3pm", provider2);
        Job.addJob(job2);
        Job.addJob(job3);

        // Start menu-driven program
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Welcome to the Administrator Panel");
            System.out.println("1. Job Providers");
            System.out.println("2. Job Seekers");
            System.out.println("3. Administrator");
            System.out.println("4. Log Out");
            System.out.print("Please select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); 
            
            AuthorizationService authorizationService = new AuthorizationService();
            
            switch (option) {
                case 1:
                	JobProvider provider = new JobProvider() ;
                	provider = (JobProvider) authorizationService.handleOptions(provider);
                	if(provider != null) {
	                	JobProviderService service = new JobProviderService();
	                	service.options(provider);
                	}else {
                		System.out.println("Not Authorized");
                	}
                    break;
                case 2:
                	JobSeeker seeker = new JobSeeker();
                	seeker = (JobSeeker) authorizationService.handleOptions(seeker);
                	if(seeker != null) {
                		JobSeekerService service =  new JobSeekerService();
                		service.options(seeker);
                	}else {
                		System.out.println("Not Authorized");
                	}
                    break;
                case 3:
                	Administrator admin = new Administrator();
                	admin = (Administrator) authorizationService.handleOptions(admin);
                	if(admin != null) {
                		AdministratorService service =  new AdministratorService();
                		service.options();
                	}else {
                		System.out.println("Not Authorized");
                	}
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 4);

        scanner.close();
    }
}
