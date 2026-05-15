package in.sp.main.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.entities.Course;
import in.sp.main.services.CourseService;

@Controller
public class CourseController {
	
	private String UPLOAD_DIR = "src/main/resources/static/uploads/";
	private String IMAGE_URL = "http://localhost:8080/uploads/";
	
	@Autowired
	private CourseService courseService;
	
	
	// ------ Course Management Starts --------------- //
	@GetMapping("/courseManagement")
	public String openCourseManagementPage(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		
		
        Pageable pageable = PageRequest.of(page, size);
		
		Page<Course> coursesPage = courseService.getAllCourseDetailsByPagination(pageable);
		model.addAttribute("coursesPage", coursesPage);

		return "course-management";
	}
	// ------ Course Management Ends --------------- //
	
	
	
	

	// ------ Add Course Parts Starts --------------- //
	@GetMapping("/addCourse")
	public String openAddCoursePage(Model model) {
		model.addAttribute("course", new Course());
		return "add-course";
	}

	@PostMapping("/addCourseForm")
	public String addCourseForm(@ModelAttribute("course") Course course,
			@RequestParam("courseImg") MultipartFile courseImg, Model model) {
		try {
			courseService.addCourse(course, courseImg);
			model.addAttribute("successMsg", "Course Added successfully ...");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errormsg", "Not able to Add Course Try Again later");
		}
		return "add-course";
	}
	// ----------------- Add Course End -------------------------- //
	
	
	
	

	// -------------- Edit Course Start ---------------------------//
	@GetMapping("/editCourse")
	public String openEditCoursePage(@RequestParam("courseName") String courseName, Model model) {
		Course course = courseService.getCourseDetails(courseName);
		model.addAttribute("course", course);
		model.addAttribute("newCourseObj", new Course());
		return "edit-course";
	}

	@PostMapping("/updateCourseDetailsForm")
	public String updateCourseDetailsForm(@ModelAttribute("newCourseObj") Course newCourseObj,
			@RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes) {

		try {
			Course oldCourseObj = courseService.getCourseDetails(newCourseObj.getName());
			newCourseObj.setId(oldCourseObj.getId());

			if (!courseImg.isEmpty()) {

				String imgName = courseImg.getOriginalFilename();
				Path imgPath = Paths.get(UPLOAD_DIR + imgName);
				Files.write(imgPath, courseImg.getBytes());

				String imgUrl = IMAGE_URL + imgName;
				newCourseObj.setImageUrl(imgUrl);

			} else {
				newCourseObj.setImageUrl(oldCourseObj.getImageUrl());
			}
			courseService.updateCourseDetails(newCourseObj);
			redirectAttributes.addFlashAttribute("successMsg", "Your course updated successfully ...");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", "Not able to add course try again later ...");
			e.printStackTrace();
		}

		return "redirect:/courseManagement";
	}
	// ----------- Edit Course Ends --------------- //
	
	
	
	
	// ------------------ delete Course Starts --------------- //
	@GetMapping("/deleteCourseDetails")
	public String deleteCourseDetails(@RequestParam("courseName") String courseName,
			RedirectAttributes redirectAttributes) {
		try {

			courseService.deleteCourseDetails(courseName);
			redirectAttributes.addFlashAttribute("successMsg", "Your course deleted successfully ...");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMsg", "Not able to delete course try again later ...");
			e.printStackTrace();
		}
		return "redirect:/courseManagement";
	}
	// ------------- delete Course Ends --------------- //

}
