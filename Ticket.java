package tbs.server;

public class Ticket {
	private String _ticketID;
	private int _ticketPrice;
	/**
	 * This creates a ticket object, which is used to manage the 
	 * tickets that are sold for a certain Performance
	 * @param ticketID - the ID used to identify the ticket
	 * @param ticketPriceNumber - the price of the ticket (premium or cheap)
	 */
	public Ticket(String ticketID, int ticketPriceNumber) {
		_ticketID = ticketID;
		_ticketPrice = ticketPriceNumber;
	}
	
	public String getTicketID() {
		return _ticketID;
	}
	
	public int getTicketPrice() {
		return _ticketPrice;
	}
}
