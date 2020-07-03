package com.app.findhome.model.remote.property;

import com.google.gson.annotations.SerializedName;

public class ListItem{

	@SerializedName("image")
	private String image;

	@SerializedName("agent")
	private Agent agent;

	@SerializedName("is_premium")
	private String isPremium;

	@SerializedName("description")
	private String description;

	@SerializedName("location")
	private Location location;

	@SerializedName("id")
	private String id;

	@SerializedName("attribute")
	private Attribute attribute;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("fasilities")
	private String fasilities;

	public String getImage(){
		return image;
	}

	public Agent getAgent(){
		return agent;
	}

	public String getIsPremium(){
		return isPremium;
	}

	public String getDescription(){
		return description;
	}

	public Location getLocation(){
		return location;
	}

	public String getId(){
		return id;
	}

	public Attribute getAttribute(){
		return attribute;
	}

	public String getTitle(){
		return title;
	}

	public String getType(){
		return type;
	}

	public String getFasilities(){
		return fasilities;
	}
}