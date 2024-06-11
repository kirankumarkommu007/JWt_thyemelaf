package com.example.demo.controllers;

import com.example.demo.models.Employees;
import com.example.demo.repos.EmployeeRepo;
import com.example.demo.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Tag(name = "Admin Controller", description = "Controller for managing employee data by admin")
public class AdminController {

    private final EmployeeRepo employeeRepo;
    private final EmployeeServiceImpl employeeService;

    public AdminController(EmployeeServiceImpl employeeService, EmployeeRepo employeeRepo) {
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
    }

    @Operation(summary = "View admin home page", description = "Displays the admin home page with a list of employees")
    @GetMapping("/admin/home")
    public String page(Model model) {
        List<Employees> empList = employeeService.getAllEmployees();
        model.addAttribute("Employee", empList);
        return "/views/pages/homepage";
    }

    @Operation(summary = "Show add employee form", description = "Displays the form for adding a new employee")
    @GetMapping("/admin/add")
    public String AddEmpForm(Model model) {
        model.addAttribute("Employee", new Employees());
        return "/views/fragments/addemp";
    }

    @Operation(summary = "Add a new employee", description = "Processes the form submission to add a new employee")
    @PostMapping("/admin/add")
    public String addEmp(@ModelAttribute("Employee") Employees employee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String firstName = authentication.getName();
        Employees user = employeeRepo.findByFirstname(firstName);
        employeeService.addEmployee(employee);
        return "redirect:/admin/home";
    }

    @Operation(summary = "Show edit employee form", description = "Displays the form for editing an existing employee")
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

    @Operation(summary = "Edit an existing employee", description = "Processes the form submission to edit an existing employee")
    @PostMapping("/admin/edit/{id}")
    public String editEmp(@PathVariable Integer id, @ModelAttribute("Employee") Employees employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/admin/home";
    }

    @Operation(summary = "Delete an employee", description = "Deletes an existing employee")
    @GetMapping("/admin/delete/{id}")
    public String deleteEmp(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin/home";
    }
}
