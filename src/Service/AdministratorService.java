package Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import models.Application;
import models.EmploymentRecord;
import models.Job;
import models.JobProvider;
import models.JobSeeker;

public class AdministratorService {
    private Scanner scanner= new Scanner(System.in);;

	public void options() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. View the list of job providers who are pending for verification"); // accept or reject
            System.out.println("2. Generate organizations reports");
            System.out.println("3. Generate sector reports");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                	ViewProvidersPending();
                    break;
                case 2:
                    generateOrganizationReport();
                    break;
                case 3:
                	generateSectorReport();
                    break;
                case 4:
                    System.out.println("Logged out successfully");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
         
    }

	private void ViewProvidersPending() {
		List<JobProvider> providers = JobProvider.getAllProviders();
		List<JobProvider> providersPending = new ArrayList<>();
		if(providers.isEmpty()) {
			System.out.println("No pending providers");
			return;
		}
		for (JobProvider jobProvider : providers) {
			if(!jobProvider.isVerified()) {
				providersPending.add(jobProvider);
			}
		}
		int counter = 1;
		for (JobProvider jobProvider : providersPending) {
			System.out.println(counter++ + ": " + jobProvider);
		}
		
		System.out.println("If you want to Verify Job Provider press the number of the provider else Press 0");
		int option = 0;
		do {
			option = scanner.nextInt();
			scanner.nextLine();
			if (option == 0) {
				return;
			}else if(option < 0 || option > providersPending.size()){
				System.out.println("Please enter a valid number");
			}
		}while(option < 0 || option > providersPending.size());
		
		JobProvider OldProvider = providersPending.get(option-1);
		JobProvider NewProvider = OldProvider;
		NewProvider.setVerified(true);
		JobProvider.editProvider(OldProvider , NewProvider);
		System.out.println("Verified Successfully");
	}

	private void generateSectorReport() {
	    System.out.println("The Sector Report: ");
	    
	    List<Job> allJobs = Job.getJobList();
	    List<EmploymentRecord> employees = EmploymentRecord.getAllEmploymentRecords();
	    
	    //The most sought-after sectors
	    String mostSoughtAfterSector = mostSoughtAfterSector(allJobs);
	    if (mostSoughtAfterSector.isEmpty()) {
	        System.out.println("No sector in the system");
	    } else {
	        System.out.println("The most sought after sector is: " + mostSoughtAfterSector);
	    }
	    
	    // Average wages of employees in each sector
	    Map<String, Double> averageWagesPerSector = averageWagesOfEmployeesInEachSector(allJobs);
	    System.out.println("Average Wages of Employees in Each Sector:");
	    averageWagesPerSector.forEach((sector, avgWage) -> 
	        System.out.println("Sector: " + sector + " Average Wage: $" + avgWage)
	    );
	    
	    // Average ages of employees in each sector
	    Map<String, Double> averageAgesPerSector = averageAgesOfEmployeesInEachSector(employees,allJobs);
	    System.out.println("Average Ages of Employees in Each Sector:");
	    averageAgesPerSector.forEach((sector, avgAge) -> 
	        System.out.println("Sector: " + sector + " Average Age: " + avgAge)
	    );
	    
	    // Ratio of male to female employees in each sector
	    Map<String, String> genderRatioPerSector = genderRatioInEachSector(employees,allJobs);
	    System.out.println("Gender Ratio (Male:Female) in Each Sector:");
	    genderRatioPerSector.forEach((sector, ratio) -> 
	        System.out.println("Sector: " + sector + " Ratio: " + ratio)
	    );
	    
	    // Number of accepted contracts in each sector
	    Map<String, Long> acceptedContractsPerSector = acceptedContractsInEachSector(employees,allJobs);
	    System.out.println("Number of Accepted Contracts in Each Sector:");
	    acceptedContractsPerSector.forEach((sector, count) -> 
	        System.out.println("Sector: " + sector + ", Accepted Contracts: " + count)
	    );
	}

	//return the mostSoughtAfterSector 
	private String mostSoughtAfterSector(List<Job> jobs) {
	    Map<String, Integer> sectorApplicationCount = new HashMap<>();
	    
	    for (Job job : jobs) {
	        String sector = job.getJobProvider().getSector();
	        int applicantsCount = Application.getApplicationsListOfSpecificJob(job).size();
	        sectorApplicationCount.put(sector, sectorApplicationCount.getOrDefault(sector, 0) + applicantsCount);
	    }
	    String theMostSoughtAfterSector = "";
    	int maxNumberOfApplicant = -1;
    	for(Map.Entry<String, Integer> entry : sectorApplicationCount.entrySet()) {
    		if (entry.getValue() > maxNumberOfApplicant) {
    			maxNumberOfApplicant = entry.getValue();
    			theMostSoughtAfterSector = entry.getKey();
            }
    	}
    	
    	return theMostSoughtAfterSector;
	}

	//  calculate average wages in each sector
	private Map<String, Double> averageWagesOfEmployeesInEachSector(List<Job> jobs) {
	    Map<String, Double> sectorTotalWages = new HashMap<>();//sector and payperDay
	    Map<String, Integer> sectorJobCount = new HashMap<>();//sector and the number of jobs
	    
	    for (Job job : jobs) {
	        String sector = job.getJobProvider().getSector();
	        sectorTotalWages.put(sector, sectorTotalWages.getOrDefault(sector, 0.0) + job.getPayPerHour());
	        sectorJobCount.put(sector, sectorJobCount.getOrDefault(sector, 0) + 1);
	    }
	    
	    Map<String, Double> sectorAverageWages = new HashMap<>();
	    for (String sector : sectorTotalWages.keySet()) {
	        sectorAverageWages.put(sector, sectorTotalWages.get(sector) / sectorJobCount.get(sector));
	    }
	    
	    return sectorAverageWages;
	}

	// calculate average ages of employees in each sector
	private Map<String, Double> averageAgesOfEmployeesInEachSector(List<EmploymentRecord> employees , List<Job> allJobs) {
	    Map<String, Double> sectorTotalAges = new HashMap<>();//sector and age
	    Map<String, Integer> sectorEmployeeCount = new HashMap<>();//sector and no of emp
	    
	    for (EmploymentRecord record : employees) {
	        String sector = record.getJobProvider().getSector();
	        int age = LocalDate.now().getYear() - record.getJobSeeker().getBirthDate().getYear();
	        sectorTotalAges.put(sector, sectorTotalAges.getOrDefault(sector, 0.0) + age);
	        sectorEmployeeCount.put(sector, sectorEmployeeCount.getOrDefault(sector, 0) + 1);
	    }
	    
	    Map<String, Double> sectorAverageAges = new HashMap<>();
	    for (String sector : sectorTotalAges.keySet()) {
	        
	        if(sectorEmployeeCount.get(sector) == 0) {
	        	sectorAverageAges.put(sector,0.0);
	        }else {
	        	sectorAverageAges.put(sector, sectorTotalAges.get(sector) / sectorEmployeeCount.get(sector));
	        }
	    }
	    for(Job job: allJobs ) {
	    	String sector = job.getJobProvider().getSector();
	    	
	    	if(!sectorAverageAges.containsKey(sector)) {
	    		sectorAverageAges.put(sector, 0.0);
	    	}
	    }
	    
	    return sectorAverageAges;
	}

	// calculate gender ratio (Male:Female) in each sector
	private Map<String, String> genderRatioInEachSector(List<EmploymentRecord> employees , List<Job> allJobs) {
	    Map<String, Long> maleCountPerSector = new HashMap<>();
	    Map<String, Long> femaleCountPerSector = new HashMap<>();
	    
	    for (EmploymentRecord record : employees) {
	        String sector = record.getJobProvider().getSector();
	        if (record.getJobSeeker().getGender() == 'M') {
	            maleCountPerSector.put(sector, maleCountPerSector.getOrDefault(sector, 0L) + 1);
	        } else if (record.getJobSeeker().getGender() == 'F') {
	            femaleCountPerSector.put(sector, femaleCountPerSector.getOrDefault(sector, 0L) + 1);
	        }
	    }
	    
	    Map<String, String> genderRatio = new HashMap<>();
	    
	    if(maleCountPerSector.size() >= femaleCountPerSector.size()) {
		    for (String sector : maleCountPerSector.keySet()) {
		        long males = maleCountPerSector.getOrDefault(sector, 0L);
		        long females = femaleCountPerSector.getOrDefault(sector, 0L);
		        if(females == 0) {
		        	 genderRatio.put(sector, males + ":" + 0);
		        }else {
		        	 genderRatio.put(sector, males + ":" + females);
				}
		       
		    }
	    }else {
	    	for (String sector : femaleCountPerSector.keySet()) {
		        long males = maleCountPerSector.getOrDefault(sector, 0L);
		        long females = femaleCountPerSector.getOrDefault(sector, 0L);
		         genderRatio.put(sector, males + ":" + females);
		    }
	    	for(Job job: allJobs ) {
		    	String sector = job.getJobProvider().getSector();
		    	
		    	if(!genderRatio.containsKey(sector)) {
		    		genderRatio.put(sector, 0 + ":" + 0);
		    	}
		    }
	    }
	    
	    return genderRatio;
	}

	// Method to calculate the number of accepted contracts in each sector
	private Map<String, Long> acceptedContractsInEachSector(List<EmploymentRecord> employees , List<Job> allJobs) {
	    Map<String, Long> sectorAcceptedContracts = new HashMap<>();
	    
	    for (EmploymentRecord record : employees) {
	        String sector = record.getJobProvider().getSector();
	        sectorAcceptedContracts.put(sector, sectorAcceptedContracts.getOrDefault(sector, 0L) + 1);
	    }
	    for(Job job: allJobs ) {
	    	String sector = job.getJobProvider().getSector();
	    	
	    	if(!sectorAcceptedContracts.containsKey(sector)) {
	    		sectorAcceptedContracts.put(sector, 0L);
	    	}
	    }
	    return sectorAcceptedContracts;
	}


	
	//generate Organiztions report
	private void generateOrganizationReport() {
    	
        System.out.println("The Organization Report: ");
        
        List<JobProvider> providers = JobProvider.getAllProviders();
        List<JobSeeker> seekers = JobSeeker.getAllJobSeekers();
        List<Job> allJobs = Job.getJobList();
        
        //The number of contributing organizations in the system
        Set<String> organizations = ContributingOrganizationsInSystem(providers);
        System.out.println("The number of contributing organizations in the system: " + organizations.size());
        
        //The types of organizations in the system (Company or Institute)
        Set<String> organizationTypes = TypesOfOrganizationsInSystem(providers);
        System.out.println("The types of organizations in the system: " + organizationTypes);
        
        //The number of people an organization employed in a time frame
        String theMostSoughtOrg = mostSoughtAfterOrganization(organizations, allJobs);
        if(theMostSoughtOrg == "")
        {
        	System.out.println("No organization in the system");
        }else {
        	System.out.println("The most sought-after organizations is " + theMostSoughtOrg);
        }
        
        long noOfEmployeeOfOrgInTimeFrame = NoOfPeopleEmployedInTimeFrame();
        System.out.println("The number of people employed in a time frame =" + noOfEmployeeOfOrgInTimeFrame);
        
        //Geographical distribution of organizations
        Map<String, List<String>> locationMap = geographicalDistributionOfOrganizations(allJobs);
        // Display the distribution
        System.out.println("Geographical Distribution of Organizations:");
        for (Map.Entry<String, List<String>> entry : locationMap.entrySet()) {
            System.out.println("Location: " + entry.getKey());
            System.out.println("Organizations: " + entry.getValue());
            System.out.println();
        }
        
        //Average wages of employees in each organization
        Map<String, Double> avaregeWageOfOrganization = avaregeWageOfEachOrganization(organizations, allJobs);
        for (Map.Entry<String, Double> entry : avaregeWageOfOrganization.entrySet()) {
			String key = entry.getKey();
			Double val = entry.getValue();
			System.out.println("The average wage in organization: "+key+"= $"+ val);
		}
        
        //Average ages of employees in each organization
        Map<String, Double> avaregeageOfOrganization = averageAgesOfEmployeesInEachOrganization(organizations);
        for (Map.Entry<String, Double> entry : avaregeageOfOrganization.entrySet()) {
			String key = entry.getKey();
			Double val = entry.getValue();
			System.out.println("The average age in organization: "+key+"= "+ val);
		}
        
        //The ratio of male to female employees in each organization
        Map<String, String> ratioBetweenMaleAndFemaleInEachOrg = RatioBetweenMaleTofemale(organizations);
        for (Map.Entry<String, String> entry : ratioBetweenMaleAndFemaleInEachOrg.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			System.out.println("The ratio between male and female in organization: "+key+"= "+ val);
		}
        
        //Number of accepted contracts in each organization
        Map<String, Long> acceptedContractInEachOrg = NoOfAcceptedContractInEachOrg(organizations);
        for (Map.Entry<String, Long> entry : acceptedContractInEachOrg.entrySet()) {
			String key = entry.getKey();
			Long val = entry.getValue();
			System.out.println("The number of accepted contract in organization: "+key+"= "+ val);
		}
    }
    private Set<String> ContributingOrganizationsInSystem(List<JobProvider> providers) {
    	Set<String> organizations = new HashSet<>();

        for (JobProvider provider : providers) {
            organizations.add(provider.getOrganizationName().toLowerCase());
        }
        return organizations;
    }
    private Set<String> TypesOfOrganizationsInSystem(List<JobProvider> providers) {
        Set<String> organizationTypes = new HashSet<>();

        for (JobProvider provider : providers) {
            organizationTypes.add(provider.getOrganizationType().toLowerCase());
        }
        return organizationTypes;
    }
    private String mostSoughtAfterOrganization(Set<String> organizations ,List<Job> jobs) {
    	Map<String, Integer> organizationApplicationCount = new HashMap<String, Integer>();//name of organization and the number of application for various jobs in it
    	
    	for (Job job : jobs) {
			String organizationName = job.getJobProvider().getOrganizationName();
			int applicantsCount = Application.getApplicationsListOfSpecificJob(job).size();
			organizationApplicationCount.put(organizationName, organizationApplicationCount.getOrDefault(organizationName, 0)+applicantsCount);
		}
    	
    	String theMostSoughtAfterOrganization = "";
    	int maxNumberOfApplicant = -1;
    	for(Map.Entry<String, Integer> entry : organizationApplicationCount.entrySet()) {
    		if (entry.getValue() > maxNumberOfApplicant) {
    			maxNumberOfApplicant = entry.getValue();
    			theMostSoughtAfterOrganization = entry.getKey();
            }
    	}
    	
    	return theMostSoughtAfterOrganization;
	}
    
    private long NoOfPeopleEmployedInTimeFrame() {
    	scanner = new Scanner(System.in);
        LocalDate startDate , endDate;
        do {
	        System.out.print("Enter the start date (yyyy-mm-dd): ");
	        startDate = LocalDate.parse(scanner.nextLine());
	        
	        System.out.print("Enter the end date (yyyy-mm-dd): ");
	        endDate = LocalDate.parse(scanner.nextLine());
	        
	        // Check if startDate is after endDate
	        if (startDate.isAfter(endDate)) {
	            System.out.println("Start date cannot be after end date. Please try again.");
	        }
        }while(startDate.isAfter(endDate));
        
        String organization;
        do {
        	organization = scanner.nextLine();
        	if (organization.isEmpty()) {
                System.out.println("Organization name cannot be empty. Please try again.");
            }
        }while(organization.isEmpty());
        
        
        
        List<EmploymentRecord> employees = EmploymentRecord.getAllEmploymentRecords();
        
        final String finalOrganization = organization;
        final LocalDate finalStartDate =startDate;
        final LocalDate finalEndDate =endDate;
        
        long countEmployees = employees.stream().filter(record -> record.getJobProvider().getOrganizationName().equalsIgnoreCase(finalOrganization) && !record.getHireDate().isAfter(finalEndDate) && !record.getHireDate().isBefore(finalStartDate)).count();
        
        return countEmployees;
	}
    private Map<String, List<String>>geographicalDistributionOfOrganizations(List<Job> allJobs) {
    	 Map<String, List<String>> locationMap = new HashMap<>();

         for (Job job : allJobs) {
             String location = job.getCity();
             locationMap.putIfAbsent(location, new ArrayList<>());
             locationMap.get(location).add(job.getJobProvider().getOrganizationName());
         }
         
         return locationMap;
    }
  //return org and avergae wage
    private Map<String, Double> avaregeWageOfEachOrganization(Set<String> organizations ,List<Job> jobs){
    	Map<String, Double> orgAverageWage = new HashMap<>();
    	
    	List<EmploymentRecord> employees = EmploymentRecord.getAllEmploymentRecords();
    	for (String org : organizations) {
    		double wage = 0;
			long countJobs = 0;
			for (Job job : jobs) {
				if(job.getJobProvider().getOrganizationName().equalsIgnoreCase(org)) {
					wage+=job.getPayPerHour();
					countJobs++;
				}
			}
			
			if (countJobs > 0) {
				orgAverageWage.put(org, wage / countJobs);
	        } else {
	            orgAverageWage.put(org, 0.0); 
	        }
		}
    	return orgAverageWage;
    }
    //return org and avergae age
    private Map<String, Double> averageAgesOfEmployeesInEachOrganization(Set<String> organizations) {
        Map<String, Double> orgAverageAges = new HashMap<>();

        List<EmploymentRecord> employees = EmploymentRecord.getAllEmploymentRecords();

        for (String org : organizations) {
            double totalAge = 0;
            long employeeCount = employees.stream()
                .filter(record -> record.getJobProvider().getOrganizationName().equalsIgnoreCase(org))
                .count();

            totalAge = employees.stream()
                .filter(record -> record.getJobProvider().getOrganizationName().equalsIgnoreCase(org))
                .mapToInt(record -> {
                    return LocalDate.now().getYear() - record.getJobSeeker().getBirthDate().getYear() ;//age of the employee
                }).sum();

            if (employeeCount > 0) {
                orgAverageAges.put(org, totalAge / employeeCount);
            } else {
                orgAverageAges.put(org, 0.0);
            }
        }

        return orgAverageAges;
    }
    
    //return org and ratio as string
    private Map<String, String> RatioBetweenMaleTofemale(Set<String> organizations) {
		Map<String, String> ratioBetweenMandFInEachOrgMap = new HashMap<>();
		List<EmploymentRecord> employees = EmploymentRecord.getAllEmploymentRecords();
		
		for (String org : organizations) {
			
			long male = employees.stream().filter(record -> record.getJobProvider().getOrganizationName().equalsIgnoreCase(org) && record.getJobSeeker().getGender() == 'M' ).count();
			long female = employees.stream().filter(record -> record.getJobProvider().getOrganizationName().equalsIgnoreCase(org) && record.getJobSeeker().getGender() == 'F' ).count();
			if(female == 0) {
				ratioBetweenMandFInEachOrgMap.put(org, male +":0");
			}else {
				ratioBetweenMandFInEachOrgMap.put(org, male + ":" + female);
			}
		}
		
		return ratioBetweenMandFInEachOrgMap;
	}
    
    private Map<String, Long> NoOfAcceptedContractInEachOrg(Set<String> organizations) {
		Map<String , Long> noOfAcceptedContractInEachOrg = new HashMap<>();

		List<EmploymentRecord> employees = EmploymentRecord.getAllEmploymentRecords();
		for (String org : organizations) {
			long acceptedApplicant = employees.stream().filter(record -> record.getJobProvider().getOrganizationName().equalsIgnoreCase(org)).count();
			noOfAcceptedContractInEachOrg.put(org, acceptedApplicant);
		}
		return noOfAcceptedContractInEachOrg;
	}

}
