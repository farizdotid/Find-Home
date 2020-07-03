package com.app.findhome.model.remote.property;

import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("address")
	private String address;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("longitude")
	private String longitude;

	public String getAddress(){
		return address;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getLongitude(){
		return longitude;
	}
}