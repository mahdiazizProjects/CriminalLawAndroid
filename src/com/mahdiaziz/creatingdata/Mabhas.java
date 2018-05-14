package com.mahdiaziz.creatingdata;

import java.util.ArrayList;

public class Mabhas {
	private ArrayList<Principle>principles;
	private int Id;
	private String Title;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Mabhas()
	{
		principles=new ArrayList<Principle>();
	}
	public ArrayList<Principle> getPrinciples() {
		return principles;
	}
	public void setPrinciples(ArrayList<Principle> principles) {
		this.principles = principles;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
}
