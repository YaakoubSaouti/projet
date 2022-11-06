package be.saouti.daos;

import be.saouti.models.Administrator;
import be.saouti.models.Player;

public interface IAdministratorDAO {
	Administrator find(String username);
}
