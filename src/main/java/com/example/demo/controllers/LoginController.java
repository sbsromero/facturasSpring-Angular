package com.example.demo.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,RedirectAttributes flash) {
		
		if (principal != null) {
			flash.addFlashAttribute("info", "Ya has iniciado sesión");
			return "redirect:/";
		}
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito");
		}
		if (error != null) {
			model.addAttribute("error",
					"Error en el login: Nombre de usuario o contraseña incorrectos, por favor vuelta a intentarlo");
		}
		return "login";
	}
}
