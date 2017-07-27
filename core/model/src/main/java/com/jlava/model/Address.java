package com.jlava.model;

public class Address {
	private int addressId;
	private String street;
	private String barangay;
	private String municipality;
	private int zipCode;

	public Address() {}
	public Address(String street, String barangay, String municipality, int zipCode) {
		this.street = street;
		this.barangay = barangay;
		this.municipality = municipality;
		this.zipCode = zipCode;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBarangay() {
		return barangay;
	}

	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String toString(){ 
		String streetStr = (street != null)?street + ", ":"";
		String bgyStr = (barangay != null)?" brgy. " + barangay  + ", ": "";
		String muniStr = (municipality != null)?municipality : "";
		String zipStr = (zipCode <= 0)?", " + zipCode : "";

		return streetStr + bgyStr + muniStr + zipStr;
	}
}