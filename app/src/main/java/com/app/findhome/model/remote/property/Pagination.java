package com.app.findhome.model.remote.property;

import com.google.gson.annotations.SerializedName;

public class Pagination{

	@SerializedName("total_rows")
	private int totalRows;

	@SerializedName("limit")
	private int limit;

	@SerializedName("total_page")
	private int totalPage;

	@SerializedName("current_page")
	private int currentPage;

	public int getTotalRows(){
		return totalRows;
	}

	public int getLimit(){
		return limit;
	}

	public int getTotalPage(){
		return totalPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}
}