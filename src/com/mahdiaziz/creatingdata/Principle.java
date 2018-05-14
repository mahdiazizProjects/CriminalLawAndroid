package com.mahdiaziz.creatingdata;

import java.util.ArrayList;

public class Principle {
	private int Id;
	private String content;
	private int Section;
	private int Chapter;
	private int mabhas;
	private ArrayList<Provision>provision;
	private ArrayList<Gozine>gozineha;
	public ArrayList<Gozine> getGozineha() {
		return gozineha;
	}
	public void setGozineha(ArrayList<Gozine> gozineha) {
		this.gozineha = gozineha;
	}
	public Principle()
	{
		provision=new ArrayList<Provision>();
	}
	public ArrayList<Provision> getProvision() {
		return provision;
	}
	public void setProvision(ArrayList<Provision> provision) {
		this.provision = provision;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSection() {
		return Section;
	}
	public void setSection(int section) {
		Section = section;
	}
	public int getChapter() {
		return Chapter;
	}
	public void setChapter(int chapter) {
		Chapter = chapter;
	}
	public int getMabhas() {
		return mabhas;
	}
	public void setMabhas(int mabhas) {
		this.mabhas = mabhas;
	}
	

}
