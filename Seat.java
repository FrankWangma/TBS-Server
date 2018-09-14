package tbs.server;

public class Seat {
	private Boolean _isAvailable;
	private String _seatPrice;
	/**
	 * This makes a Seat object, which will be used to make an
	 * array of Seat in the Seats object. This allows each individual
	 * Seat to contain an availability and premium status.
	 */
	public Seat(String seatPrice) {
		_isAvailable = true;;
		_seatPrice = seatPrice;
	}
	
	public void bookSeat() {
		_isAvailable = false;
	}
	
	public Boolean isAvailable() {
		return _isAvailable;
	}
	
	public String getseatPrice() {
		return _seatPrice;
	}
}
