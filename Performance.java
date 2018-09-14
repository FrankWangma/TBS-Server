package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Performance {
	private String _performanceID;
	private String _theatreID;
	private String _startTime;
	private String _premiumSeatPrice;
	private String _cheapSeatPrice;
	private Seats _seats;
	private List<Ticket> _sales = new ArrayList<Ticket>();
	/**
	 * This creates a performance object, which will be used to manage
	 * all the information that a performance contains
	 * @param performanceID - the ID used to identify a performance
	 * @param theatreID - the ID of the theatre that will hold the performance
	 * @param startTime - start time of the performance
	 * @param premiumSeatPrice - pricing of the premium seats
	 * @param cheapSeatPrice - pricing of the cheap seats
	 * @param Seats - A Seats object, which contains all the seats in the performance
	 * @param sales - a list of all the tickets sold for the performance
	 */
	public Performance(String performanceID, String theatreID, String startTime, String premiumSeatPrice,
			String cheapSeatPrice, Seats Seats, List<Ticket> sales ) {
			_performanceID = performanceID;
			_theatreID = theatreID;
			_startTime = startTime;
			_premiumSeatPrice = premiumSeatPrice;
			_cheapSeatPrice = cheapSeatPrice;
			_seats = Seats;
			_sales = sales;
	}
	
	public String getPerformanceID() {
		return _performanceID;
	}
	
	public String getTheatreID() {
		return _theatreID;
	}
	

	public Seats getSeats() {
		return _seats;
	}
	
	public List<Ticket> getSales(){
		return _sales;
	}
	
	public String getPremiumSeatPrice() {
		return _premiumSeatPrice;
	}
	
	public String getCheapSeatPrice() {
		return _cheapSeatPrice;
	}
	
	public String getStartTime() {
		return _startTime;
	}
	
	public String totalSalePrice() {
		// This method gives the total amount of money that was made from selling the tickets
		int salePrice = 0;
		for(int i = 0; i < _sales.size(); i++) {
			salePrice += _sales.get(i).getTicketPrice();
		}
		String totalSalePrice = ("$" + Integer.toString(salePrice));
		return totalSalePrice;
	}
}
