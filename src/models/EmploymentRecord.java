package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmploymentRecord {
	private static List<EmploymentRecord> employmentRecord = new ArrayList<>();
	private JobProvider jobProvider;
    private Job job;
    private JobSeeker jobSeeker;
    private LocalDate hireDate; 

    public EmploymentRecord(JobProvider jobProvider, Job job, JobSeeker jobSeeker, LocalDate hireDate) {
        this.jobProvider = jobProvider;
        this.job = job;
        this.jobSeeker = jobSeeker;
        this.hireDate = hireDate;
    }
    
    public JobProvider getJobProvider() {
		return jobProvider;
	}

	public void setJobProvider(JobProvider jobProvider) {
		this.jobProvider = jobProvider;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public static boolean addEmployee(EmploymentRecord employee) {
        for (EmploymentRecord record : employmentRecord) {
            if (record.getJobProvider().equals(employee.getJobProvider()) &&
                record.getJob().equals(employee.getJob()) &&
                record.getJobSeeker().equals(employee.getJobSeeker())) {
                System.out.println("Employment record already exists for this job seeker, job, and provider.");
                return false; 
            }
        }
        
        employmentRecord.add(employee);
        return true;
    }
    public static List<EmploymentRecord> getAllEmploymentRecords() {
		return employmentRecord;
	}
    public static boolean removeEmployee(Job job , JobProvider provider , JobSeeker seeker) {
	    for(EmploymentRecord emp : employmentRecord){
	    	if(emp.getJob() == job && emp.getJobProvider() == provider && emp.getJobSeeker() == seeker) {
	    		employmentRecord.remove(emp);
	    		return true;
	    	}
	    }
		return false;
	}
    public static int countEmployeesInTimeframe(JobProvider jobProvider, LocalDate startDate, LocalDate endDate) {
        int count = 0;
        
        for (EmploymentRecord record : employmentRecord) {
            if (record.getJobProvider().equals(jobProvider) && 
                (record.getHireDate().isEqual(startDate) || record.getHireDate().isEqual(endDate) || 
                (record.getHireDate().isAfter(startDate) && record.getHireDate().isBefore(endDate)))) {
                count++;
            }
        }
        
        return count;
    }

	public static void editEmploymentRecord(JobProvider oldJobProvider, JobProvider newJobProvider) {
		for (EmploymentRecord employmentRecord2 : employmentRecord) {
			if(employmentRecord2.getJobProvider() == oldJobProvider) {
				EmploymentRecord emp = employmentRecord2;
				emp.setJobProvider(newJobProvider);
				employmentRecord.set(employmentRecord.indexOf(employmentRecord2), emp);
			}
		}
		
	}
}
