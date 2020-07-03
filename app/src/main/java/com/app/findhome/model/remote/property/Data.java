package com.app.findhome.model.remote.property;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("list")
	private List<ListItem> list;

	public Pagination getPagination(){
		return pagination;
	}

	public List<ListItem> getList(){
		return list;
	}
}