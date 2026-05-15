package in.sp.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.User;
import in.sp.main.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	//Register User
	
	public void RegisterUserService(User user) {
			userRepository.save(user);
		
	}
	
	
	
	// Login User
	
	public boolean LoginUSerService(String email, String password) {
		User user = userRepository.findByEmail(email);
		if(user!= null) {
			return password.equals(user.getPassword());
		}else {
			return false;
		}
	}

}
