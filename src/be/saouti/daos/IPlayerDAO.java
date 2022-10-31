package be.saouti.daos;

import be.saouti.models.Player;

public interface IPlayerDAO {
	boolean exists(String username);
}
