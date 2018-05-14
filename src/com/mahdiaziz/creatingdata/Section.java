package com.mahdiaziz.creatingdata;

import java.util.ArrayList;

import com.mahdiaziz.punishmentlaw.R;

import android.content.Context;

public class Section {
	private int Id;
	private String Title;
	private ArrayList<Chapter>chapters;
	public Section()
	{
		chapters=new ArrayList<Chapter>();
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public ArrayList<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(ArrayList<Chapter> chapters) {
		this.chapters = chapters;
	}
	public ArrayList<String>getChapterTitles(ArrayList<Chapter>chapters,Context mcontext)
	{
		ArrayList<String>Titles=new ArrayList<String>();
		for(int ii=0;ii<chapters.size();ii++)
		{
			Titles.add(mcontext.getResources().getString(R.string.chapter)+Integer.toString(chapters.get(ii).getId())+" " +chapters.get(ii).getTitle());
		}
		return Titles;
	}

}
