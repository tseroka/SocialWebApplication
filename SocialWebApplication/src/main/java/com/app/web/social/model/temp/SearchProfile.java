package com.app.web.social.model.temp;

public class SearchProfile {
	
	private String searchSex;
	private String searchCity;
	private String searchInterests;
	
	public SearchProfile() {
		
	}
	
	public SearchProfile(String searchSex, String searchCity, String searchInterests) {
		this.searchSex = searchSex ;
		this.searchCity = searchCity;
		this.searchInterests = searchInterests;
	}
	 
	public String getSearchSex() {
		return searchSex;
	}
	public void setSearchSex(String searchSex) {
		this.searchSex = searchSex;
	}
	public String getSearchCity() {
		return searchCity;
	}
	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}
	public String getSearchInterests() {
		return searchInterests;
	}
	public void setSearchInterests(String searchInterests) {
		this.searchInterests = searchInterests;
	}

}
