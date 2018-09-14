package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Seats {
	private Seat[][] _layout;
	private int _theatreSeatsNumber;
	/**
	 * This creates a seats object, which contains an array of Seat objects,
	 * which will be used to check all the available seats, and manage them
	 * @param theatreSeats - The number of rows in the theatre
	 */
	public Seats(String theatreSeats, String premiumPriceStr, String cheapSeatStr) {
		_theatreSeatsNumber = Integer.parseInt(theatreSeats);
		_layout = new Seat[_theatreSeatsNumber][_theatreSeatsNumber];
		for(int row = 0; row < _theatreSeatsNumber; row++) {
			for(int col = 0; col < _theatreSeatsNumber;col++) {
				/*
				 *  fill the seat array with new seats, and make them premium 
				 *  if they are in the lower half
				 */
				if(row <= _theatreSeatsNumber / 2) {
					Seat Seat = new Seat(premiumPriceStr);
					_layout[row][col] = Seat;
				} else {
					Seat Seat = new Seat(cheapSeatStr);
					_layout[row][col] = Seat;
				}
			}
				
		}
	}
	
	public List<String> availableSeats(String performanceID, Seats Seats) {
		List<String> availableSeats = new ArrayList<String>();
		//returns a list of all the available seats
		for(int i = 0; i < _theatreSeatsNumber;i++) {
			for(int j = 0; j < _theatreSeatsNumber;j++) {
				if(_layout[i][j].isAvailable()) {
					availableSeats.add((Integer.toString(i + 1) + "\t" + (Integer.toString(j + 1))));
				}
			}
		}
		return availableSeats;
	}
	
	public int getTheatreSeatsNumber(){
		return _theatreSeatsNumber;
	}
	
	public void makeSeatUnavailable(int row, int seatNumber) {
		_layout[row][seatNumber].bookSeat();
	}
	
	public String seatPrice(int row, int seatNumber) {
		return _layout[row][seatNumber].getseatPrice();
	}
}
