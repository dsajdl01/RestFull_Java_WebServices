package model;

import java.util.Date;
/**
* EmployeeForJson Class
*
* Employee class contains different setters and getters methods names that is required for using
* Jackson  objectMapper.readValue() method reader. Therefore EmployeeForJson class is created.
*
* @author David Sajdl
*/
public class EmployeeForJson {

	  private String firstName;
	  private String lastName;
	  private String email;
	  private Integer employeeId;
	  private Date hireDate ;

	    public EmployeeForJson() {
	    }

	    public EmployeeForJson(String firstName, String lastName, String email, Integer employeeId, Date hireDate) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.email = email;
	        this.employeeId = employeeId;
	        this.hireDate = hireDate;
	    }
	    // getter
	    public Integer getEmployeeId() {
	        return employeeId;
	    }
	    public String getFirstName() {
	        return firstName;
	    }
	    public String getLastName() {
	        return lastName;
	    }
	    public String getEmail() {
	        return email;
	    }
	    public Date getHireDate() {
	        return hireDate;
	    }
	    // setter
	    public void setEmployeeId(Integer employeeId) {
	        this.employeeId = employeeId;
	    }
	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }
	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }
	    public void setEmail(String email) {
	        this.email = email;
	    }
	    public void setHireDate(Date hireDate) {
	        this.hireDate = hireDate;
	    }

	    @Override
	    public String toString() {
	        return "Employee { employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email +  ", hireDate=" + hireDate + '}';
	    }
}
