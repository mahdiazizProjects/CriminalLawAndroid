package com.mahdiaziz.fragments;
import java.util.ArrayList;
import com.mahdiaziz.adapters.PrincipleAdapter;
import com.mahdiaziz.creatingdata.Gozine;
import com.mahdiaziz.creatingdata.Principle;
import com.mahdiaziz.creatingdata.Provision;
import com.mahdiaziz.creatingdata.Search_class;
import com.mahdiaziz.fragments.Detail_Principle.PassingPrincipleInterface;
import com.mahdiaziz.punishmentlaw.MainActivity.refreshdataInterface;
import com.mahdiaziz.punishmentlaw.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Principle_Content_Fragment extends Fragment implements PassingPrincipleInterface,refreshdataInterface{
	public interface PassingPrinciplesandChaptersInterface{
		public ArrayList<Principle> getprinciplesforchapters();
		public String getChapterTitle();
		public String getSectionTitle();
		public String getMabhasTitle();
		public int getMabhasId();
	}
	private Change_button_state button_state;
	private SharedPreferences settings;
	private int themeindex;
//	private int[]themebody;
	private int[]themenimbody;
	//	private int fontsizebodyindex;
	private int fontsizeheaderindex;
	private int[]fontsizes;
	private final int req_int=1;
	PassingPrinciplesandChaptersInterface passprinciple=null;
	private ArrayList<String>principleTitles;
	private ArrayList<Principle>principles;
	private String Sectiontitle;
	private String Chaptertitle;
	private String MabhasTitle;
	private int MabhasId;
	private int principleindex;
	private ListView lv;
	private PassingPrinciplesandChaptersInterface mf;
	private PrincipleAdapter adapter;
	private boolean from_main_it_comes;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(button_state!=null)
		button_state.changingbutton_state(false);
		this.settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname, 0);
		principles=mf.getprinciplesforchapters();
		Sectiontitle=mf.getSectionTitle();
		Chaptertitle=mf.getChapterTitle();
		MabhasTitle=mf.getMabhasTitle();
		MabhasId=mf.getMabhasId();
		if(MabhasTitle==null)
			MabhasTitle="ندارد";
		fontsizes=getActivity().getResources().getIntArray(R.array.fontsizes);
		fontsizeheaderindex=settings.getInt(Setting_Fragment.size_font_header, 2);
		principleTitles=new ArrayList<String>();
		for(int ii=0;ii<principles.size();ii++)
		{
			String text = "<font color="+String.valueOf(getResources().getColor(R.color.made))+" >"+"ماده"+String.valueOf(principles.get(ii).getId())+"</font> <font color="+String.valueOf(getResources().getColor(R.color.madebody))+">"+"-"+principles.get(ii).getContent()+"</font>";
			principleTitles.add(text);
		}
	}

	@Override
	public void onDestroy() {
		if(button_state!=null)
		button_state.changingbutton_state(true);
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/////////////////////////////////////////
		settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname, 0);
		String Headertitle=settings.getString(Setting_Fragment.header_font_name, "Mitra");
		themeindex=settings.getInt(Setting_Fragment.theme,0);
