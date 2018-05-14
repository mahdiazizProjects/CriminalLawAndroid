package com.mahdiaziz.creatingdata;

import java.util.ArrayList;
import java.util.List;

public class Search_class {

	public static ArrayList<Principle> SearchSections(String word,ArrayList<Principle>fulladress,List<Section>sections,int type)
	{
		for (int i = 0; i < sections.size(); i++) {
			if(sections.get(i).getTitle().contains(word)&&type==1||content_equal(Stringfixer(sections.get(i).getTitle()),word)&&type==1)
			{
				Principle p=new Principle();
				p.setSection(sections.get(i).getId());
				fulladress.add(p);
			}
		}
		return fulladress;
	}
	public static ArrayList<Principle> Searchchapters(String word,ArrayList<Principle>fulladdress,List<Section>sections,int type)
	{
		for (int i = 0; i < sections.size(); i++) {
			ArrayList<Chapter>chapters=sections.get(i).getChapters();
			if(chapters.size()>1)
				for (int j = 0; j < chapters.size(); j++) {
					if(chapters.get(j).getTitle().contains(word)&&type==1||content_equal(Stringfixer(chapters.get(j).getTitle()),word)&&type==0)
					{
						Principle p=new Principle();
						p.setSection(sections.get(i).getId());
						p.setChapter(chapters.get(j).getId());
						fulladdress.add(p);
					}
				}
		}
		return fulladdress;
	}
	public static ArrayList<Principle> Searchmabhas(String word,ArrayList<Principle>fulladdress,List<Section>sections,int type)
	{
		for (int i = 0; i < sections.size(); i++) {
			ArrayList<Chapter>chapters=sections.get(i).getChapters();
			for (int j = 0; j < chapters.size(); j++) {
				ArrayList<Mabhas>mabahes=chapters.get(j).getMabahes();
				if(mabahes.size()>1)
					for (int k = 0; k < mabahes.size(); k++) {
						if(mabahes.get(k).getTitle().contains(word)&&type==1||content_equal(Stringfixer(mabahes.get(k).getTitle()),word)&&type==1)
						{
							Principle p=new Principle();
							p.setSection(sections.get(i).getId());
							p.setChapter(chapters.get(j).getId());
							p.setMabhas(mabahes.get(k).getId());
							fulladdress.add(p);
						}
					}
			}
		}
		return fulladdress;
	}
	public static ArrayList<Principle> Searchprinciples_fromoutside(String word,List<Principle>principles)
	{
		ArrayList<Principle>fulladdress=new ArrayList<Principle>();
		for (int j2 = 0; j2 < principles.size(); j2++) {
			if(principles.get(j2).getContent().contains(word))
			{
				Principle p=new Principle();
				p.setId(principles.get(j2).getId());
				p.setContent(principles.get(j2).getContent());
				p.setGozineha(principles.get(j2).getGozineha());
				p.setProvision(principles.get(j2).getProvision());
				fulladdress.add(p);
			}
			else
			{
				ArrayList<Gozine>goz=principles.get(j2).getGozineha();
				boolean flag=false;
				for (int k2 = 0; k2 < goz.size(); k2++) {
					if(goz.get(k2).getContent().contains(word))
					{
						Principle p=new Principle();
						p.setId(principles.get(j2).getId());
						p.setContent(principles.get(j2).getContent());
						p.setGozineha(principles.get(j2).getGozineha());
						fulladdress.add(p);
						flag=true;
					}
				}
				if(!flag)
				{
					ArrayList<Provision>prov=principles.get(j2).getProvision();
					for (int k2 = 0; k2 < prov.size(); k2++) {
						if(prov.get(k2).getContent().contains(word))
						{
							Principle p=new Principle();
							p.setId(principles.get(j2).getId());
							p.setContent(principles.get(j2).getContent());
							p.setGozineha(principles.get(j2).getGozineha());
							p.setProvision(principles.get(j2).getProvision());
							fulladdress.add(p);
							break;
						}
					}
				}
			}

		}
		return fulladdress;
	}
	public static ArrayList<Principle> Searchprinciples(String word,ArrayList<Principle>fulladdress,List<Section>sections,int type)
	{
		for (int i = 0; i < sections.size(); i++) {
			ArrayList<Chapter>chapters=sections.get(i).getChapters();
			for (int j = 0; j < chapters.size(); j++) {
				ArrayList<Mabhas>mabahes=chapters.get(j).getMabahes();
				for (int k = 0; k < mabahes.size(); k++) {

					ArrayList<Principle>principles=mabahes.get(k).getPrinciples();
					for (int j2 = 0; j2 < principles.size(); j2++) {
						if(principles.get(j2).getContent().contains(word)&&type==1||content_equal(Stringfixer(principles.get(j2).getContent()),word)&&type==0)
						{
							Principle p=new Principle();
							p.setSection(sections.get(i).getId());
							p.setChapter(chapters.get(j).getId());
							p.setMabhas(mabahes.get(k).getId());
							p.setId(principles.get(j2).getId());
							p.setContent(principles.get(j2).getContent());
							p.setGozineha(principles.get(j2).getGozineha());
							p.setProvision(principles.get(j2).getProvision());
							fulladdress.add(p);
						}
						else
						{
							ArrayList<Gozine>goz=principles.get(j2).getGozineha();
							boolean flag=false;
							for (int k2 = 0; k2 < goz.size(); k2++) {
								if(goz.get(k2).getContent().contains(word)&&type==1||content_equal(Stringfixer(goz.get(k2).getContent()),word)&&type==0)
								{
									Principle p=new Principle();
									p.setSection(sections.get(i).getId());
									p.setChapter(chapters.get(j).getId());
									p.setId(principles.get(j2).getId());
									p.setContent(principles.get(j2).getContent());
									//									ArrayList<Gozine>gozareha=new ArrayList<Gozine>();
									p.setGozineha(principles.get(j2).getGozineha());
									//									for(int g=0;g<goz.size();g++)
									//									{
									//										Gozine go=new Gozine();
									//										String temp=goz.get(g).getContent();
									//										go.setContent(temp.replaceAll(word,"<font color='red'>"+word+"</font>"));
									//										gozareha.add(go);
									//									}
									//									p.setGozineha(gozareha);
									//									p.setProvision(principles.get(j2).getProvision());
									fulladdress.add(p);
									flag=true;
									//									break;
								}
							}
							if(!flag)
							{
								ArrayList<Provision>prov=principles.get(j2).getProvision();
								for (int k2 = 0; k2 < prov.size(); k2++) {
									if(prov.get(k2).getContent().contains(word)&&type==1||content_equal(Stringfixer(prov.get(k2).getContent()),word)&&type==0)
									{
										Principle p=new Principle();
										p.setSection(sections.get(i).getId());
										p.setChapter(chapters.get(j).getId());
										p.setId(principles.get(j2).getId());
										p.setContent(principles.get(j2).getContent());
										p.setGozineha(principles.get(j2).getGozineha());
										p.setProvision(principles.get(j2).getProvision());
										//										ArrayList<Provision>provisions=new ArrayList<Provision>();
										//										for(int pp=0;pp<provisions.size();pp++)
										//										{
										//											Provision pr=new Provision();
										//											String temp=principles.get(j2).getProvision().get(pp).getContent();
										//											pr.setContent(temp.replaceAll(word,"<font color='red'>"+word+"</font>"));
										//											provisions.add(pr);
										//										}
										//										p.setProvision(provisions)
										fulladdress.add(p);
										break;
									}
								}
							}
						}

					}
				}
			}
		}
		return fulladdress;
	}

