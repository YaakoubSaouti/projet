package be.saouti.daos;

import be.saouti.models.Copy;

public interface ICopyDAO {
	public boolean delete(Copy copy);

	Copy find(int id);
}
