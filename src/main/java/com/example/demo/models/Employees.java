package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EMS_employees")
@Data
@Getter
@Setter
public class Employees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//personal details
	private String firstname;
	private String middlename;
	private String lastname;
	private LocalDate dateOfBirth;
	private String bloodgroup;
	private String gender;
	private String pan;
	private Long mobile;
	private String email;
	private String fathername;
	private String mothername;
	private String spousename;
	private String maritalStatus;
	private String address;
	
	
	//EducationalDetails
	private String qualification;
	private String branchOfStudy;
	private Long yearOfPassing;
	private String collegeName;
	private String university;
	private String collegeCity;
	private String collegeState;
	private String TechnicalSkills;
	private String technicalCertification;
	private Double cgpa_percentage;

	
	
	//Present employementDetails
	private Long salary;
	private String managerid;
	private String designation;
	private String department;
	private LocalDate dateofJoining;
	private LocalDate dateofLeaving;
	
	
	

	//Previous employementDetails
	private Integer experience;
	private String jobrole;
	private String previouscompany;
	private String uannumber;
	private LocalDate dateofLeavingcompany;
	

	// emergency
	private Long emergencycontact1;
	private Long emergencycontact2;
	private Long emergencyEmail;
	private String emergencycontactperson;
	private String relation;
	
	
	
	//Login
	private String role;
	private String password;

}