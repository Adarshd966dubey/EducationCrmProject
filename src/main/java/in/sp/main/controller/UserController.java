package in.sp.main.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import in.sp.main.dto.PurchasedCourse;
import in.sp.main.entities.Course;
import in.sp.main.entities.User;
import in.sp.main.repositories.OrdersRepository;
import in.sp.main.repositories.UserRepository;
import in.sp.main.services.CourseService;
import in.sp.main.services.UserService;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionUser")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CourseService courseService;;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	
	// -------------- First Loading Page Starts----------------------//
	
	@GetMapping({"/","/index"})
	public String OpenIndexPage(Model model, @SessionAttribute(name="sessionUser", required=false) User sessionUser) {
		
		List<Course> courseList = courseService.getAllCourseDetails();
		model.addAttribute("courseList", courseList);
		
		if(sessionUser != null ) {
			List<Object[]> purchasedCoursesList = ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
			List<String> purchasedCoursesNameList =new ArrayList<>();
			for(Object[] course : purchasedCoursesList) {
				String courseName = (String)course[3];
				purchasedCoursesNameList.add(courseName);
			}
			model.addAttribute("purchasedCoursesNameList", purchasedCoursesNameList);
		}
		
		return "index";
	}
	// -------------- First Loading Page Ends----------------------//
	
	
	
	// -------------------- Register Starts -------------------------------------------//
	@GetMapping("/register")
	public String OpenRegisetrPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	@PostMapping("/regForm")
	public String HandleRegForm(@Valid @ModelAttribute("user") User user,BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "register";
		}
		else 
		  {
			try 
			{
				userService.RegisterUserService(user);
				model.addAttribute("successMsg", "User Registered Successfully");
				// return "redirect:/register";
				return "register";
			}
		   catch(Exception e) 
			{
				e.printStackTrace();
				
				model.addAttribute("errorMsg", "User Not Saved Successfully");
				return "error";
			}	
		 }
	}
	// ----------------------- Register Ends ------------------------------------------//
	
	
	
	
	// ----------------------- Login Starts ------------------------------------------//
	@GetMapping("/login")
	public String OpenLoginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/loginForm")
	public String HandleLoginForm(@ModelAttribute("user") User user, Model model) {
		boolean isAuthenticated = userService.LoginUSerService(user.getEmail(), user.getPassword());
		if(isAuthenticated) {
			User AuthenticatedUser = userRepository.findByEmail(user.getEmail());
			model.addAttribute("sessionUser", AuthenticatedUser);
			
			return "user-profile";
		}
		else 
		{
			model.addAttribute("errorMsg", "Incorrect Email or Password");
			return "login";
		}
	}
	// ----------------------- Login Ends ------------------------------------------//
	
	
	
	
	
	// ----------------------- LogOut Start ------------------------------------------//
	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus)
	{
		sessionStatus.setComplete();
		return "login";
	}
	// ----------------------- LogOut Ends ------------------------------------------//
	
	
	
	
	// -----------------------UserProfile Starts------------------------------------------//
	@GetMapping("/userProfile")
	public String openUserProfile() {
		return "user-profile";
	}
	// -----------------------UserProfile Starts------------------------------------------//
	
	
	
	
	// -----------------------MyCourses Starts------------------------------------------//
	@GetMapping("/myCourses")
	public String myCoursesPage(@ModelAttribute("sessionUser") User sessionUser, Model model) {
		
		List<Object[]> pcDbList= ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
		
		List<PurchasedCourse> purchasedCoursesList = new ArrayList<>();
		
		for(Object[] course : pcDbList) {
			
			PurchasedCourse purchasedCourse = new PurchasedCourse();
			
			
			purchasedCourse.setPurchasedOn((String)course[0]);
			purchasedCourse.setDescription((String)course[1]);
			purchasedCourse.setImageUrl((String)course[2]);
			purchasedCourse.setCourseName((String)course[3]);
			purchasedCourse.setUpdatedOn((String)course[4]);
			
			purchasedCoursesList.add(purchasedCourse);
		}
		
		model.addAttribute("purchasedCoursesList", purchasedCoursesList);
		
		return "my-courses";
	}
	// ----------------------- MyCourses Ends ------------------------------------------//
	
}	