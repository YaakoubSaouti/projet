package be.saouti.daos;
import be.saouti.models.User;

public interface IUserDAO {
	
	User find(String username);
	
}
