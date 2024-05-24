package com.example.demo.controllers;


import com.example.demo.models.Employees;
import com.example.demo.repos.EmployeeRepo;
import com.example.demo.service.EmployeeServiceImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final EmployeeRepo employeeRepo;

    private final EmployeeServiceImpl employeeService;

    public AdminController(EmployeeServiceImpl employeeService, EmployeeRepo employeeRepo) {
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
    }

   

    @GetMapping("/admin/home")
    public String page(Model model) {
        List<Employees> empList = employeeService.getAllEmployees();
        model.addAttribute("Employee", empList);
        return "/views/pages/homepage";
    }

    @GetMapping("/admin/add")
    public String AddEmpForm(Model model) {
        model.addAttribute("Employee", new Employees());
        return "/views/fragments/addemp";
    }

    @PostMapping("/admin/add")
    public String addEmp(@ModelAttribute("Employee") Employees employee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String firstName = authentication.getName();
        Employees user = employeeRepo.findByFirstname(firstName);
        employeeService.addEmployee(employee);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/edit/{id}")
    public String editEmpForm(@PathVariable Integer id, Model model) {
        Optional<Employees> optionalEmp = employeeService.getEmployeeById(id);
        if (optionalEmp.isPresent()) {
            model.addAttribute("Employee", optionalEmp.get());
            return "/views/fragments/editemp";
        } else {
            return "redirect:/admin/home";
        }
    }

    @PostMapping("/admin/edit/{id}")
    public String editEmp(@PathVariable Integer id, @ModelAttribute("Employee") Employees employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteEmp(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin/home";
    }
}
