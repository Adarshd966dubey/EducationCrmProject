package in.sp.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Employee;
import in.sp.main.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	// To get All Employees Details Without Any Specific Condition.
	public List<Employee> getAllEmployeeDetails(){
		return employeeRepository.findAll();
	}
	
	
	// To get All Employees Details Based on Pagination.
	public Page<Employee> getAllEmployeeDetailsByPagination(Pageable pageable){
		return employeeRepository.findAll(pageable);
	}
	
	
	// for adding Employee normally.
	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);	
	}
	
	// for getting Employee by their email
	public Employee getEmployeeDetail(String empoyeeEmail) {
		return employeeRepository.findByEemail(empoyeeEmail);
	}
	
	// for save updated Employee.
	public void updateEmployeeDetail(Employee employee) {
		
	   employeeRepository.save(employee);
	}

}
