package be.saouti.daos;

import be.saouti.models.Administrator;

public interface IAdministratorDAO {
	Administrator find(String username);
}
