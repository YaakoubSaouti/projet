package be.saouti.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.PlayerDAO;

public class Player extends User{
	//Members
	
	private int credit;
	private String pseudo;
	private LocalDate registrationDate;
	private LocalDate dateOfBirth;
	private List<Loan> lended;
	private List<Loan> borrowed;
	private List<Integer> alreadyGivenBirthdayPresent;
	
	//Getters and Setters
	
	public int getCredit() { return credit; }
	public String getPseudo() { return pseudo; }
	public LocalDate getRegistrationDate() { return registrationDate; }
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	public List<Loan> getLended(){ return lended; }
	public List<Loan> getBorrowed(){ return borrowed; }
	
	public void setCredit(int credit) { this.credit = credit; }
	public void setPseudo(String pseudo){ this.pseudo = pseudo;}
	public void setRegistrationDate(LocalDate registrationDate){ 
		this.registrationDate = registrationDate;
	}
	public void setDateOfBirth(LocalDate dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	public void setLended(List<Loan> lended){ this.lended = lended; }
	public void setBorrowed(List<Loan> borrowed){ this.borrowed = borrowed; }
	//Constructor
	public Player() {}
	public Player(int id,String username,String password,
			int credit,String pseudo,LocalDate registrationDate,LocalDate dateOfBirth){
		super(id,username, password);
		this.credit = credit;
		this.pseudo = pseudo;
		this.registrationDate = registrationDate;
		this.dateOfBirth = dateOfBirth;
		lended = new ArrayList<Loan>();
		borrowed = new ArrayList<Loan>();
	}
	
	//Methods
	//Static 
	public static boolean register(String username,String pseudo,String password,LocalDate dateOfBirth) throws Exception{
		if(new PlayerDAO(VideoGamesConnection.getInstance()).find(username) != null) {
			throw new Exception(
			 "This username already exist");
		}
		if(!Pattern.matches("^[A-Za-z][A-Za-z0-9_]{2,29}$", username)) 
			throw new Exception(
			 "The username should be between 2 and 30 character,"
			+"\nshould start whith a letter and can only contain letters,numbers and '_'!");
		if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[=@$!%*#?&])[A-Za-z\\d=@$!%*#?&]{8,}$", password)) 
			throw new Exception(
			 "The password should be at least 8 characters "
			+"\nand should contain a letter,a number and a special character(=@$!%*#?&)"); 
		if(!Pattern.matches("^[A-Za-z][A-Za-z0-9_]{2,29}$", pseudo))
			throw new Exception(
			 "The pseudo should be between 3 and 30 character,"
			+"\nshould start whith a letter and can only contain letters,numbers and '_'!");
		if(LocalDate.from(dateOfBirth).until(LocalDate.now(), ChronoUnit.YEARS)<18){
			throw new Exception(
			 "You are too young to register !\n"
			+"The minimum age is 18");
		}
		return true;
	}
	//Non Static
	public boolean loanAllowed() {
		return credit>0;
	}
	
	public void addBirthdayBonus() {
		LocalDate rd = getRegistrationDate();
		int yrd = rd.getYear();
		LocalDate t = LocalDate.now();
		int yt = rd.getYear();
		for (int i = yrd; i < yt; i++){
			boolean alreadygiven = false;
			for(Integer j : alreadyGivenBirthdayPresent) {
				if((int)j==i){
					alreadygiven = true;
				}
			}
			if(!alreadygiven && (t.isAfter(dateOfBirth) || t.equals(dateOfBirth))) { 
				credit += 2;
				alreadyGivenBirthdayPresent.add(i);
			}
		}
	}
	
	public boolean create(){
		credit = 10;
		registrationDate = LocalDate.now();
		return new PlayerDAO(VideoGamesConnection.getInstance()).create(this);
	}
	
	public boolean update(){
		return new PlayerDAO(VideoGamesConnection.getInstance()).update(this);
	}
	
	//Overrides
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	@Override
	public String toString() {
		return this.getId() + this.getUsername() + this.getPassword();
	}
	@Override
	public boolean equals(Object obj){
		if (obj ==null || obj.getClass()!=this.getClass()) return false;
		else return (((Player)obj).hashCode() == this.hashCode());
	}
	
	public void addLended(Loan loan){
		loan.setLender(this);
		lended.add(loan);
	}
	
	public void addBorrowed(Loan loan) {
		loan.setBorrower(this);
		borrowed.add(loan);
	}

}
