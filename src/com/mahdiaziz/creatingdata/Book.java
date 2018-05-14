package com.mahdiaziz.creatingdata;

import java.util.ArrayList;
import java.util.List;

import com.mahdiaziz.punishmentlaw.R;

import android.content.Context;

public class Book {
	private int Id;
	private List<Section>sections;
	public Book()
	{
		sections=new ArrayList<Section>();
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	public ArrayList<String>getSectionTitles(Context mcontext)
	{
		ArrayList<String>Titles=new ArrayList<String>();
		for(int ii=0;ii<sections.size();ii++)
		{
			Titles.add(mcontext.getResources().getString(R.string.section)+Integer.toString(sections.get(ii).getId())+" " +sections.get(ii).getTitle());
		}
		return Titles;
	}

}
