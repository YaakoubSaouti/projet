package be.saouti.models;

import java.time.LocalDate;
import java.util.regex.Pattern;

import be.saouti.daos.PlayerDAO;

public class Player extends User{
	//Members
	
	private int credit;
	private String pseudo;
	private LocalDate registrationDate;
	private LocalDate dateOfBirth;
	
	//Getters and Setters
	
	public int getCredit() { return credit; }
	public String getPseudo() { return pseudo; }
	public LocalDate getRegistrationDate() { return registrationDate; }
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	
	public void setCredit(int credit) { this.credit = credit; }
	public void setPseudo(String pseudo) throws Exception{ 
		if(!Pattern.matches("^[A-Za-z][A-Za-z0-9_]{2,29}$", pseudo))
			throw new Exception(
					 "The pseudo should be between 3 and 30 character,"
					+"\nshould start whith a letter and can only contain letters,numbers and '_'!");  
		this.pseudo = pseudo;
	}
	public void setRegistrationDate(LocalDate registrationDate){ 
		this.registrationDate = registrationDate;
	}
	public void setDateOfBirth(LocalDate dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	
	//Constructor
	public Player() {}
	public Player(int id,String username,String password,
			int credit,String pseudo,LocalDate registrationDate,LocalDate dateOfBirth){
		super(id,username, password);
		this.credit = credit;
		this.pseudo = pseudo;
		this.registrationDate = registrationDate;
		this.dateOfBirth = dateOfBirth;
	}
	
	//Methods
	
}
