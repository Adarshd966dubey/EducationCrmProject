package in.sp.main.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.entities.Employee;
import in.sp.main.entities.Orders;
import in.sp.main.repositories.EmployeeRepository;
import in.sp.main.services.CourseService;
import in.sp.main.services.EmployeeService;
import in.sp.main.services.OrdersService;

@Controller
@SessionAttributes("sessionEmp")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private OrdersService ordersService;

	@Autowired
	private EmployeeRepository employeeRepository;

	
    /*------   Handle Employee Login Page  -------*/
	@GetMapping("/employeeLogin")
	public String OpenLoginPage() {
		return "employee-login";
	}

	@PostMapping("/empLoginForm")
	public String OpenEmployeeLoginForm(@RequestParam("eemail") String eemail,
			@RequestParam("epassword") String epassword, Model model) {

		boolean isAuthenticated = employeeService.loginEmpService(eemail, epassword);
		if (isAuthenticated) {
			Employee authenticatedEmp = employeeRepository.findByEemail(eemail);
			model.addAttribute("sessionEmp", authenticatedEmp);
			return "employee-profile";
		} else {
			model.addAttribute("errorMsg", "Encorrect Email id or Password");
			return "employee-login";
		}
	}

	
    /*------   Handle EmployeeProfile Page  -------*/
	@GetMapping("/employeeProfile")
	public String OpenEmployeeProfilePage() {
		return "employee-profile";
	}
	
	
	
     /*------   Handle EmployeManagement Page  -------*/
	@GetMapping("/employeeManagement")
	public String OpenEmployeeManagementPage(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Employee> employeePage = employeeService.getAllEmployeeDetailsByPagination(pageable);
		model.addAttribute("employeePage", employeePage);

		return "employee-management";
	}

	/*------  Adding Employee Details Starts -------------*/

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

	/*------  For Adding Employee Details Ends-------------*/

	/*------  Edit Employee Details Starts-------------*/
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
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", "Not able to delete employee try again later ...");
			e.printStackTrace();
		}
		return "redirect:/employeeManagement";
	}

	
	
	// -------------Open sell Course Page--------------
	@GetMapping("/sellCourse")
	public String OpenSellCoursePage(Model model) {
		List<String> courseNameList = courseService.getAllCoursesName();
		model.addAttribute("courseNameList", courseNameList);
		
		String uuidOrderId = UUID.randomUUID().toString();
		model.addAttribute("uuidOrderId", uuidOrderId);
		model.addAttribute("orders", new Orders());
		
		return "sell-course";
	}
	
	//---------- Handling Sell course form ---------------------------
	@PostMapping("/sellCourseForm")
	public String OpenSellCourseFormPage(@ModelAttribute("orders") Orders orders, RedirectAttributes redirectAttributes) {
		
		LocalDate ld = LocalDate.now();
		String pdate = ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalTime lt = LocalTime.now();
		String ptime = lt.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
		String puchased_Date_Time = pdate+" ,"+ptime;
		orders.setDateOfPurchase(puchased_Date_Time);
		
		try {
			ordersService.storeUserOrders(orders);
			redirectAttributes.addFlashAttribute("successMsg","Course provided successfully ...");
		}
		catch(Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMsg","Not able to provide course try After some time...");
		}
		return "redirect:/sellCourse";
	}

}
