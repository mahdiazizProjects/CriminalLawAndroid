package com.mahdiaziz.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mahdiaziz.adapters.MabhasExpandableAdapter;
import com.mahdiaziz.creatingdata.Gozine;
import com.mahdiaziz.creatingdata.Mabhas;
import com.mahdiaziz.creatingdata.Principle;
import com.mahdiaziz.creatingdata.Provision;
import com.mahdiaziz.creatingdata.Search_class;
import com.mahdiaziz.fragments.Detail_Principle.PassingPrincipleInterface;
import com.mahdiaziz.punishmentlaw.R;
import com.mahdiaziz.punishmentlaw.MainActivity.refreshdataInterface;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class Mabhas_Principle_Fragment extends Fragment implements PassingPrincipleInterface ,refreshdataInterface{
	public interface PassingMabhasandPrincipleInterface{
		public ArrayList<Mabhas>getMabahes();
		public String getChapterTitle();
		public String getSectionTitle();
		public int getSectionId();
		public int getChapterId();
	}
	private Change_button_state button_state;
	private HashMap<String, List<String>> listDataChild;
	private int themeindex;
	private int[]themebody;
	private int fontsizeheaderindex;
	private int[]fontsizes;
	private String SectionTitle;
	private String ChapterTitle;
	private List<Mabhas>mabahes;
	private List<String>mabahesTitle;
	private ExpandableListView expListview;
	private SharedPreferences settings;
	private PassingMabhasandPrincipleInterface passinginterface;
	private MabhasExpandableAdapter adapter;
	private String font_name_header;
	private boolean from_main_it_comes=false;
	private int mabhasindex;
	private int principleindex;
	@Override
	public void onDestroy() {
		if(button_state!=null)
		button_state.changingbutton_state(true);
		super.onDestroy();
	}

	public void onCreate(Bundle savedInstanceState) {
		settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname, 0);
		super.onCreate(savedInstanceState);

		fontsizes=getActivity().getResources().getIntArray(R.array.fontsizes);
		fontsizeheaderindex=settings.getInt(Setting_Fragment.size_font_header, 2);
		font_name_header=settings.getString(Setting_Fragment.header_font_name, "BNazanin");
		prepareListData();
		adapter=new MabhasExpandableAdapter(getActivity(), mabahesTitle, listDataChild);
		if(button_state!=null)
		button_state.changingbutton_state(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.mabhas_principle, container, false);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
		themebody=getResources().getIntArray(R.array.colornimheader);
		int[]themenimbody=getActivity().getResources().getIntArray(R.array.colornimheader);
		TypedArray button=getActivity().getResources().obtainTypedArray(R.array.button);
		TextView ts=(TextView)rootView.findViewById(R.id.section_m);
		TextView tc=(TextView)rootView.findViewById(R.id.chapter_m);
		ts.setBackgroundColor(themenimbody[themeindex]);
		tc.setBackgroundColor(themenimbody[themeindex]);
		rootView.setBackgroundColor(themebody[themeindex]);
		Typeface tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/"+font_name_header +".ttf");
		ts.setTypeface(tf);
		ts.setTextSize(fontsizes[fontsizeheaderindex]);
		ts.setText(getActivity().getResources().getString(R.string.section)+passinginterface.getSectionId()+"-"+SectionTitle);
		tc.setTypeface(tf);
		tc.setText(getActivity().getResources().getString(R.string.chapter)+passinginterface.getChapterId()+"-"+ChapterTitle);
		tc.setTextSize(fontsizes[fontsizeheaderindex]);
		expListview =  (ExpandableListView) rootView.findViewById(R.id.expmabhas_priciple);
//		expListview.setBackgroundColor(themebody[themeindex]);
//		expListview.setSelector(button.getDrawable(themeindex));
		button.recycle();
		expListview.setAdapter(adapter);
		expListview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				principleindex=childPosition;
				mabhasindex=groupPosition;
				Fragment f=new Detail_Principle();
				if (f != null) {
					f.setTargetFragment(Mabhas_Principle_Fragment.this, 3);
					FragmentTransaction fts = getFragmentManager().beginTransaction();
					fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
					if(from_main_it_comes)
						fts.replace(R.id.frame_container, f);
					else
						fts.replace(R.id.frame_container_search, f);
					fts.addToBackStack(null);
					fts.commit();
				}
				return false;
			}
		});

		return rootView;			
	}
	@Override
	public void setTargetFragment(Fragment fragment, int requestCode) {
		passinginterface=(PassingMabhasandPrincipleInterface)fragment;
		if(requestCode==1)
		{
			from_main_it_comes=true;
		}
		else
		{
			from_main_it_comes=false;
			button_state=(Change_button_state)fragment;
		}

		super.setTargetFragment(fragment, requestCode);
	}
	private void prepareListData() {

		mabahes=passinginterface.getMabahes();
		mabahesTitle=new ArrayList<String>();
		SectionTitle=passinginterface.getSectionTitle();
		ChapterTitle=passinginterface.getChapterTitle();
		listDataChild = new HashMap<String, List<String>>();
		// Adding child data
		for(int ii=0;ii<mabahes.size();ii++)
		{
			mabahesTitle.add(getActivity().getResources().getString(R.string.mabhas)+mabahes.get(ii).getId()+"-"+mabahes.get(ii).getTitle());
			List<String>Madeha=new ArrayList<String>();
			for(int kk=0;kk<mabahes.get(ii).getPrinciples().size();kk++)
			{
				Madeha.add("<font color="+String.valueOf(getResources().getColor(R.color.made))+" >"+getActivity().getResources().getString(R.string.made)+String.valueOf(mabahes.get(ii).getPrinciples().get(kk).getId())+"</font><font color="+String.valueOf(getResources().getColor(R.color.madebody))+">"+"-"+mabahes.get(ii).getPrinciples().get(kk).getContent()+"</font>");
			}
			listDataChild.put(mabahesTitle.get(ii),Madeha ); // Header, Child data
		}
	}

	@Override
	public int getprincipleindex() {

		return principleindex;
	}

	@Override
	public ArrayList<Principle> getprinciples() {
		return mabahes.get(mabhasindex).getPrinciples();
	}

	@Override
	public void RefereshData(String word) {
		listDataChild.clear();
		mabahesTitle.clear();
		mabahes=passinginterface.getMabahes();
		List<Mabhas>mabahestemp=new ArrayList<Mabhas>();
		// Adding child data
		for(int ii=0;ii<mabahes.size();ii++)
		{
			Mabhas mab=new Mabhas();
			ArrayList<Principle>fulladress=Search_class.Searchprinciples_fromoutside(word, mabahes.get(ii).getPrinciples());
			ArrayList<Principle> principles=new ArrayList<Principle>();
			boolean flag=false;
			String temp="";
			for(int jj=0;jj<fulladress.size();jj++)
			{
				flag=true;
				Principle p=new Principle();
				temp=fulladress.get(jj).getContent();
				p.setId(fulladress.get(jj).getId());
				p.setContent(Search_Fragment.HighlightText(temp, word, 10000, 10000,1));
				ArrayList<Gozine>gozi=new ArrayList<Gozine>();
				for(int gg=0;gg<fulladress.get(jj).getGozineha().size();gg++)
				{
					Gozine g=new Gozine();
					temp+=" "+fulladress.get(jj).getGozineha().get(gg).getName()+"-"+fulladress.get(jj).getGozineha().get(gg).getContent();
					g.setContent(Search_Fragment.HighlightText(fulladress.get(jj).getGozineha().get(gg).getContent(), word,  10000, 10000,1));
					g.setName(fulladress.get(jj).getGozineha().get(gg).getName());
					gozi.add(g);
				}
				p.setGozineha(gozi);
				ArrayList<Provision>pro=new ArrayList<Provision>();
				for(int pp=0;pp<fulladress.get(jj).getProvision().size();pp++)
				{
					temp+=getActivity().getResources().getString(R.string.tabsare)+fulladress.get(jj).getProvision().get(pp).getNumber()+fulladress.get(jj).getProvision().get(pp).getContent();
					Provision pr=new Provision();
					pr.setContent(Search_Fragment.HighlightText(fulladress.get(jj).getProvision().get(pp).getContent(), word,  10000, 10000,1));
					pr.setNumber(fulladress.get(jj).getProvision().get(pp).getNumber());
					pro.add(pr);
				}
				p.setProvision(pro);
				principles.add(p);
			}

			mab.setPrinciples(principles);
			mabahestemp.add(mab);
			if(flag)
			{
				mabahesTitle.add(getActivity().getString(R.string.mabhas)+mabahes.get(ii).getId()+"-"+mabahes.get(ii).getTitle());
				ArrayList<String>Madeha=new ArrayList<String>();
				for(int kk=0;kk<mabahestemp.get(ii).getPrinciples().size();kk++)
				{
					Madeha.add("<font color="+String.valueOf(getResources().getColor(R.color.made))+" >"+getActivity().getResources().getString(R.string.made)+String.valueOf(mabahestemp.get(ii).getPrinciples().get(kk).getId())+"-"+"</font>"+Search_Fragment.HighlightText(mabahestemp.get(ii).getPrinciples().get(kk).getContent(), word, 20, 300,1));
				}
				listDataChild.put(mabahesTitle.get(ii),Madeha ); // Header, Child data
			}

		}
		mabahes=new ArrayList<Mabhas>();
		mabahes.addAll(mabahestemp);
		adapter=new MabhasExpandableAdapter(getActivity(), mabahesTitle, listDataChild);
		expListview.setAdapter(adapter);

	}

	@Override
	public void ResetData() {
		prepareListData();
		adapter=new MabhasExpandableAdapter(getActivity(), mabahesTitle, listDataChild);
		expListview.setAdapter(adapter);

	}
}
