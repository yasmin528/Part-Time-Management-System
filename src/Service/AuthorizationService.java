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
        	 // Organization Name
            String organizationName;
            while (true) {
                System.out.println("Enter Organization Name: ");
                organizationName = scanner.nextLine().trim();
                if (!organizationName.isEmpty()) {
                    break;
                } else {
                    System.out.println("Organization Name cannot be empty. Please enter the name of the organization.");
                }
            }

            // Organization Type
            String organizationType;
            while (true) {
                System.out.println("Enter Organization Type: ");
                organizationType = scanner.nextLine().trim();
                if (!organizationType.isEmpty()) {
                    break;
                } else {
                    System.out.println("Organization Type cannot be empty. Please enter the type of the organization.");
                }
            }

            // Sector
            String sector;
            while (true) {
                System.out.println("Enter Sector: ");
                sector = scanner.nextLine().trim();
                if (!sector.isEmpty()) {
                    break;
                } else {
                    System.out.println("Sector cannot be empty. Please enter the sector.");
                }
            }
            String email;
            while (true) {
                System.out.println("Enter Email: ");
                email = scanner.nextLine().trim();
                if (!email.isEmpty()) {
                    break;
                } else {
                    System.out.println("Email cannot be empty. Please enter your email.");
                }
            }
            
            String password;
            while (true) {
                System.out.println("Enter Password: ");
                password = scanner.nextLine().trim();
                if (!password.isEmpty()) {
                    System.out.println("Password confirmed.");
                    break;
                } else {
                    System.out.println("Password can`t be empty. Please try again.");
                }
            }

            JobProvider provider =  new JobProvider(organizationName, organizationType, sector, email, password);
            return JobProvider.addProvider(provider)? null : provider;
            
        } else {

        	// Full Name
            String fullName;
            while (true) {
                System.out.println("Enter Full Name: ");
                fullName = scanner.nextLine().trim();
                if (!fullName.isEmpty()) {
                    break;
                } else {
                    System.out.println("Full Name cannot be empty. Please enter your full name.");
                }
            }

            // Email
            String email;
            while (true) {
                System.out.println("Enter Email: ");
                email = scanner.nextLine().trim();
                if (!email.isEmpty()) {
                    break;
                } else {
                    System.out.println("Email cannot be empty. Please enter your email.");
                }
            }

            // Phone Number
            String phoneNumber;
            while (true) {
                System.out.println("Enter Phone Number: ");
                phoneNumber = scanner.nextLine().trim();
                if (!phoneNumber.isEmpty()) {
                    break;
                } else {
                    System.out.println("Phone Number cannot be empty. Please enter your phone number.");
                }
            }

            // Birth Date
            LocalDate birthDate;
            while (true) {
                System.out.println("Enter Birth Date (YYYY-MM-DD): ");
                try {
                    birthDate = LocalDate.parse(scanner.nextLine().trim());
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please enter your birth date in YYYY-MM-DD format.");
                }
            }

            // Gender
            char gender;
            while (true) {
                System.out.println("Enter Gender (M/F): ");
                String genderInput = scanner.nextLine().trim().toUpperCase();
                if (genderInput.equals("M") || genderInput.equals("F")) {
                    gender = genderInput.charAt(0);
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'M' for Male or 'F' for Female.");
                }
            }

            // Skills
            System.out.println("Enter Skills (comma-separated): ");
            String skillsInput = scanner.nextLine();
            List<String> skills = new ArrayList<>();
            for (String skill : skillsInput.split(",")) {
                skills.add(skill.trim());
            }

            // Password verification
            String password;
            while (true) {
                System.out.println("Enter Password: ");
                password = scanner.nextLine().trim();
                if (!password.isEmpty()) {
                    System.out.println("Password confirmed.");
                    break;
                } else {
                    System.out.println("Password can`t be empty. Please try again.");
                }
            }
            
            JobSeeker seeker = new JobSeeker(fullName, email, phoneNumber, birthDate, gender, skills, password);
            return JobSeeker.addJobSeeker(seeker) ? null : seeker;
        }
    }

    private Object login(Object user) {
    	String email;
        while (true) {
            System.out.println("Enter Email: ");
            email = scanner.nextLine().trim();
            if (!email.isEmpty()) {
                break;
            } else {
                System.out.println("Email cannot be empty. Please enter your email.");
            }
        }
        String password;
        while (true) {
            System.out.println("Enter Password: ");
            password = scanner.nextLine().trim();
            if (!password.isEmpty()) {
                System.out.println("Password confirmed.");
                break;
            } else {
                System.out.println("Password can`t be empty. Please try again.");
            }
        }

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

}
