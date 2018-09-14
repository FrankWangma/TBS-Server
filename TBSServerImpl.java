package tbs.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class TBSServerImpl implements TBSServer {
	//Create some lists to store information
	private List<String> _theatreID = new ArrayList<String>();
	private List<String> _theatreSeats = new ArrayList<String>();
	private List<String> _theatreFloorSpace = new ArrayList<String>();
	private List<String> _artistNamesList = new ArrayList<String>();
	private List<String> _artistIDList = new ArrayList<String>();
	private Map<String,List<String>> _artistsAndActs = new HashMap<String,List<String>>();
	private Map<String,List<Performance>> _actsAndPerformances = new HashMap<String,List<Performance>>();
	private Map<String,Integer> _actsAndDurations = new HashMap<String,Integer>();
	private Map<String,Performance> _performanceIDsAndPerformances = new HashMap<String,Performance>();
	
	public String initialise(String path) {
		if (HelperMethods.isInputBlank(path)) {
			return("ERROR input is blank");
		}
		else {
			String line = null; 
			
			try {
				// allows theatres1.csv to be read
				  FileReader fr = new FileReader(path);
				  BufferedReader theatres = new BufferedReader(fr);
				  
				// read the information line by line
				  while((line = theatres.readLine()) != null) {
					  String[] theatreInfo = line.split("\t");
					  _theatreID.add(theatreInfo[1]);
					  _theatreSeats.add(theatreInfo[2]);
					  _theatreFloorSpace.add(theatreInfo[3]);
					  
				  }
				  theatres.close();
			} catch (FileNotFoundException e) {
				return("ERROR File not found");
			} catch (IOException e) {
				return("ERROR File incorrect format");
			}
			 return("");
		}
	}

	
	public List<String> getTheatreIDs() {
		List<String> sortedTheatreID = new ArrayList<String>(_theatreID);
		Collections.sort(sortedTheatreID);
		return sortedTheatreID;
	}

	
	public List<String> getArtistIDs() {
		List<String> sortedArtistIDList = new ArrayList<String>();
		sortedArtistIDList.addAll(_artistsAndActs.keySet());
		Collections.sort(sortedArtistIDList);
		return sortedArtistIDList;
	}

	
	public List<String> getArtistNames() {
		List<String> sortedArtistNameList = _artistNamesList;
		Collections.sort(sortedArtistNameList);
		return sortedArtistNameList;
	}

	
	public List<String> getActIDsForArtist(String artistID) {
		//use helper methods to check for any user errors in the inputs
		List<String> returnList = new ArrayList<String>();
		if(HelperMethods.isInputBlank(artistID)) {
			returnList.add("ERROR artistID is blank");
		} else if(! HelperMethods.doesExist(artistID, _artistIDList)) {
			returnList.add("ERROR no artist with specified ID");
		} else {
			returnList = _artistsAndActs.get(artistID);
			Collections.sort(returnList);
		}
		return returnList;
	}

	
	public List<String> getPeformanceIDsForAct(String actID) {
		List<Performance> performanceObjectList = _actsAndPerformances.get(actID);
		List<String> sortedPerformanceIDList = new ArrayList<String>();
		//use helper methods to check for any user errors in the inputs
		List<String> actList = new ArrayList<String>();
		actList.addAll(_actsAndPerformances.keySet()); //listing the keys in the map
		if(HelperMethods.isInputBlank(actID)) {
			sortedPerformanceIDList.add("ERROR actID is blank");
		} else if(! HelperMethods.doesExist(actID, actList)) {
			sortedPerformanceIDList.add("ERROR no act with specified ID");
		} else {
			// get all the performance ID's that belong to existing acts
			for(int i = 0; i < performanceObjectList.size();i++ ) {
				Performance currentObject = performanceObjectList.get(i);
				sortedPerformanceIDList.add(currentObject.getPerformanceID());
			}
			Collections.sort(sortedPerformanceIDList);
		}
		return sortedPerformanceIDList;
	}

	
	public List<String> getTicketIDsForPerformance(String performanceID) {
		List<String> sortedTicketIDList = new ArrayList<String>();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<String> actIDList = new ArrayList<String>();
		actIDList.addAll(_actsAndPerformances.keySet());
		//use helper methods to check for any user errors in the inputs
		if (HelperMethods.isInputBlank(performanceID)) {
			sortedTicketIDList.add("ERROR performanceID is blank");
			return sortedTicketIDList;
		} else {
				Performance performance = _performanceIDsAndPerformances.get(performanceID);
				if(performance == null) {
					//if no performance is found, this line will be reached
					sortedTicketIDList.add("ERROR no performance with specified ID");
				} else {
					//get the ticket IDs for the performance if its found and sort
					ticketList = performance.getSales();
					for(int j = 0; j < ticketList.size();j++) {
					sortedTicketIDList.add(ticketList.get(j).getTicketID());
					} 
					Collections.sort(sortedTicketIDList);
					}
				}
		return sortedTicketIDList;
	}

	
	public String addArtist(String name) {
		//use helper methods to check for any user errors in the inputs
		if (HelperMethods.isInputBlank(name)) {
			return("ERROR input is blank");
		} else {
			name = name.toLowerCase();
			Boolean exists = HelperMethods.doesExist(name, _artistNamesList);
			if (exists) {
				return("ERROR artist already exists");
			} else {
				//produce a unique ID and add the artist to a list
				List<String> blankList = new ArrayList<String>();
				String artistID = HelperMethods.generateID(0);
				_artistNamesList.add(name);
				_artistsAndActs.put(artistID, blankList);
				_artistIDList.addAll(_artistsAndActs.keySet());
				return artistID;
			}
		}
	}

	
	public String addAct(String title, String artistID, int minutesDuration) {
		String[] inputs = {title, artistID};
		//use helper methods to check for any user errors in the inputs
		Boolean areInputsBlank = HelperMethods.areInputsBlank(inputs);
		Boolean doesArtistExist = _artistsAndActs.containsKey(artistID);
		if(areInputsBlank) {
			return("ERROR one or more inputs are blank");
		} else if (!doesArtistExist) {
			return("ERROR artist does not exist");
		} else if( minutesDuration <= 0) {
			return("ERROR Duration is not possible");
		} else {
			//produce a unique ID and add the act to a list
			String actID = HelperMethods.generateID(1);
			_actsAndDurations.put(actID, minutesDuration);
			_artistsAndActs.get(artistID).add(actID);
			//create a blank list of performances to put with an act
			List<Performance> blankList = new ArrayList<Performance>();
			_actsAndPerformances.put(actID, blankList);
			return actID;
		}
	}

	
	public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr,
			String cheapSeatsStr) {
			String[] inputs = {actID, theatreID, startTimeStr, premiumPriceStr,cheapSeatsStr};
			List<String> actsList = new ArrayList<String>();
			actsList.addAll(_actsAndPerformances.keySet());
			//use helper methods to check for any user errors in the inputs
			if(HelperMethods.areInputsBlank(inputs)) {
				return("ERROR one or more inputs are blank");
			} else if(!HelperMethods.doesExist(actID, actsList) || !HelperMethods.doesExist(theatreID, _theatreID)) {
				return("ERROR the act and/or theatre specified does not exist"); }
			 else if(!HelperMethods.isTimeValid(startTimeStr, "yyyy-MM-dd'T'HH:mm" )) {
				return("ERROR the time is null, or invalid format");
			} else if(!premiumPriceStr.matches("\\$\\d+") || !cheapSeatsStr.matches("\\$\\d+")) {
				return("ERROR the prices for seats are in incorrect format");
			} else {
				/*
				 * Generate a unique ID and create a performance object using
				 * the information provided, as well as creating a new Seats object
				 * to manage the available seats for each performance
				 */
				String performanceID = HelperMethods.generateID(2);
				int theatreIndex = _theatreID.indexOf(theatreID);
				Seats Seats = new Seats(_theatreSeats.get(theatreIndex), premiumPriceStr, cheapSeatsStr);
				List<Ticket> sales= new Vector<Ticket>();
				Performance performance = new Performance(performanceID,theatreID,startTimeStr,premiumPriceStr,cheapSeatsStr, Seats, sales);
				_performanceIDsAndPerformances.put(performanceID,performance);
				_actsAndPerformances.get(actID).add(performance);
				return performanceID;
			}
			
	}

	
	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		List<String> availableSeats = seatsAvailable(performanceID);
		List<String> actIDList = new ArrayList<String>();
		String ticketPrice;
		actIDList.addAll(_actsAndPerformances.keySet());
		//use helper methods to check for any user errors in the inputs
		if(HelperMethods.isInputBlank(performanceID)) {
			return("ERROR performanceID is blank");
		} else if (rowNumber <= 0 || seatNumber <= 0) {
			return("ERROR seat does not exist");
		} else {
				Performance performance = _performanceIDsAndPerformances.get(performanceID);
				if(performance == null) {
					return ("ERROR Performance does not exist");
				}
					//Find whether the seat specified is available or not
					String wantedSeat = (Integer.toString(rowNumber) + "\t" + Integer.toString(seatNumber));
							for(int k = 0; k <availableSeats.size();k++) {
								if(wantedSeat.equals(availableSeats.get(k))) {
									//if seat is found, generate a unique ID for the ticket
									String ticketID = HelperMethods.generateID(3);
									//Check if the specified seat is a premium or cheap seat
									ticketPrice = performance.getSeats().seatPrice(rowNumber, seatNumber);
									//book the seat, and make it unavailable
									performance.getSeats().makeSeatUnavailable(rowNumber - 1, seatNumber - 1);
									int ticketPriceNumber = Integer.parseInt(ticketPrice.substring(1));
									Ticket ticket = new Ticket(ticketID, ticketPriceNumber);
									performance.getSales().add(ticket);
									return ticketID;
								}
							}
							return("ERROR Specified Seat is not available");
					}
	}

	
	public List<String> seatsAvailable(String performanceID) {
		List<String> returnList = new ArrayList<String>();
		List<String> actIDList = new ArrayList<String>();
		actIDList.addAll(_actsAndPerformances.keySet());
		//use helper methods to check for any user errors in the inputs
		if(HelperMethods.isInputBlank(performanceID)) {
			returnList.add("ERROR performance ID is blank");
			return returnList;
		} else {	
			Performance performance = _performanceIDsAndPerformances.get(performanceID);
			if (performance == null) {
				returnList.add("ERROR no performance with specified ID");
			} else {	
				//If the performance is found, get the seats available
				Seats Seats = performance.getSeats();
				returnList = Seats.availableSeats(performanceID, Seats);
				}
			}
		return returnList;
		}

	
	public List<String> salesReport(String actID) {
		List<String> returnList = new ArrayList<String>();
		List<String> actIDList = new ArrayList<String>();
		List<Performance> performanceList = new ArrayList<Performance>();
		actIDList.addAll(_actsAndPerformances.keySet());
		//use helper methods to check for any user errors in the inputs
		if(HelperMethods.isInputBlank(actID)) {
			returnList.add("ERROR actID is blank");
		} else if(!HelperMethods.doesExist(actID, actIDList)) {
			returnList.add("ERROR no act with specified ID");
		} else {
			performanceList = _actsAndPerformances.get(actID);
			for(int i = 0; i < performanceList.size();i++) {
				/*
				 * format and add the sales reports for each performance
				 * All of the required information is contained in the performance Objects
				 */
				String performanceID = performanceList.get(i).getPerformanceID();
				String startTime = performanceList.get(i).getStartTime();
				String numberOfTickets = Integer.toString(performanceList.get(i).getSales().size());
				String totalSalePrice = performanceList.get(i).totalSalePrice();
				returnList.add(performanceID + "\t" + startTime + "\t" + numberOfTickets + "\t" + totalSalePrice);
			}
		}
		return returnList;
	}

	public List<String> dump() {
		List<String> returnList = new ArrayList<String>();
		//Get all of the start times for the performances
		Collection<List<Performance>> performances = _actsAndPerformances.values();
		for(int i = 0; i < performances.size(); i++) {
			List<Performance> currentList = performances.iterator().next();
			for(int j = 0; j < currentList.size();j++) {
				returnList.add(currentList.get(j).getStartTime());
			}
		}
		//Get all of the durations for acts
		Collection<Integer> actDurations = _actsAndDurations.values();
		for(int i = 0; i < actDurations.size();i++) {
			returnList.add(actDurations.iterator().next().toString());
		}
		
		//get the floor space for theatres
		returnList.addAll(_theatreFloorSpace);
		return returnList;
	}
}
