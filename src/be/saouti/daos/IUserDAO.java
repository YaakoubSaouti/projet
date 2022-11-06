package be.saouti.daos;

import java.sql.Connection;

import be.saouti.models.User;

public interface IUserDAO {
	
	User find(String username);
	
}
