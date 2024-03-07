package com.example.demotp.bib;

public interface PersonneServiceItf {
	Iterable<Personne>getAllAutors();
    void creerCompte( String nom, String prenom,String email, String password);
    void seConnecter(String email, String password);
   
}
