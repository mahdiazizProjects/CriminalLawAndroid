package com.mahdiaziz.creatingdata;

import java.util.ArrayList;

public class Chapter {

	private ArrayList<Mabhas>Mabahes;
	private int Id;
	private String Title;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Chapter()
	{
		Mabahes=new ArrayList<Mabhas>();
	}
	public ArrayList<Mabhas> getMabahes() {
		return Mabahes;
	}
	public void setMabahes(ArrayList<Mabhas> principles) {
		this.Mabahes = principles;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
}
