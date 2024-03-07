package com.example.demotp.bib;

import jakarta.persistence.Entity;


import jakarta.persistence.Id;

@Entity
public class Personne {
	//private long id;
	 private String nom;
	    private String prenom;
	    private String email;
	    private String password;
	    public Personne(String nom, String prenom, String email, String password) {
	        
	    	
	    	this.nom = nom;
	        this.prenom = prenom;
	        this.email=email;
	        this.password= password;
	    }
public Personne( String email, String password) {
	        
	    	
	    
	        this.email=email;
	        this.password= password;
	    }
	   
	    public String getNom() {
	        return nom;
	    }

	    // Setter for Nom
	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    // Getter for Prénom
	    public String getPrenom() {
	        return prenom;
	    }

	    // Setter for prenom
	    public void setPrenom(String prenom) {
	        this.prenom = prenom;
	    }
@Id
	    public String getEmail() {
	        return email;
	    }

	    // Setter for email
	    public void setEmail(String email) {
	        this.email = email;
	    }
	    public String getPassword() {
	        return password;
	    }

	    // Setter for Password
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    /*public long getId() {
	        return id;
	    }
	    public void setId(long id) {
	        this.id = id;
	    }*/
	    public Personne() {
	}

}
