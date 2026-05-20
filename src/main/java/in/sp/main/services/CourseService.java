package in.sp.main.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.sp.main.entities.Course;
import in.sp.main.repositories.CourseRepository;

@Service
public class CourseService {

	private String UPLOAD_DIR = "src/main/resources/static/uploads/";
	private String IMAGE_URL = "http://localhost:8080/uploads/";

	@Autowired
	private CourseRepository courseRepository;

	// ------------------ Getting All Course Information From the Database with the
	// help of repository layer -------------------

	public List<Course> getAllCourseDetails() {
		return courseRepository.findAll();
	}

	// ----Pageable is used to specify pagination information i.e. page number, page
	// size, sort order etc.
	// ---------------- When Querying with database.

	// Page represent the chunk of data that is fetched according to pagination
	// parameters defined by pageable.

	public Page<Course> getAllCourseDetailsByPagination(Pageable pageable) {
		return courseRepository.findAll(pageable);
	}

	// --------- add Course and image Starts ------------//
	public void addCourse(Course course, MultipartFile courseImg) throws IOException {
		String imgName = courseImg.getOriginalFilename();
		Path imgPath = Paths.get(UPLOAD_DIR + imgName);
		Files.write(imgPath, courseImg.getBytes());

		String imgUrl = IMAGE_URL + imgName;
		course.setImageUrl(imgUrl);

		courseRepository.save(course);
	}
	// --------- add Course and image Ends ------------//

	// -------- to get Course details -------------//
	public Course getCourseDetails(String courseName) {
		return courseRepository.findByName(courseName);
	}
	// -------- to get Course details -------------//

	public void updateCourseDetails(Course course) {
		courseRepository.save(course);
	}

	public void deleteCourseDetails(String courseName) {
		Course course = courseRepository.findByName(courseName);
		if (course != null) {
			courseRepository.delete(course);
		} else {
			throw new RuntimeException("Course Not Found with: " + courseName);
		}
	}

	// ------- For getting Course Name Only used in employee sell course part over
	// admin module----

	public List<String> getAllCoursesName() {
		List<Course> courseList = courseRepository.findAll();
		List<String> courseNameList = new ArrayList<>();
		for (Course course : courseList) {
			String courseName = course.getName();
			courseNameList.add(courseName);
		}
		return courseNameList;
	}

	/*------------------can Also Use Single line of Code Using Java 8 for the above method------------------------
	 * 
	---   return courseRepository.findAll().stream().map(Course::getName).collect(Collectors.toList());
	*/
}
