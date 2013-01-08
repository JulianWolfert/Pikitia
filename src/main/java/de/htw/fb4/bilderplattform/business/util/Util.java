package de.htw.fb4.bilderplattform.business.util;

import java.text.DecimalFormat;

public class Util {

	public static final String formatDouble(double d){
		DecimalFormat df = new DecimalFormat(",##0.00");
		return df.format(d);
	}
	
}
