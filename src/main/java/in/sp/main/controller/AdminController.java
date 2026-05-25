/*package in.sp.main.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
public class AdminController {
	
	
	@Value("${admin.login.id}")
	private String AdminEmail;
	
	@Value("${admin.login.password}")
	private String AdminPassword;

    // ------------- Login Page Starts----------- //
	@GetMapping("/adminLogin")
	public String openAdminLoginPage() {

		return "admin-login";
	}
	
	@PostMapping("/adminLoginForm")
	public String adminLoginForm(@RequestParam("adminemail") String aemail, @RequestParam("adminpass") String apass,
			Model model) {

		if (aemail.equals(AdminEmail) && apass.equals(AdminPassword)) {

			return "admin-profile";
		} else {

			model.addAttribute("errormsg", "Invalid Email or Password : Please check Again");
			return "admin-login";
		}
	}
	// ------------- Login Page Ends----------- //

	
	
	
	// -------------- Opening admin profile page Starts ------//
	@GetMapping("/adminProfile")
	public String openAdminProfilePage() {

		return "admin-profile";
	}
	// -------------  Admin Page Ends----------- //
	
	
	@GetMapping("/adminFeedback")
	public String openAdminFeedbackPage() {

		return "admin-feedback";
	}
	
	@GetMapping("/adminLogout")
	public String openAdminLogoutPage() {

		return "admin-login";
	}
	
	

}
*/
package in.sp.main.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



@Controller

public class AdminController {
	
	
	@Value("${admin.login.id}")
	private String AdminEmail;
	
	@Value("${admin.login.password}")
	private String AdminPassword;

    // ------------- Login Page Starts----------- //
	@GetMapping("/adminLogin")
	public String openAdminLoginPage() {

		return "admin-login";
	}
	
	@PostMapping("/adminLoginForm")
	public String adminLoginForm( @RequestParam("adminemail") String aemail,
	                              @RequestParam("adminpass") String apass,
	                              Model model, HttpSession session) {

	    if (aemail.equals(AdminEmail) &&
	        apass.equals(AdminPassword)) {

	        session.setAttribute("adminSession", aemail);

	        return "redirect:/adminProfile";

	    } else {

	        model.addAttribute("errormsg", "Invalid Email or Password");
            return "admin-login";
	    }
	}
	// ------------- Login Page Ends----------- //

	
	
	
	// -------------- Opening admin profile page Starts ------//
	@GetMapping("/adminProfile")
	public String openAdminProfilePage(HttpSession session) {

	    String admin =
	        (String) session.getAttribute("adminSession");

	    if(admin == null) {
	        return "redirect:/adminLogin";
	    }

	    return "admin-profile";
	}
	// -------------  Admin Page Ends----------- //
	
	
	@GetMapping("/adminFeedback")
	public String openAdminFeedbackPage(HttpSession session) {

	    String admin =
	        (String) session.getAttribute("adminSession");

	    if(admin == null) {
	        return "redirect:/adminLogin";
	    }

	    return "admin-feedback";
	}
	
	@GetMapping("/adminLogout")
	public String openAdminLogoutPage(HttpSession session) {

	    session.invalidate();

	    return "redirect:/adminLogin";
	}
	
	

}

