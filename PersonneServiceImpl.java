package com.example.demotp.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PersonneServiceImpl implements PersonneServiceItf{
@Autowired
	
	private PersonneRepository repo;
	@Override
	public void creerCompte(String nom, String prenom, String email, String password){
		repo.save(new Personne(nom, prenom, email, password));
		}
	@Override
	public void seConnecter(String email, String password) {
		repo.save(new Personne (email, password));
}
	

	
	@Override
	public Iterable<Personne> getAllAutors() {
		return repo.findAll();
	}
	

	}
