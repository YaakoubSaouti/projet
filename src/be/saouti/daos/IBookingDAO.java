package be.saouti.daos;
import be.saouti.models.Booking;

public interface IBookingDAO {
	public boolean delete(Booking booking);
	public Booking find(int i);
}
