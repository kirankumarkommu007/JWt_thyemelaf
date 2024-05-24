package com.example.demo.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.security.MyUserDetailsService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PageController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenProvider;

	@Autowired
	private MyUserDetailsService employeeDetailsService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/welcome")
	public String getWelcome() {
		return "welcome";
	}

	@PostMapping("/login")//successfull login generates token and save in cookie
	public String login(@RequestParam String username, @RequestParam String password, Model model,
			HttpServletResponse response) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtTokenProvider.generateToken(userDetails);
			String role = jwtTokenProvider.extractRoleAsString(token); // Assuming you have access to the token

			Cookie cookie = new Cookie("token", token);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
			model.addAttribute("token", token);
			model.addAttribute("username", userDetails.getUsername());
			model.addAttribute("roles", role);
			return "home";
		} catch (Exception e) {
			model.addAttribute("error", "Invalid username or password");
			return "welcome";
		}
	}

	@GetMapping("/home")//main page
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtTokenProvider.generateToken(userDetails);

		String role = jwtTokenProvider.extractRoleAsString(token); // Assuming you have access to the token
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute("roles", role);
		return "home";

	}

	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "admin_dashboard";

	}

	@GetMapping("/user/dashboard")
	public String userDashboard() {
		return "user_dashboard";
	}

}
