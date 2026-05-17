package in.sp.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.entities.Employee;
import in.sp.main.services.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employeeManagement")
	public String OpenEmployeeManagementPage(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Employee> employeePage = employeeService.getAllEmployeeDetailsByPagination(pageable);
		model.addAttribute("employeePage", employeePage);

		return "employee-management";
	}

	// For Adding Employee Details Starts

	@GetMapping("/addEmployee")
	public String OpenAddEmployeePage(Model model) {
		model.addAttribute("employee", new Employee());
		return "add-employee";
	}

	@PostMapping("/addEmployeeForm")
	public String OpenAddEmployeeForm(@ModelAttribute("employee") Employee employee, Model model) {
		try {
			employeeService.addEmployee(employee);
			model.addAttribute("successMsg", "Employee Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "Employee Added Successfully");
		}
		return "add-employee";
	}

	// For Adding Employee Details Ends

	// For Edit Employee Details Starts
	@GetMapping("/editEmployee")
	public String OpenEditEmployeePage(@RequestParam("employeeEmail") String employeeEmail, Model model) {
		Employee employee = employeeService.getEmployeeDetail(employeeEmail);
		model.addAttribute("employee", employee);
		model.addAttribute("newEmployeeObj", new Employee());
		return "edit-employee";
	}

	@PostMapping("/updateEmployeeDetailsForm")
	public String OpenUpdateEmployeeDetailsFormPage(@ModelAttribute("newEmployeeObj") Employee newEmployeeObj,
			RedirectAttributes redirectAttributes) {
		try {
			Employee oldEmployeeObj = employeeService.getEmployeeDetail(newEmployeeObj.getEemail());
			newEmployeeObj.setId(oldEmployeeObj.getId());
			employeeService.updateEmployeeDetail(newEmployeeObj);
			redirectAttributes.addFlashAttribute("successMsg", "Employee updated successfully ...");
		} catch (Exception e) {

			redirectAttributes.addFlashAttribute("errorMsg", "Employee updated successfully ...");
			e.printStackTrace();
		}
		return "redirect:/employeeManagement";
	}
	
	// ----------- delete methods for employee --------------------
	
	@GetMapping("/deleteEmployeeDetails")
	public String deleteEmployeeDetails(@RequestParam("employeeEmail") String employeeEmail,
			RedirectAttributes redirectAttributes) {
		try {
			employeeService.deleteEmployeeDetails(employeeEmail);  
			redirectAttributes.addFlashAttribute("successMsg", "Your employee deleted successfully ...");
		    } 
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", "Not able to delete employee try again later ...");
			e.printStackTrace();
		    }
		return "redirect:/employeeManagement";
	}

}
