package be.saouti.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.VideoGameDAO;

public class VideoGame {
	//Members
	private int id;
	private String name;
	private String console;
	private int creditCost;
	private List<Booking> bookings = new ArrayList<Booking>(); 
	private List<Copy> copies = new ArrayList<Copy>();
	
	//Constructor
	public VideoGame(int id, String name, String console, int creditCost) {
		 this.id = id;
		 this.name = name;
		 this.console = console;
		 this.creditCost = creditCost;
	}
	//Getters and Setters
	public int getId() { return id; }
	public String getName() { return name; }
	public String getConsole() { return console; }
	public int getCreditCost() { return creditCost; }
	public List<Booking> getBookings(){ return bookings; }
	public List<Copy> getCopies(){ return copies; }
	
	public void setId(int id) { this.id = id; }
	public void setConsole(String console) { this.console = console; }
	public void setName(String name) { this.name = name; }
	public void setCreditCost(int creditCost) throws Exception{ 
		if(creditCost<1) throw new Exception("The credit cost of a video game should be above 0");
		this.creditCost = creditCost;
	}
	public void setBookings(ArrayList<Booking> bookings){
		this.bookings = bookings;
	}
	public void setCopies(List<Copy> copies){ 
		this.copies = copies;
	}
	
	//Methods
	//Static
	public static List<VideoGame> getAll(){
		return new VideoGameDAO(VideoGamesConnection.getInstance()).getAll();
	}

	public static VideoGame getById(int id) {
		return new VideoGameDAO(VideoGamesConnection.getInstance()).find(id);
	}
	//Non Static
	public boolean update(){
		 return new VideoGameDAO(VideoGamesConnection.getInstance()).update(this);
	}
	public void addBooking(Booking booking){
		bookings.add(booking);
	}
	public void removeBooking(Booking booking){
		booking.delete();
		bookings.remove(booking);
	}
	
	public List<Booking> getBookingsOfPlayer(Player player){
		List<Booking> bookings = new ArrayList<Booking>(); 
		for(Booking b : this.bookings) {
			if(b.getPlayer().equals(player)) bookings.add(b);
		}
		return bookings;
	}
	public void addCopy(int id,Player player){
		copies.add(new Copy(id,player,this));
	}
	public void removeCopy(Copy copy){
		copy.delete();
		copies.remove(copy);
	}
	
	public List<Copy> getCopiesOfPlayer(Player player){
		List<Copy> copies = new ArrayList<Copy>(); 
		for(Copy c : this.copies) {
			if(c.getOwner().equals(player)) copies.add(c);
		}
		return copies;
	}
	
	public Booking SelectBooking(){
		if(bookings.size()>0) {
			List<Booking> remaining = new ArrayList<Booking>();
			int maxCredit;
			maxCredit = bookings.get(0).getPlayer().getCredit();
			for(Booking b : bookings){
				int credit = b.getPlayer().getCredit();
				maxCredit = (credit>maxCredit)? credit : maxCredit;
			}
			for(Booking b : bookings) {
				int credit = b.getPlayer().getCredit();
				if(credit==maxCredit) remaining.add(b);
			}
			if(remaining.size()>1){
				List<Booking> remaining1 = new ArrayList<Booking>();
				LocalDate mostAncientBookingDate = remaining.get(0).getBookingDate();
				for(Booking b : remaining){
					LocalDate bookingDate = b.getBookingDate();
					mostAncientBookingDate = (bookingDate.isBefore(mostAncientBookingDate))? bookingDate : mostAncientBookingDate;
				}
				for(Booking b : remaining) {
					LocalDate bookingDate = b.getBookingDate();
					if(bookingDate.isEqual(mostAncientBookingDate)) remaining1.add(b);
				}
				if(remaining1.size()>1){
					List<Booking> remaining2 = new ArrayList<Booking>();
					LocalDate mostAncientPlayer = remaining1.get(0).getPlayer().getRegistrationDate();
					for(Booking b : remaining1){
						LocalDate registrationDate = b.getPlayer().getRegistrationDate();
						mostAncientPlayer = (registrationDate.isBefore(mostAncientPlayer))? registrationDate : mostAncientPlayer;
					}
					for(Booking b : remaining1) {
						LocalDate registrationDate = b.getPlayer().getRegistrationDate();
						if(registrationDate.isEqual(mostAncientPlayer)) remaining2.add(b);
					}
					if(remaining2.size()>1) {
						List<Booking> remaining3 = new ArrayList<Booking>();
						LocalDate olderPlayer = remaining1.get(0).getPlayer().getDateOfBirth();
						for(Booking b : remaining2){
							LocalDate dob = b.getPlayer().getDateOfBirth();
							 olderPlayer = (dob.isBefore(olderPlayer))? dob : olderPlayer;
						}
						for(Booking b : remaining2) {
							LocalDate dob = b.getPlayer().getRegistrationDate();
							if(dob.isEqual(olderPlayer)) remaining3.add(b);
						}
						if(remaining3.size()>1) {
							Random rand = new Random();
							return remaining3.get(rand.nextInt(remaining.size()));
						}else {
							return remaining3.get(0);
						}
					}else {
						return remaining2.get(0);
					}
				}else{
					return remaining1.get(0);
				}
			}else{
				return remaining.get(0);
			}
		}
		return null;
	}
	
	public Copy CopyAvailable() {
		for(Copy c : copies) {
			if (c.isAvailable()){
				return c;
			}
		}
		return null;
	}
	
	@Override
	public String toString(){
		return name + "|" + console;
	}
}
