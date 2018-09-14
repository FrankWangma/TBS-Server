package tbs.server;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperMethods {
	private static int _counterID = 1;
	public static Boolean isInputBlank(String input) {
		/*
		 * This method checks whether a single input is blank and 
		 * returns a boolean true or false 
		 */
		if(input.equals("")) {
			return true;
		} else if (input.equals(null)) {
			return true;
		} else {
			return false;
		}
	}
	public static Boolean areInputsBlank(String[] input)	{
		/*
		 * This method checks whether multiple inputs are blank
		 * and returns a boolean true or false
		 */
		for (int i = 0; i < input.length; i++) {
			if(input[i].equals("")) {
				return true;
			} else if (input[i].equals(null)) {
				return true;
			}
		}
		return false;
	}
	

	public static Boolean doesExist(String name, List<String> compareList) {
		/*
		 *  This method finds whether a name or ID exists inside a given list
		 * returns a Boolean true or false
		 */
		for(int i = 0; i < compareList.size();i++) {
			if(name.equals(compareList.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static Boolean isTimeValid(String timeToValidate, String timeFormat) {
		/*
		 * This method checks whether the inputed time is in the correct format
		 * If it isn't, it will give a parse exception, and return a boolean false.
		 * If it is the right input, it will return a boolean true
		 */
		if(timeToValidate == null) {
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		sdf.setLenient(false);
		
		try {
			// a parse exception will be thrown if invalid
			Date time = sdf.parse(timeToValidate);
		} catch(ParseException e) {
			return false;
		}
		
		return true;
	}
	
	public static String generateID(int type) {
		/*
		 * This method generates an ID, using a field counterID in
		 * the helper method class
		 * returns the newly generated ID as a string
		 */
		String code = Integer.toString(_counterID);
		String ID;
		/**
		 *  The type that's inputed determines what the ID generated
		 *  will be used for.
		 *  0 - generate ID for artists
		 *  1 - generate ID for acts
		 *  2 - generate ID for performances
		 *  3 - generate ID for tickets
		 */
		if(type == 0) {
			ID = ("art" + code);
		} else if (type == 1) {
			ID = ("act" + code);
		} else if (type == 2) {
			ID = ("per" + code);
		} else {
			ID = ("tic" + code);
		}
		_counterID++;
		return ID;
	}
	
}
