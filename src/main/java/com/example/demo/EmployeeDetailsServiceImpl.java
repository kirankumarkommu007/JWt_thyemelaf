package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepo empRepo;
    
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employees emp = empRepo.findByUsername(username);
        if (emp == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(emp.getUsername())
                .password(emp.getPassword())
                .roles(emp.getRole())
                .build();
    }
}