//		themebody=getResources().getIntArray(R.array.colorbody);
		themenimbody=getResources().getIntArray(R.array.colornimheader);
		TypedArray button=getActivity().getResources().obtainTypedArray(R.array.button);
		////////////////////////////////////
		View rootView = inflater.inflate(R.layout.principles_list, container, false);
		TextView ts=(TextView)rootView.findViewById(R.id.section_title);
		TextView tc=(TextView)rootView.findViewById(R.id.chapter_title);
		TextView tm=(TextView)rootView.findViewById(R.id.Mabhas_title);
		ts.setBackgroundColor(themenimbody[themeindex]);
		tc.setBackgroundColor(themenimbody[themeindex]);
		tm.setBackgroundColor(themenimbody[themeindex]);
		Typeface tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/"+ Headertitle+".ttf");
		ts.setTypeface(tf);
		ts.setTextSize(fontsizes[fontsizeheaderindex]);
		ts.setText(" بخش"+String.valueOf(principles.get(0).getSection())+"-"+Sectiontitle);
		tc.setTypeface(tf);
		tc.setText(" فصل"+String.valueOf(principles.get(0).getChapter())+"-"+Chaptertitle);
		tc.setTextSize(fontsizes[fontsizeheaderindex]);
		tm.setTypeface(tf);
		tm.setText(" مبحث"+String.valueOf(MabhasId)+"-"+MabhasTitle);
		tm.setTextSize(fontsizes[fontsizeheaderindex]);
		lv=(ListView) rootView.findViewById(R.id.listprinciples);
		adapter=new PrincipleAdapter(getActivity(), principleTitles);
		lv.setAdapter(adapter);
		lv.setSelector(button.getDrawable(themeindex));
		button.recycle();
		lv.setOnItemClickListener(new ListViewItemClick());
		return rootView;
	}
	@Override
	public void setTargetFragment(Fragment fragment, int requestCode) {
		if(requestCode==req_int)
		{
			mf=(Main_Fragment)fragment;
			from_main_it_comes=true;
		}
		else if(requestCode==3)
		{
			mf=(Search_Fragment)fragment;
			from_main_it_comes=false;
			button_state=(Change_button_state)fragment;
		}
		super.setTargetFragment(fragment, requestCode);
	}
	private class ListViewItemClick implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			principleindex=position;
			Fragment f=new Detail_Principle();
			if (f != null) {
				f.setTargetFragment(Principle_Content_Fragment.this, 2);
				FragmentTransaction fts = getFragmentManager().beginTransaction();
				fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
				if(from_main_it_comes)
					fts.replace(R.id.frame_container, f);
				else
					fts.replace(R.id.frame_container_search, f);
				fts.addToBackStack(null);
				fts.commit();
			}
		}
	}
	@Override
	public int getprincipleindex() {
		return principleindex;
	}

	@Override
	public ArrayList<Principle> getprinciples() {
		return principles;
	}

	@Override
	public void RefereshData(String word) {
		principleTitles=new ArrayList<String>();

		
		ArrayList<Principle>fulladress=Search_class.Searchprinciples_fromoutside(word, mf.getprinciplesforchapters());
		principles=new ArrayList<Principle>();
		for(int ii=0;ii<fulladress.size();ii++)
		{
			Principle p=new Principle();
			String temp=fulladress.get(ii).getContent();
			p.setId(fulladress.get(ii).getId());
			p.setContent(Search_Fragment.HighlightText(temp, word, 10000, 10000,1));
			ArrayList<Gozine>gozi=new ArrayList<Gozine>();
			for(int gg=0;gg<fulladress.get(ii).getGozineha().size();gg++)
			{
				Gozine g=new Gozine();
				temp+=" "+fulladress.get(ii).getGozineha().get(gg).getName()+"-"+fulladress.get(ii).getGozineha().get(gg).getContent();
				g.setContent(Search_Fragment.HighlightText(fulladress.get(ii).getGozineha().get(gg).getContent(), word, 10000, 10000,1));
				g.setName(fulladress.get(ii).getGozineha().get(gg).getName());
				gozi.add(g);
			}
			p.setGozineha(gozi);
			ArrayList<Provision>pro=new ArrayList<Provision>();
			for(int pp=0;pp<fulladress.get(ii).getProvision().size();pp++)
			{
				temp+=" تبصره "+fulladress.get(ii).getProvision().get(pp).getNumber()+fulladress.get(ii).getProvision().get(pp).getContent();
				Provision pr=new Provision();
				pr.setContent(Search_Fragment.HighlightText(fulladress.get(ii).getProvision().get(pp).getContent(), word, 10000, 10000,1));
				pr.setNumber(fulladress.get(ii).getProvision().get(pp).getNumber());
				pro.add(pr);
			}
			p.setProvision(pro);
			principles.add(p);
			temp=Search_Fragment.HighlightText(temp,word,20,100,1);
			String text = "<font color="+String.valueOf(getResources().getColor(R.color.made))+" >"+"ماده"+String.valueOf(fulladress.get(ii).getId())+"</font>-"+temp;
			principleTitles.add(text);
		}
		adapter=new PrincipleAdapter(getActivity(), principleTitles);
		lv.setAdapter(adapter);
		
	}

	@Override
	public void ResetData() {
		principleTitles=new ArrayList<String>();
		principles=mf.getprinciplesforchapters();
		for(int ii=0;ii<principles.size();ii++)
		{
			String text = "<font color="+String.valueOf(getResources().getColor(R.color.made))+" >"+"ماده"+String.valueOf(principles.get(ii).getId())+"</font> <font color="+String.valueOf(getResources().getColor(R.color.madebody))+">"+"-"+principles.get(ii).getContent()+"</font>";
			principleTitles.add(text);
		}
		adapter=new PrincipleAdapter(getActivity(), principleTitles);
		lv.setAdapter(adapter);
		
	}
}
