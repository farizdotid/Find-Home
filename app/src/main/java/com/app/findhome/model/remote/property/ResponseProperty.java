package com.app.findhome.model.remote.property;

import com.google.gson.annotations.SerializedName;

public class ResponseProperty{

	@SerializedName("data")
	private Data data;

	@SerializedName("meta")
	private Meta meta;

	public Data getData(){
		return data;
	}

	public Meta getMeta(){
		return meta;
	}
}