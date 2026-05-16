package in.sp.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	@Pattern(regexp="^[a-z,A-Z ]{5,25}$",message="Invalid Name Format")
	private String ename;
	
	@Column
	@Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message="Invalid Email Format")
	private String eemail;
	
	@Column
	@Pattern(regexp="^[a-z,A-Z,0-9]{5,25}$",message="Invalid Password Format")
	private String epassword;
	
	@Column
	@Pattern(regexp="^[0-9]{10}$",message="Invalid PhoneNo Format")
	private String ephoneno;
	
	@Column
	@Pattern(regexp="^[a-z,A-Z ]{3,25}$",message="Invalid City Format")
	private String ecity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getEemail() {
		return eemail;
	}

	public void setEemail(String eemail) {
		this.eemail = eemail;
	}

	public String getEpassword() {
		return epassword;
	}

	public void setEpassword(String epassword) {
		this.epassword = epassword;
	}

	public String getEphoneno() {
		return ephoneno;
	}

	public void setEphoneno(String ephoneno) {
		this.ephoneno = ephoneno;
	}

	public String getEcity() {
		return ecity;
	}

	public void setEcity(String ecity) {
		this.ecity = ecity;
	}
	
	

}