	public static ArrayList<Principle> Searchprinciplestitle(String word,ArrayList<Principle>fulladdress,List<Section>sections,int type)
	{
		for (int i = 0; i < sections.size(); i++) {
			ArrayList<Chapter>chapters=sections.get(i).getChapters();
			for (int j = 0; j < chapters.size(); j++) {
				ArrayList<Mabhas>mabahes=chapters.get(j).getMabahes();
				for (int k = 0; k < mabahes.size(); k++) {

					ArrayList<Principle>principles=mabahes.get(k).getPrinciples();
					for (int j2 = 0; j2 < principles.size(); j2++) {
						if(String.valueOf(principles.get(j2).getId()).contains(word)&&type==1||String.valueOf(principles.get(j2).getId()).contentEquals(word)&&type==0)
						{
							Principle p=new Principle();
							p.setSection(sections.get(i).getId());
							p.setChapter(chapters.get(j).getId());
							p.setMabhas(mabahes.get(k).getId());
							p.setId(principles.get(j2).getId());
							p.setContent(principles.get(j2).getContent());
							p.setGozineha(principles.get(j2).getGozineha());
							p.setProvision(principles.get(j2).getProvision());
							fulladdress.add(p);
						}

					}
				}
			}
		}
		return fulladdress;
	}
	private static boolean content_equal(String Content,String word)
	{
		String[]contents=Content.split(" ");
		for(int ii=0;ii<contents.length;ii++)
		{
			if(contents[ii].equalsIgnoreCase(word))
				return true;
		}

		return false;

	}
	public static ArrayList<String> SearchWordwithSuggestions(String word,List<Section>sections,ArrayList<String>Suggestions)
	{
		for (int i = 0; i < sections.size(); i++) {
			ArrayList<Chapter>chapters=sections.get(i).getChapters();
			for (int j = 0; j < chapters.size(); j++) {
				ArrayList<Mabhas>mabahes=chapters.get(j).getMabahes();
				for (int k = 0; k < mabahes.size(); k++) {

					ArrayList<Principle>principles=mabahes.get(k).getPrinciples();
					for (int j2 = 0; j2 < principles.size(); j2++) {
						String[]contents=principles.get(j2).getContent().split(" |\\.|:|[0-9]|,|¡|)|(");
						for(int cc=0;cc<contents.length;cc++)
						{
							if(contents[cc].startsWith(word)&&!Suggestions.contains(word))
								Suggestions.add(contents[cc]);
								
						}
					}
				}
			}
		}
		return Suggestions;
	}
	public static ArrayList<String> FillingwithSuggestions(List<Section>sections,ArrayList<String>Suggestions)
	{
		for (int i = 0; i < sections.size(); i++) {
			ArrayList<Chapter>chapters=sections.get(i).getChapters();
			for (int j = 0; j < chapters.size(); j++) {
				ArrayList<Mabhas>mabahes=chapters.get(j).getMabahes();
				for (int k = 0; k < mabahes.size(); k++) {

					ArrayList<Principle>principles=mabahes.get(k).getPrinciples();
					for (int j2 = 0; j2 < principles.size(); j2++) {
						String[]contents=Stringfixer(principles.get(j2).getContent()).split(" ");
						for(int cc=0;cc<contents.length;cc++)
						{
							if(!Suggestions.contains(contents[cc]))
								Suggestions.add(contents[cc]);
								
						}
					}
				}
			}
		}
		return Suggestions;
	}
	public static String Stringfixer(String Content)
	{
//		Content=Content.replaceAll("[:]|[,]|[¡]|[(]|[)]|[«]|[»]|[.]", " ");
		Content=Content.replaceAll("[:]|[,]|[¡]|[(]|[)]|[«]|[»]", " ");
//		Content=Content.replaceAll("¡", " ");
//		Content=Content.replaceAll("(", " ");
//		Content=Content.replaceAll(")", " ");
//		Content=Content.replaceAll(",", " ");
//		Content=Content.replaceAll("   ", " ");
//		Content=Content.replaceAll("  ", " ");
		return Content;
		
	}
}
