package be.saouti.daos;

import java.util.List;

import be.saouti.models.VideoGame;

public interface IVideoGameDAO {
	boolean update(VideoGame videogame);
	List<VideoGame> getAll();
	VideoGame find(int id);
}
