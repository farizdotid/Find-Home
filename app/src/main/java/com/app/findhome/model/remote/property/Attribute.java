package com.app.findhome.model.remote.property;

import com.google.gson.annotations.SerializedName;

public class Attribute{

	@SerializedName("bedrooms")
	private String bedrooms;

	@SerializedName("price")
	private String price;

	@SerializedName("building_size")
	private String buildingSize;

	@SerializedName("land_size")
	private String landSize;

	@SerializedName("floor")
	private String floor;

	@SerializedName("bathrooms")
	private String bathrooms;

	public String getBedrooms(){
		return bedrooms;
	}

	public String getPrice(){
		return price;
	}

	public String getBuildingSize(){
		return buildingSize;
	}

	public String getLandSize(){
		return landSize;
	}

	public String getFloor(){
		return floor;
	}

	public String getBathrooms(){
		return bathrooms;
	}
}