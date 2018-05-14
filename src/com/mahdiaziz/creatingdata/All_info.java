package com.mahdiaziz.creatingdata;

import java.util.List;
public class All_info{
	private static List<Book>books;
	private static List<Section>sections;
	public static void setall(List<Book>book,List<Section>section)
	{
		books=book;
		sections=section;
	}
	public static List<Book> getbooks()
	{
		return books;
	}
	public static List<Section> getsections()
	{
		return sections;
	}

}
