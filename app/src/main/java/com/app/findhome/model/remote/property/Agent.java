package com.app.findhome.model.remote.property;

import com.google.gson.annotations.SerializedName;

public class Agent{

	@SerializedName("name")
	private String name;

	@SerializedName("photo")
	private String photo;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	public String getName(){
		return name;
	}

	public String getPhoto(){
		return photo;
	}

	public String getId(){
		return id;
	}

	public String getType(){
		return type;
	}
}