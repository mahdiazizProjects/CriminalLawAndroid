package com.mahdiaziz.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import android.content.Context;
import android.util.Log;

import com.mahdiaziz.creatingdata.Book;
import com.mahdiaziz.creatingdata.Chapter;
import com.mahdiaziz.creatingdata.Gozine;
import com.mahdiaziz.creatingdata.Mabhas;
import com.mahdiaziz.creatingdata.Principle;
import com.mahdiaziz.creatingdata.Provision;
import com.mahdiaziz.creatingdata.Section;
import com.mahdiaziz.punishmentlaw.R;
public class RulesJDOMParser {

	private static final String LOG_TAG = "Rules";
	private static final String Book_TAG = "book";
	private static final String Section_TAG = "section";
	private static final String Chapter_TAG = "Chapter";
	private static final String Principle_TAG = "Made";
	private static final String Provision_TAG = "Tabsare";
	private static final String Gozine_TAG = "Goz";
	private static final String Number_TAG = "number";
	private static final String Name_TAG = "Name";
	private static final String Mabhas_TAG = "Mabhas";


	public List<Section> parseXML(Context context) {

		InputStream stream = context.getResources().openRawResource(R.raw.keyfari);
		SAXBuilder builder = new SAXBuilder();
		org.jdom2.Element rootNode=null;

		try {

			Document document = (Document) builder.build(stream);
			rootNode = document.getRootElement();
			/// the document is a book!
		} catch (IOException e) {
			Log.i(LOG_TAG, e.getMessage());
		} catch (JDOMException e) {
			Log.i(LOG_TAG, e.getMessage());
		}
		return SectionReader(rootNode);
	}
	public List<Book> parseMojazat(Context context) {

		InputStream stream = context.getResources().openRawResource(R.raw.mojazat);
		SAXBuilder builder = new SAXBuilder();
		org.jdom2.Element rootNode=null;
		List<Book>books=new ArrayList<Book>();

		try {

			Document document = (Document) builder.build(stream);
			rootNode = document.getRootElement();
			/// the document is a book!
			List<org.jdom2.Element> booknodes = rootNode.getChildren(Book_TAG);
			for (Element nodeb : booknodes) {
				Book book = new Book();
				book.setId(Integer.parseInt(nodeb.getAttributeValue(Number_TAG)));
				book.setSections(SectionReader(nodeb));
				books.add(book);
			}
		} catch (IOException e) {
			Log.i(LOG_TAG, e.getMessage());
		} catch (JDOMException e) {
			Log.i(LOG_TAG, e.getMessage());
		}
		return books;
	}
	private List<Section>SectionReader(org.jdom2.Element rootNode)
	{
		List<Section> sections = new ArrayList<Section>();
		List<org.jdom2.Element> sectionsnodes = rootNode.getChildren(Section_TAG);
		for (Element nodes : sectionsnodes) {
			Section section = new Section();
			section.setId(Integer.parseInt(nodes.getAttributeValue(Number_TAG)));
			section.setTitle(nodes.getText().trim());
			List<org.jdom2.Element> chaptersnodes = nodes.getChildren(Chapter_TAG);
			ArrayList<Chapter>chapters=new ArrayList<Chapter>();
			for (Element nodec : chaptersnodes) {
				Chapter chapter=new Chapter();
				chapter.setId(Integer.parseInt(nodec.getAttributeValue(Number_TAG)));
				chapter.setTitle(nodec.getText().trim());
				List<org.jdom2.Element> Mabhassnodes = nodec.getChildren(Mabhas_TAG);
				ArrayList<Mabhas>Mabahes=new ArrayList<Mabhas>();
				for (Element nodem : Mabhassnodes) {
					Mabhas m=new Mabhas();
					m.setId(Integer.parseInt(nodem.getAttributeValue(Number_TAG)));
					m.setTitle(nodem.getText().trim());
					List<org.jdom2.Element> Principlesnodes = nodem.getChildren(Principle_TAG);
					ArrayList<Principle>principles=new ArrayList<Principle>();
					for (Element nodep : Principlesnodes) {
						Principle principle=new Principle();
						principle.setId(Integer.parseInt(nodep.getAttributeValue(Number_TAG)));
						principle.setContent(nodep.getText().trim());
						principle.setChapter(Integer.parseInt(nodec.getAttributeValue(Number_TAG)));
						principle.setSection(Integer.parseInt(nodes.getAttributeValue(Number_TAG)));
						List<org.jdom2.Element> Gozinehanode = nodep.getChildren(Gozine_TAG);
						ArrayList<Gozine>gozineha=new ArrayList<Gozine>();
						for (Element nodeg : Gozinehanode) {

							Gozine goz=new Gozine();
							goz.setContent(nodeg.getText().trim());
							goz.setName(nodeg.getAttributeValue(Name_TAG));
							gozineha.add(goz);
						}

						List<org.jdom2.Element> Provisionsnodes = nodep.getChildren(Provision_TAG);
						ArrayList<Provision>provisions=new ArrayList<Provision>();
						for (Element nodet : Provisionsnodes) {

							Provision prov=new Provision();
							prov.setContent(nodet.getText().trim());
							prov.setNumber(Integer.parseInt(nodet.getAttributeValue(Number_TAG)));
							provisions.add(prov);
						}
						principle.setProvision(provisions);
						principle.setGozineha(gozineha);
						principles.add(principle);
					}
					m.setPrinciples(principles);
					Mabahes.add(m);
				}
				chapter.setMabahes(Mabahes);
				chapters.add(chapter);
			}
			section.setChapters(chapters);
			sections.add(section);
		}
		return sections;

	}

}
