package be.saouti.models;

import java.time.LocalDate;


import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.BookingDAO;

public class Booking {
	//Members
	private int id;
	private LocalDate bookingDate;
	private Player player;
	
	
	//Constructor
	public Booking() {}
	public Booking(int id, Player player,LocalDate bookingDate) {
		this.id = id;
		this.bookingDate = bookingDate;
		this.player = player;
	}
	
	//Getters and Setters
	public LocalDate getBookingDate() { return bookingDate; }
	public int getId() { return id; }
	public Player getPlayer() { return player; }
	
	public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
	public void setId(int id) { this.id = id; }
	public void setPlayer(Player player) { this.player = player; }
	//Methods
	//Static
	
	//Non Static
	public boolean delete(){
		return new BookingDAO(VideoGamesConnection.getInstance()).delete(this);
	}
	
	public static Booking getById(int id) {
		return new BookingDAO(VideoGamesConnection.getInstance()).find(id);
	}
	
}
