package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.saouti.models.VideoGame;

public class VideoGameDAO implements IVideoGameDAO{

	protected Connection connect = null;
	
	public VideoGameDAO(Connection conn){
		this.connect = conn;
	}

	@Override
	public boolean update(VideoGame videogame) {
		try{
			PreparedStatement prepare = connect.prepareStatement(
	            "UPDATE videogame "
	    		+"SET title = ?, price = ?"
        		+" WHERE videogame_id = ?"
	        );
			prepare.setString(1, videogame.getName());
			prepare.setInt(2, videogame.getCreditCost());
			prepare.setInt(3, videogame.getId());
			prepare.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public List<VideoGame> getAll(){
		List<VideoGame> videogames = new ArrayList<>();
		try{
			ResultSet result = this.connect.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY)
			.executeQuery(
				"SELECT vg.videogame_id, vg.title, c.console, vg.price"
				+" FROM  videogame vg, console c"
				+" WHERE vg.console_id = c.console_id"
			);
			while(result.next()){
				VideoGame vg = new VideoGame(
					result.getInt(1),
					result.getString(2),
					result.getString(3),
					result.getInt(4)
				);			
				videogames.add(vg);
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
			if(result.next()) 
				videogame = new VideoGame(
					id,
					result.getString(1),
					result.getString(2),
					result.getInt(3)
				);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return videogame;
	}
}
