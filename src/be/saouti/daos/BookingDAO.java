package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.models.Booking;

public class BookingDAO implements IBookingDAO{
	protected Connection connect = null;
	
	public BookingDAO(Connection conn){
		this.connect = conn;
	}
	
	@Override
	public boolean delete(Booking booking) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "DELETE FROM booking WHERE booking_id = ?"
	        );
			prepare.setInt(1, booking.getId());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Booking find(int id) {
		try {
			Booking booking = null;
			PreparedStatement prepare = connect.prepareStatement(
	            "SELECT * FROM booking WHERE booking_id = ?"
	        );
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			if(result.next()){
				booking = new Booking(
					id,
					new PlayerDAO(this.connect).find(result.getInt("player_id")),
					result.getDate("booking_date").toLocalDate()
				);
			}
			return booking;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
