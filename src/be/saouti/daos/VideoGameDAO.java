package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import be.saouti.models.Booking;
import be.saouti.models.Copy;
import be.saouti.models.VideoGame;

public class VideoGameDAO implements IVideoGameDAO{

	protected Connection connect = null;
	
	public VideoGameDAO(Connection conn){
		this.connect = conn;
	}

	@Override
	public boolean update(VideoGame videogame) {
		try {
	        connect.setAutoCommit(false);
			PreparedStatement prepare1 = this.connect.prepareStatement(
				"UPDATE videogame "
				+"SET title = ?, price = ?"
				+" WHERE videogame_id = ?"
	        );
			prepare1.setString(1, videogame.getName());
			prepare1.setInt(2, videogame.getCreditCost());
			prepare1.setInt(3, videogame.getId());
			prepare1.executeUpdate();
			for(Booking b : videogame.getBookings()) {
				if(b.getId()  == 0 ) {
					PreparedStatement prepare2 = this.connect.prepareStatement(
						"INSERT INTO booking(player_id,videogame_id,booking_date) VALUES (?,?,?)"
			        );
					prepare2.setInt(1, b.getPlayer().getId());
					prepare2.setInt(2, videogame.getId());
					prepare2.setString(3, b.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));			
					prepare2.executeUpdate();
				}
			}
			for(Copy c : videogame.getCopies()) {
				if(c.getId() == 0) {
					PreparedStatement prepare3 = this.connect.prepareStatement(
						"INSERT INTO copy(videogame_id,player_id) VALUES (?,?)"
			        );
					prepare3.setInt(1, c.getVideogame().getId());
					prepare3.setInt(2, c.getOwner().getId());		
					prepare3.executeUpdate();
				}
			}
			videogame = find(videogame.getId());
	        connect.commit();
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			try {
				connect.rollback();
			} catch (SQLException e1) {
				return false;
			}
			return false;
		}finally {
			try{
				connect.setAutoCommit(true);
			} catch (SQLException e) {
				return false;
			}
		}
	}
	
	@Override
	public List<VideoGame> getAll(){
		List<VideoGame> videogames = new ArrayList<>();
		try{
			ResultSet result = this.connect.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY)
			.executeQuery(
				"SELECT videogame_id"
				+" FROM  videogame"
			);
			while(result.next()){
				videogames.add(find(result.getInt(1)));
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return videogames;
	}

	@Override
	public VideoGame find(int id) {
		VideoGame videogame = null;
		try{
			PreparedStatement prepare = connect.prepareStatement(
				"SELECT vg.title, c.console, vg.price"
				+" FROM  videogame vg, console c"
				+" WHERE vg.console_id = c.console_id AND vg.videogame_id = ?"
            );
			prepare.setInt(1,id);
			ResultSet result = prepare.executeQuery();
			if(result.next()){
				videogame = new VideoGame(
					id,
					result.getString(1),
					result.getString(2),
					result.getInt(3)
				);
				PreparedStatement prepare1 = connect.prepareStatement(
					"SELECT booking_id"
					+" FROM  booking"
					+" WHERE videogame_id = ?"
	            );
				prepare1.setInt(1,id);
				ResultSet result1 = prepare1.executeQuery();
				while(result1.next()) {
					videogame.addBooking(new BookingDAO(this.connect).find(result1.getInt(1)));
				}
				PreparedStatement prepare2 = connect.prepareStatement(
						"SELECT copy_id, player_id"
						+" FROM  copy"
						+" WHERE copy_id = ?"
	            );
				prepare2.setInt(1,id);
				ResultSet result2 = prepare2.executeQuery();
				while(result2.next()) {
					videogame.addCopy(result2.getInt("copy_id"),new PlayerDAO(this.connect).find(result2.getInt("player_id")));
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return videogame;
	}
}
