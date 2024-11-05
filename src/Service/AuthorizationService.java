package Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.Administrator;
import models.JobProvider;
import models.JobSeeker;

public class AuthorizationService {
    private Scanner scanner;

    public AuthorizationService() {
        this.scanner = new Scanner(System.in);
    }

    public Object handleOptions(Object user) {
        int option = Menu(user);
        Object registeredObject = null;
        switch (option) {
            case 1:
            	registeredObject = register(user);
                break;
            case 2:
            	registeredObject = login(user);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        
        return registeredObject; //if remain null not authorized
    }

    private Object register(Object user) {
        if (user instanceof JobProvider) {
        	System.out.println("Enter Organization Name: ");
            String organizationName = scanner.nextLine();

            System.out.println("Enter Organization Type: ");
            String organizationType = scanner.nextLine();

            System.out.println("Enter Sector: ");
            String sector = scanner.nextLine();

            System.out.println("Enter Email: ");
            String email = scanner.nextLine();

            System.out.println("Enter Password: ");
            String password = scanner.nextLine();

            JobProvider provider =  new JobProvider(organizationName, organizationType, sector, email, password);
            return JobProvider.addProvider(provider)? null : provider;
            
        } else {

        	System.out.println("Enter Full Name: ");
            String fulName = scanner.nextLine();

            System.out.println("Enter Email: ");
            String email = scanner.nextLine();

            System.out.println("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.println("Enter Birth Date (YYYY-MM-DD): ");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());

            System.out.println("Enter Gender (M/F): ");
            char gender = scanner.nextLine().charAt(0);

            System.out.println("Enter Skills (comma-separated): ");
            String skillsInput = scanner.nextLine();
            List<String> skills = new ArrayList<>();
            for (String skill : skillsInput.split(",")) {
                skills.add(skill.trim());
            }

            System.out.println("Enter Password: ");
            String password = scanner.nextLine();
            
            JobSeeker seeker = new JobSeeker(fulName, email, phoneNumber, birthDate, gender, skills, password);
            return JobSeeker.addJobSeeker(seeker) ? null : seeker;
        }
    }

    private Object login(Object user) {
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Password: "); 
        String password = scanner.nextLine();

        if (user instanceof JobProvider) {
            List<JobProvider> providers = JobProvider.getAllProviders();
            for (JobProvider provider : providers) {
                if (provider.getEmail().equalsIgnoreCase(email) && provider.getPassword().equalsIgnoreCase(password) && provider.isVerified()) {
                    System.out.println("Login successful for Job Provider" );
                    return provider; 
                }
            }
            System.out.println("Invalid email or password for Job Provider.");
            return null; 

        } else if (user instanceof JobSeeker) {
            List<JobSeeker> seekers = JobSeeker.getAllJobSeekers();
            
            for (JobSeeker seeker : seekers) {
                if (seeker.getEmail().equalsIgnoreCase(email) && seeker.getPassword().equalsIgnoreCase(password)) {
                    System.out.println("Login successful for Job Seeker" );
                    return seeker; 
                }
            }
            System.out.println("Invalid email or password for Job Seeker.");
            return null; 

        } else if (user instanceof Administrator) {
            List<Administrator> administrators = Administrator.getAdministrators();
            for (Administrator admin : administrators) {
                if (admin.getEmail().equalsIgnoreCase(email) && admin.getPassword().equalsIgnoreCase(password)) {
                    System.out.println("Login successful for Administrator ");
                    return admin; 
                }
            }
            System.out.println("Invalid email or password for Administrator.");
            return null; 
        }
        
        return null; 
    }


    private int Menu(Object user) {
        int option;
        if(user instanceof Administrator) {
        	 do {
 	            System.out.println("Welcome to Part Time Management System");
 	            System.out.println("1. Login");
 	            System.out.println("2. Exit");
 	            System.out.print("Please select an option: ");
 	            option = scanner.nextInt();
 	            scanner.nextLine(); 
 	
 	            if (option < 1 || option > 2) {
 	                System.out.println("Invalid option. Please try again.");
 	            }
 	        } while (option < 1 || option > 2);
        	 //because in handleOptions 3 means exit and admin can`t register
        	 if(option == 2) {
        		 option = 3;
        	 }else if(option == 1) {
        		 option = 2;
        	 }
        	 return option;
    	}else {
	        do {
	            System.out.println("Welcome to Part Time Management System");
	            System.out.println("1. Register");
	            System.out.println("2. Login");
	            System.out.println("3. Exit");
	            System.out.print("Please select an option: ");
	            option = scanner.nextInt();
	            scanner.nextLine(); 
	
	            if (option < 1 || option > 3) {
	                System.out.println("Invalid option. Please try again.");
	            }
	        } while (option < 1 || option > 3);
    	}
        return option;
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
