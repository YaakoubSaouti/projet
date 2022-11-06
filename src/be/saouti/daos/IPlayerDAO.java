package be.saouti.daos;

import be.saouti.models.Player;

public interface IPlayerDAO {

	boolean create(Player player);

	boolean update(Player player);

	Player find(int id);

	Player find(String username);
}
