package com.example.demotp.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bib")
public class PersonneController {
	@Autowired
	private PersonneServiceItf service;
	@GetMapping("/home")
	public String home (Model model) {
		Iterable<Personne> personnes = service.getAllAutors();

		model.addAttribute("personne", personnes);
	
		return "/bib/home";
				}
	@PostMapping("/inscription")
	public String inscription(@RequestParam String nom, @RequestParam String prenom, @RequestParam String email, @RequestParam String password, Model model) {
	  
	        service.creerCompte(nom, prenom, email, password);
	        return "redirect:/bib/home";
	    
	}

	
	@PostMapping("/connexion")
	public String connexion ( @RequestParam String email,  @RequestParam String password) {
		service.seConnecter(email, password);
		return "redirect:/bib/home";
	}


}
