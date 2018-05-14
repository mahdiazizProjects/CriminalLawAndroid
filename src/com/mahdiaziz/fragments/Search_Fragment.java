package com.mahdiaziz.fragments;

import java.util.ArrayList;
import java.util.List;

import widgets.PersianButton;
import widgets.PersianTextView;
import com.mahdiaziz.adapters.SearchAdapter;
import com.mahdiaziz.creatingdata.All_info;
import com.mahdiaziz.creatingdata.Book;
import com.mahdiaziz.creatingdata.Chapter;
import com.mahdiaziz.creatingdata.Mabhas;
import com.mahdiaziz.creatingdata.Principle;
import com.mahdiaziz.creatingdata.Search_class;
import com.mahdiaziz.creatingdata.Section;
import com.mahdiaziz.fragments.Detail_Principle.PassingPrincipleInterface;
import com.mahdiaziz.fragments.Mabhas_Principle_Fragment.PassingMabhasandPrincipleInterface;
import com.mahdiaziz.fragments.Principle_Content_Fragment.PassingPrinciplesandChaptersInterface;
import com.mahdiaziz.punishmentlaw.MainActivity.disablingsearchcontent;
import com.mahdiaziz.punishmentlaw.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Search_Fragment extends Fragment implements PassingPrincipleInterface,PassingPrinciplesandChaptersInterface,PassingMabhasandPrincipleInterface,disablingsearchcontent,Change_button_state{

	private SharedPreferences settings;
	private AutoCompleteTextView Ed;
	private Spinner sp;
	private Spinner sp_search_asli;
	private Spinner Spinner_book;
	private Spinner Spinner_exact;
	private ListView lv;
	private static int type_search_faree;
	private static int typeasli;
	private PersianTextView found_textview;
	private ArrayList<Principle>fulladress;
	private static int position_listview;
	private int SectionId;
	private int ChapterId;
	private int MabhasId;
	private String MabhasTitle;
	private String chapterTitle;
	private String SectionTitle;
	private ArrayList<Mabhas>Selectedmabahes;
	private ArrayList<Principle>Selectedprinciples;
	private ArrayList<Chapter>Selectedchapters;
	private Getsuggestions Suggestions=null;
	private boolean is_small_layout;
	private LinearLayout org_layout;
	private LinearLayout search_layout_small;
	ArrayAdapter<String>suggestionadapter;
	RelativeLayout search_book_layout;
	private boolean buttton_is_clicked=false;
	private ArrayList<Principle>Principles=new ArrayList<Principle>();
	ArrayList<Mabhas>mabahes=new ArrayList<Mabhas>();
	private List<Section>allsections=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings=this.getActivity().getSharedPreferences(Setting_Fragment.Settingsname,0);
		is_small_layout=getActivity().getResources().getBoolean(R.bool.is_small_layout);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Suggestions=(Getsuggestions)activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.search_layout, container, false);
		if(is_small_layout)
			search_layout_small=(LinearLayout) rootView.findViewById(R.id.layout_search_small);
		org_layout=(LinearLayout) rootView.findViewById(R.id.layout_search);
		if(is_small_layout)
			found_textview=(PersianTextView) rootView.findViewById(R.id.found_result_text2);
		else
		found_textview=(PersianTextView) rootView.findViewById(R.id.found_result_text);
		changetheme(rootView);
		//////////////////

		Ed=(AutoCompleteTextView) rootView.findViewById(R.id.editsearch);
		allsections=All_info.getsections();
		//		Suggestions=Search_class.FillingwithSuggestions(allsections, Suggestions);
		suggestionadapter=new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Suggestions.getSuggestions());
		Ed.setAdapter(suggestionadapter);
		/////////////////////////////////
		//		Ed.addTextChangedListener(new Textwacher());
		sp=(Spinner) rootView.findViewById(R.id.spinnersearch);
		Spinner_book=(Spinner) rootView.findViewById(R.id.spinnerbook);
		Spinner_book.setOnItemSelectedListener(new BookItemListener());
		sp_search_asli=(Spinner) rootView.findViewById(R.id.spinnertype);
		sp_search_asli.setOnItemSelectedListener(new ItemSelectedSpinner());
		Spinner_exact=(Spinner) rootView.findViewById(R.id.exactrelativespinner);
		return rootView;
	}
	@SuppressWarnings("deprecation")
	private void changetheme(View rootview)
	{
		int themeheader[]=getActivity().getResources().getIntArray(R.array.colorheader);
		int themebody[]=getActivity().getResources().getIntArray(R.array.colorbody);
		TypedArray buttons=getActivity().getResources().obtainTypedArray(R.array.button);
		int themeindex=settings.getInt(Setting_Fragment.theme, 0);
		PersianTextView tv1=(PersianTextView) rootview.findViewById(R.id.searchhead);
		tv1.setBackgroundColor(themeheader[themeindex]);
		RelativeLayout r_exactrelative=(RelativeLayout) rootview.findViewById(R.id.exact_layout);
		r_exactrelative.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl1=(RelativeLayout) rootview.findViewById(R.id.rsearch1);
		rl1.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl3=(RelativeLayout) rootview.findViewById(R.id.r1search);
		rl3.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl2=(RelativeLayout) rootview.findViewById(R.id.r2search);
		rl2.setBackgroundColor(themebody[themeindex]);
		search_book_layout=(RelativeLayout) rootview.findViewById(R.id.booklayout);
		search_book_layout.setBackgroundColor(themebody[themeindex]);
		PersianButton pb=(PersianButton) rootview.findViewById(R.id.Ok_search_button);
		pb.setBackgroundDrawable(buttons.getDrawable(themeindex));
		pb.setOnClickListener(new Buttonclicklistener());
		found_textview.setBackgroundColor(themeheader[themeindex]);
		if(is_small_layout)
			lv=(ListView) rootview.findViewById(R.id.list_search_small);
		else
			lv=(ListView) rootview.findViewById(R.id.foundlist);
		lv.setSelector(buttons.getDrawable(themeindex));
		buttons.recycle();
	}
	private ArrayList<String>Searchword(String word,int typeasli,int typefaree,int type_exact)
	{
		ArrayList<String>Result=new ArrayList<String>();
		fulladress=new ArrayList<Principle>();
		Selectedchapters=new ArrayList<Chapter>();
		Selectedmabahes=new ArrayList<Mabhas>();
		Selectedprinciples=new ArrayList<Principle>();
		//		SelectedSections=new ArrayList<Section>();
		String ChapterTitle=null;
		String SectionTitle=null;
		//////////////searching in keyfari
		if(typeasli==0)
		{
			allsections=All_info.getsections();
		}
		else
		{
			List<Book>allbooks=All_info.getbooks();
			allsections=allbooks.get(Spinner_book.getSelectedItemPosition()).getSections();
		}
		if(typefaree==0)
		{

			fulladress=Search_class.Searchprinciples(word, fulladress, allsections,type_exact);
			fulladress=Search_class.Searchmabhas(word, fulladress, allsections,type_exact);
			fulladress=Search_class.Searchchapters(word, fulladress, allsections,type_exact);
			fulladress=Search_class.SearchSections(word, fulladress, allsections,type_exact);
			word=word.replaceAll("ماده", "");
			word=word.replaceAll(" ", "");
			int[]charint=new int[word.length()];
			StringBuilder s=new StringBuilder();
			int wordcount=word.length();
			int count=0;
			for(int uu=0;uu<word.length();uu++)
			{
				if((word.codePointAt(uu)>1776)&&(word.codePointAt(uu)<1787))
				{
					charint[uu]=(int)word.codePointAt(uu)-1728;
					s.append((char)charint[uu]);
					count++;
				}

			}
			if(count==wordcount)
				word=word.replace(word,s);
			fulladress=Search_class.Searchprinciplestitle( word, fulladress, allsections,type_exact);

			for(int ii=0;ii<fulladress.size();ii++)
			{
				if(fulladress.get(ii).getId()!=0)
				{
					Selectedprinciples.add(fulladress.get(ii));
					String temp=fulladress.get(ii).getContent();
					for(int gg=0;gg<fulladress.get(ii).getGozineha().size();gg++)
					{
						temp+=" "+fulladress.get(ii).getGozineha().get(gg).getName()+"-"+fulladress.get(ii).getGozineha().get(gg).getContent();
					}
					for(int pp=0;pp<fulladress.get(ii).getProvision().size();pp++)
					{
						temp+=" تبصره "+fulladress.get(ii).getProvision().get(pp).getNumber()+fulladress.get(ii).getProvision().get(pp).getContent();
					}
					temp=HighlightText(temp,word,50,200,type_exact);
					temp="ماده "+fulladress.get(ii).getId()+" - " + temp;
					Result.add(temp);
				}
				else if(fulladress.get(ii).getMabhas()!=0)
				{
					Selectedmabahes.add(allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getMabahes().get(fulladress.get(ii).getMabhas()-1));
					MabhasTitle=allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getMabahes().get(fulladress.get(ii).getMabhas()-1).getTitle();
					MabhasTitle=HighlightText(MabhasTitle,word,30,200,type_exact);
					ChapterTitle=allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getTitle();
					SectionTitle=allsections.get(fulladress.get(ii).getSection()-1).getTitle();
					String temp="مبحث "+fulladress.get(ii).getMabhas()+" "+MabhasTitle+" "+ "فصل"+ fulladress.get(ii).getChapter()+ChapterTitle+" "+"بخش "+fulladress.get(ii).getSection()+ SectionTitle;
					Result.add(temp);
				}
				else if(fulladress.get(ii).getChapter()!=0)
				{
					Selectedchapters.add(allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1));
					ChapterTitle=allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getTitle();
					ChapterTitle=HighlightText(ChapterTitle, word,50,200,type_exact);
					SectionTitle=allsections.get(fulladress.get(ii).getSection()-1).getTitle();
					String temp="فصل"+ fulladress.get(ii).getChapter()+ChapterTitle+" "+"بخش "+fulladress.get(ii).getSection()+ SectionTitle;
					Result.add(temp);
				}
				else
				{
					//					String temp=HighlightText(allsections.get(fulladress.get(ii).getSection()-1).getTitle(), word,100,200);
					//					Result.add("بخش"+ fulladress.get(ii).getSection()+temp);
				}

			}

		}
		////////////searching sections
		//		else if(typefaree==1)
		//		{
		//			fulladress=Search_class.SearchSections(word, fulladress, allsections);
		//			for(int ii=0;ii<fulladress.size();ii++)
		//			{
		//				///// adding -1 since the array index starts from 0
		//				Result.add("بخش"+ fulladress.get(ii).getSection()+allsections.get(fulladress.get(ii).getSection()-1).getTitle());
		//			}
		//		}
		else if(typefaree==1)
		{
			fulladress=Search_class.Searchchapters(word, fulladress, allsections,type_exact);
			for(int ii=0;ii<fulladress.size();ii++)
			{
				ChapterTitle=allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getTitle();
				ChapterTitle=HighlightText(ChapterTitle, word,50,200,type_exact);
				SectionTitle=allsections.get(fulladress.get(ii).getSection()-1).getTitle();
				String temp="فصل"+ fulladress.get(ii).getChapter()+ChapterTitle+" "+"بخش "+fulladress.get(ii).getSection()+ SectionTitle;
				Result.add(temp);
				Selectedchapters.add(allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1));
			}
		}
		else if(typefaree==2)
		{
			fulladress=Search_class.Searchmabhas(word, fulladress, allsections,type_exact);
			for(int ii=0;ii<fulladress.size();ii++)
			{
				MabhasTitle=allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getMabahes().get(fulladress.get(ii).getMabhas()-1).getTitle();
				MabhasTitle=HighlightText(MabhasTitle,word,50,200,type_exact);
				ChapterTitle=allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getTitle();
				SectionTitle=allsections.get(fulladress.get(ii).getSection()-1).getTitle();
				Selectedmabahes.add(allsections.get(fulladress.get(ii).getSection()-1).getChapters().get(fulladress.get(ii).getChapter()-1).getMabahes().get(fulladress.get(ii).getMabhas()-1));
				String temp=" مبحث"+fulladress.get(ii).getMabhas()+MabhasTitle+" "+"فصل"+ fulladress.get(ii).getChapter()+ChapterTitle+" "+"بخش "+fulladress.get(ii).getSection()+ SectionTitle;
				Result.add(temp);
			}
		}
		else if(typefaree==3)
		{
			word=word.replaceAll("ماده", "");
			word=word.replaceAll(" ", "");
			int[]charint=new int[word.length()];
			StringBuilder s=new StringBuilder();
			int wordcount=word.length();
			int count=0;
			for(int uu=0;uu<word.length();uu++)
			{
				if((word.codePointAt(uu)>1776)&&(word.codePointAt(uu)<1787))
				{
					charint[uu]=(int)word.codePointAt(uu)-1728;
					s.append((char)charint[uu]);
					count++;
				}

			}
			if(count==wordcount)
				word=word.replace(word,s);
			fulladress=Search_class.Searchprinciplestitle( word, fulladress, allsections,type_exact);

			for(int ii=0;ii<fulladress.size();ii++)
			{
				Selectedprinciples.add(fulladress.get(ii));
				String temp=fulladress.get(ii).getContent();
				for(int gg=0;gg<fulladress.get(ii).getGozineha().size();gg++)
				{
					temp+=" "+fulladress.get(ii).getGozineha().get(gg).getName()+"-"+fulladress.get(ii).getGozineha().get(gg).getContent();
				}
				for(int pp=0;pp<fulladress.get(ii).getProvision().size();pp++)
				{
					temp+=" تبصره "+fulladress.get(ii).getProvision().get(pp).getNumber()+fulladress.get(ii).getProvision().get(pp).getContent();
				}
				temp=HighlightText(temp,word,50,200,type_exact);
				temp="ماده "+fulladress.get(ii).getId()+" - " + temp;
				Result.add(temp);
			}
		}
		else if(typefaree==4)
		{
			fulladress=Search_class.Searchprinciples(word, fulladress, allsections,type_exact);

			for(int ii=0;ii<fulladress.size();ii++)
			{
				Selectedprinciples.add(fulladress.get(ii));
				String temp=fulladress.get(ii).getContent();
				for(int gg=0;gg<fulladress.get(ii).getGozineha().size();gg++)
				{
					temp+=" "+fulladress.get(ii).getGozineha().get(gg).getName()+"-"+fulladress.get(ii).getGozineha().get(gg).getContent();
				}
				for(int pp=0;pp<fulladress.get(ii).getProvision().size();pp++)
				{
					temp+=" تبصره "+fulladress.get(ii).getProvision().get(pp).getNumber()+fulladress.get(ii).getProvision().get(pp).getContent();
				}
				temp=HighlightText(temp,word,50,200,type_exact);
				temp="ماده "+fulladress.get(ii).getId()+" - " + temp;
				Result.add(temp);
			}
		}
		/////////////////////////////////searching in mojazat

		return Result;
	}
	private class Buttonclicklistener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			int type_exact_relative=Spinner_exact.getSelectedItemPosition();
			buttton_is_clicked=true;
			String word=Ed.getText().toString().trim();
			if(word.equals(""))
			{
				Toast.makeText(getActivity(), "لطفا کلمه مورد جستجو را وارد کنید", Toast.LENGTH_LONG).show();
				return;
			}
			typeasli=sp_search_asli.getSelectedItemPosition();
			type_search_faree=sp.getSelectedItemPosition();
			ArrayList<String>listdata=Searchword(word,typeasli,type_search_faree,type_exact_relative);
			if(listdata.size()==0)
			{
				Toast.makeText(getActivity(), "چیزی پیدا نشد.", Toast.LENGTH_LONG).show();
			}
			SearchAdapter sa=new SearchAdapter(getActivity(), listdata);
			if(is_small_layout)
			{
				search_layout_small.setVisibility(View.VISIBLE);
				org_layout.setVisibility(View.GONE);
			}
			found_textview.setVisibility(PersianTextView.VISIBLE);
			lv.setVisibility(ListView.VISIBLE);
			lv.setAdapter(sa);
			lv.setOnItemClickListener(new ListviewOnitemClick());
		}

	}
	private class ListviewOnitemClick implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			position_listview=position;
			///////////////////////////
			SectionId=fulladress.get(position_listview).getSection();
			ChapterId=fulladress.get(position_listview).getChapter();
			MabhasId=fulladress.get(position_listview).getMabhas();
			SectionTitle=allsections.get(fulladress.get(position_listview).getSection()-1).getTitle();
			if(ChapterId==0)
				chapterTitle="ندارد";
			else
				chapterTitle=allsections.get(fulladress.get(position_listview).getSection()-1).getChapters().get(fulladress.get(position_listview).getChapter()-1).getTitle();
			if(MabhasId==0)
				MabhasTitle="ندارد";
			else
				MabhasTitle=allsections.get(fulladress.get(position_listview).getSection()-1).getChapters().get(fulladress.get(position_listview).getChapter()-1).getMabahes().get(fulladress.get(position_listview).getMabhas()-1).getTitle();
			/////////////////////////////
			if(type_search_faree==0)
			{
				if(fulladress.get(position).getId()!=0)
				{
					Fragment f=new Detail_Principle();
					if (f != null) {
						f.setTargetFragment(Search_Fragment.this, 1);
						FragmentTransaction fts = getFragmentManager().beginTransaction();
						fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
						fts.replace(R.id.frame_container_search, f);
						fts.addToBackStack(null);
						fts.commit();
					}
					//					position_listview=position-Selectedchapters.size()-SelectedSections.size()-Selectedmabahes.size();
				}
				else if(fulladress.get(position).getMabhas()!=0)
				{
					position_listview=position-Selectedprinciples.size();
					Principles=Selectedmabahes.get(position_listview).getPrinciples();
					Fragment f=new Principle_Content_Fragment();
					if (f != null) {
						f.setTargetFragment(Search_Fragment.this, 3);
						FragmentTransaction fts = getFragmentManager().beginTransaction();
						fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
						fts.replace(R.id.frame_container_search, f);
						fts.addToBackStack(null);
						fts.commit();
					}
				}
				else if(fulladress.get(position).getChapter()!=0)
				{


					position_listview=position-Selectedmabahes.size()-Selectedprinciples.size();
					mabahes=Selectedchapters.get(position_listview).getMabahes();
					if(mabahes.size()<=1)
					{	
						Principles=Selectedchapters.get(position_listview).getMabahes().get(0).getPrinciples();
						Fragment f=new Principle_Content_Fragment();
						if (f != null) {
							f.setTargetFragment(Search_Fragment.this, 3);
							FragmentTransaction fts = getFragmentManager().beginTransaction();
							fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
							fts.replace(R.id.frame_container_search, f);
							fts.addToBackStack(null);
							fts.commit();
						}
					}
					else
					{

						Fragment f=new Mabhas_Principle_Fragment();
						if (f != null) {
							f.setTargetFragment(Search_Fragment.this, 2);
							FragmentTransaction fts = getFragmentManager().beginTransaction();
							fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
							fts.replace(R.id.frame_container_search, f);
							fts.addToBackStack(null);
							fts.commit();
						}
					}

				}
				else
				{

				}

			}
			///////////////////sections
			//			else if(type_search_faree==1)
			//			{
			//
			//			}
			///////////////////chapters
			else if(type_search_faree==1)
			{
				mabahes=Selectedchapters.get(position_listview).getMabahes();
				if(mabahes.size()<=1)
				{	
					Principles=Selectedchapters.get(position_listview).getMabahes().get(0).getPrinciples();
					Fragment f=new Principle_Content_Fragment();
					if (f != null) {
						f.setTargetFragment(Search_Fragment.this, 3);
						FragmentTransaction fts = getFragmentManager().beginTransaction();
						fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
						fts.replace(R.id.frame_container_search, f);
						fts.addToBackStack(null);
						fts.commit();
					}
				}
				else
				{

					Fragment f=new Mabhas_Principle_Fragment();
					if (f != null) {
						f.setTargetFragment(Search_Fragment.this, 2);
						FragmentTransaction fts = getFragmentManager().beginTransaction();
						fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
						fts.replace(R.id.frame_container_search, f);
						fts.addToBackStack(null);
						fts.commit();
					}
				}
			}
			else if(type_search_faree==2)
			{
				position_listview=position;
				Principles=Selectedmabahes.get(position_listview).getPrinciples();
				Fragment f=new Principle_Content_Fragment();
				if (f != null) {
					f.setTargetFragment(Search_Fragment.this, 3);
					FragmentTransaction fts = getFragmentManager().beginTransaction();
					fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
					fts.replace(R.id.frame_container_search, f);
					fts.addToBackStack(null);
					fts.commit();
				}
			}
			//////////////////principles
			else if(type_search_faree==3||type_search_faree==4)
			{
				Fragment f=new Detail_Principle();
				if (f != null) {
					f.setTargetFragment(Search_Fragment.this, 1);
					FragmentTransaction fts = getFragmentManager().beginTransaction();
					fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
					fts.replace(R.id.frame_container_search, f);
					fts.addToBackStack(null);
					fts.commit();
				}
			}

		}
	}
	@Override
	public int getprincipleindex() {
		return position_listview;
	}
	@Override
	public ArrayList<Principle> getprinciples() {
		return Selectedprinciples;
	}
	@Override
	public String getChapterTitle() {
		return  chapterTitle;
	}
	@Override
	public String getSectionTitle() {
		return SectionTitle;
	}
	@Override
	public ArrayList<Mabhas> getMabahes() {
		return mabahes;
	}
	@Override
	public int getSectionId() {
		return SectionId;
	}
	@Override
	public int getChapterId() {
		return ChapterId;
	}
	@Override
	public ArrayList<Principle> getprinciplesforchapters() {
		return Principles;
	}
	@Override
	public String getMabhasTitle() {
		return MabhasTitle;
	}
	public static String HighlightText(String temp,String word,int firstspacing,int endspacing,int type_exact)
	{
		int index=temp.indexOf(word);
		int first=index-firstspacing;
		int last=index+endspacing;
		boolean start=false;
		boolean end=false;
		if(first<=0)
		{
			first=0;
			start=true;
		}
		if(last>=temp.length())
		{
			last=temp.length();
			end=true;
		}
		temp=temp.substring(first, last);
		if(!start)
			temp="..."+temp;
		if(!end)
			temp+="...";
		if(type_exact==1)
			temp=temp.replaceAll(word,"<font color='red'>"+word+"</font>");
		else
		{
			String[]contents=Search_class.Stringfixer(temp).split(" ");
			temp="";
			for(int ii=0;ii<contents.length;ii++)
				if(contents[ii].contentEquals(word))
					temp+=contents[ii].replace(word,"<font color='red'>"+word+"</font>")+" ";
				else
					temp+=contents[ii]+" ";

			//			temp=temp.replace(word,"<font color='red'>"+word+"</font>");
		}
		return temp;
	}
	@Override
	public int getMabhasId() {
		return MabhasId;
	}
	private class ItemSelectedSpinner implements AdapterView.OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(position==1)
			{
				search_book_layout.setVisibility(View.VISIBLE);
				List<Book>b=All_info.getbooks();
				allsections=b.get(Spinner_book.getSelectedItemPosition()).getSections();
				ArrayList<String> Sug=new ArrayList<String>();
				Sug=Search_class.FillingwithSuggestions(allsections, Sug);
				suggestionadapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item, Sug);
				Ed.setAdapter(suggestionadapter);

			}
			else
			{

				search_book_layout.setVisibility(View.GONE);
				//				allsections=All_info.getsections();
				//				Suggestions=new ArrayList<String>();
				//				Suggestions=Search_class.FillingwithSuggestions(allsections, Suggestions);
				suggestionadapter=new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Suggestions.getSuggestions());
				Ed.setAdapter(suggestionadapter);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}
	private class BookItemListener implements AdapterView.OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			List<Book>b=All_info.getbooks();
			allsections=b.get(position).getSections();
			ArrayList<String> Sug=new ArrayList<String>();
			Sug=Search_class.FillingwithSuggestions(allsections, Sug);
			suggestionadapter=new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Sug);
			Ed.setAdapter(suggestionadapter);

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}
	@Override
	public void disabling_search_content() {
		search_layout_small.setVisibility(View.GONE);
		org_layout.setVisibility(View.VISIBLE);
		buttton_is_clicked=false;
		
	}
	@Override
	public boolean get_button_state() {
		return buttton_is_clicked;
	}
	@Override
	public void changingbutton_state(boolean clicked) {
		buttton_is_clicked=clicked;
		
	}
}
