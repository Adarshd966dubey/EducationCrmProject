package in.sp.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Pattern(regexp="^[a-z,A-Z ]{5,25}$",message="Invalid Name Format")
	private String name;
	
	
	@Column(nullable = false, unique = true)
	@NotNull
	@Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message="Invalid Email Format")
	private String email;
	
	
	@Column
	@Pattern(regexp="^[a-z,A-Z,0-9]{5,25}$",message="Invalid Password Format")
	private String password;
	
	
	@Pattern(regexp="^[0-9]{10}$",message="Invalid PhoneNo Format")
	@Column
	private String phoneno;
	
	
	@Column
	@Pattern(regexp="^[a-z,A-Z ]{3,25}$",message="Invalid City Format")
	private String city;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
