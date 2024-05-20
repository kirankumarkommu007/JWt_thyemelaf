package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employees, Long> {

	Employees findByUsername(String username);

	Employees findByUsernameAndId(String username, Long id);
}
