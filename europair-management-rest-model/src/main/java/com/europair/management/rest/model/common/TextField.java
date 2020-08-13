package com.europair.management.rest.model.common;

/**
 * Support class for the unified definition of text string sizes.
 * In this class are defined:
 * - Types of strings with usual fixed format.
 * - Subset of common string sizes.
 *    
 * @author david.castro
 *
 */
public class TextField {

	// Not instance 
	private TextField(){}
	
	// Specific sizes
	
	// Common NIF: 2 characteres por country id + country identification id
	// https://ec.europa.eu/taxation_customs/tin/pdf/es/TIN_-_subject_sheet_-_1_structure_summary_es.pdf
	public static final int NIF = 18; 
	
	public static final int CIF = 16;
		
	// Name
	public static final int NAME = 40;
	
	// Surname
	public static final int SURNAME = 40;
	
	// Username
	public static final int USERNAME = 40;
	
	// Address type
	public static final int ADDRESS_TYPE = 1;
	
	// ZipCode
	public static final int ZIPCODE = 10;
	
	// Region/Province code 
	public static final int REGION = 6;
	
	// ISO Country ID: ES, FR, ... 
	public static final int COUNTRY_ID = 3;

	// ISO 3166-1 Alpha-2 code
	public static final int ISO_LANG_CODE = 2;

	// IATA CODE
	public static final int IATA_CODE = 3;

	// ICAO CODE
	public static final int ICAO_CODE = 4;

	//-------------------------------------------------------------------------------------------------------------
	// Generic sizes
	// Do not modify
	public static final int TEXT_5 = 5;
	public static final int TEXT_10 = 10;
	public static final int TEXT_20 = 20;
	public static final int TEXT_30 = 30;
	public static final int TEXT_40 = 40;
	public static final int TEXT_60 = 60;
	public static final int TEXT_120 = 120;
	public static final int TEXT_255 = 255;
}
