package be.saouti.models;

import java.time.LocalDate;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.CopyDAO;

public class Copy {
	//Members
	private int id;
	Player owner;
	Loan currentLoan;
	VideoGame videogame;
	
	//Constructor
	public Copy() {}
	public Copy(int id,Player owner, VideoGame videogame) {
		this.id = id;
		this.owner=owner;
		this.videogame = videogame;
	}
	
	//Getters and Setters
	public int getId() { return id; }
	public Player getOwner() { return owner; }
	public Loan getCurrentLoan() { return currentLoan; } 
	public VideoGame getVideogame() {return videogame; }
	
	public void setId(int id) { this.id = id; }
	public void setOwner(Player owner) { this.owner = owner; }
	public void setCurrentLoan(Loan currentLoan) {this.currentLoan = currentLoan;}
	public void setVideogame(VideoGame videogame) {this.videogame = videogame; }
	//Methods
	//Non Static
	public boolean delete() {
		return new CopyDAO(VideoGamesConnection.getInstance()).delete(this);
	}
	public static Copy getById(int id) {
		return new CopyDAO(VideoGamesConnection.getInstance()).find(id);
	}
	public void releaseCopy() {
		currentLoan.endLoan();
		currentLoan = null;
	}
	
	public void borrow(int id,LocalDate startDate, LocalDate endDate,Player borrower){
		currentLoan =  new Loan(id,owner,borrower,startDate,endDate,this);
	}
	public boolean isAvailable() {
		return currentLoan==null;
	}
}
