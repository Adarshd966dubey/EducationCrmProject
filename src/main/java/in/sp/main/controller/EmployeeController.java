package in.sp.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sp.main.entities.Employee;
import in.sp.main.services.EmployeeService;



@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employeeManagement")
	public String OpenEmployeeManagementPage(Model model, 
			@RequestParam(name="page",defaultValue="0") int page, 
	        @RequestParam(name="size", defaultValue="3")int size ){
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Employee> employeePage = employeeService.getAllEmployeeDetailsByPagination(pageable);
		model.addAttribute("employeePage", employeePage);
		
		return "employee-management";
	}

}
