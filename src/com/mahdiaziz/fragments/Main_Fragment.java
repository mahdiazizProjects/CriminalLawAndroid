package com.mahdiaziz.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mahdiaziz.adapters.SectionExpandableAdapter;
import com.mahdiaziz.creatingdata.All_info;
import com.mahdiaziz.creatingdata.Chapter;
import com.mahdiaziz.creatingdata.Mabhas;
import com.mahdiaziz.creatingdata.Principle;
import com.mahdiaziz.creatingdata.Section;
import com.mahdiaziz.fragments.Mabhas_Principle_Fragment.PassingMabhasandPrincipleInterface;
import com.mahdiaziz.fragments.Principle_Content_Fragment.PassingPrinciplesandChaptersInterface;
import com.mahdiaziz.punishmentlaw.MainActivity.refreshdataInterface;
import com.mahdiaziz.punishmentlaw.R;
import com.mahdiaziz.punishmentlaw.Search_Interface;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class Main_Fragment extends Fragment implements PassingPrinciplesandChaptersInterface,PassingMabhasandPrincipleInterface,refreshdataInterface{

	private int type;
	private Book_Fragment bf;
	public interface PassingSectionsInterface{
		public List<Section> getsections();
	}
	List<Section>all;
	List<String>Sections;
	SectionExpandableAdapter listAdap;
	private ArrayList<Principle> principles=new ArrayList<Principle>();
	private ArrayList<Mabhas>mabahes=new ArrayList<Mabhas>();
	private String SectionTitle;
	private String ChapterTitle;
	private int SectionId;
	private int ChapterId;
	ExpandableListView expListView;
	HashMap<String, List<String>> listDataChild;
	private class SelectedChaptersId{
		public Integer sectionId;
		public List<Integer>ChapterIds;
		public SelectedChaptersId()
		{
			this.ChapterIds=new ArrayList<Integer>();
			this.sectionId=0;
		}
	}

	private List<SelectedChaptersId> selectedchapterId=new ArrayList<Main_Fragment.SelectedChaptersId>();
	private ArrayList<Integer>SectionID;
	private Search_Interface calling_the_search=null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		calling_the_search=(Search_Interface)activity;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		prepareListData(type);
		calling_the_search.Button_search_enabler(true);
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroy() {
		calling_the_search.Button_search_enabler(false);
		super.onDestroy();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//		SectionID.clear();

		View rootView = inflater.inflate(R.layout.home, container, false);
		//		TypedArray button=getActivity().getResources().obtainTypedArray(R.array.button);
		expListView =  (ExpandableListView) rootView.findViewById(R.id.expLV);
		// preparing list data
		listAdap=new SectionExpandableAdapter(getActivity(), Sections,listDataChild,SectionID,all,false );
		// setting list adapter
		expListView.setAdapter(listAdap);
		//		expListView.setBackgroundColor(themebody[themeindex]);
		//		expListView.setSelector(button.getDrawable(themeindex));
		//		button.recycle();
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				if(all.get(groupPosition).getChapters().size()<2)
				{
					ArrayList<Chapter>chapters=all.get(selectedchapterId.get(groupPosition).sectionId-1).getChapters();
					Chapter chapter=chapters.get(0);
					principles=chapter.getMabahes().get(0).getPrinciples();
					SectionTitle=all.get(groupPosition).getTitle();
					ChapterTitle=chapters.get(0).getTitle();
//					if(principles.size()>0)
//					{
						Fragment f=new Principle_Content_Fragment();
						if (f != null) {
							f.setTargetFragment(Main_Fragment.this, 1);
							FragmentTransaction fts = getFragmentManager().beginTransaction();
							fts.replace(R.id.frame_container, f);
							fts.addToBackStack(null);
							fts.commit();
						}
//					}
//					else
//					{
//						dialogbox();
//					}
				}
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});
		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				//				ArrayList<Chapter>chapters=all.get(groupPosition).getChapters();
				//				Chapter chapter=chapters.get(childPosition);
				if(all.get(groupPosition).getChapters().size()<2)
				{
					ArrayList<Chapter>chapters=all.get(selectedchapterId.get(groupPosition).sectionId-1).getChapters();
					Chapter chapter=chapters.get(0);
					principles=chapter.getMabahes().get(0).getPrinciples();
//					if(principles.size()>0)
//					{
						SectionTitle=all.get(groupPosition).getTitle();
						ChapterTitle=chapters.get(0).getTitle();
						Fragment f=new Principle_Content_Fragment();
						if (f != null) {
							f.setTargetFragment(Main_Fragment.this, 1);
							FragmentTransaction fts = getFragmentManager().beginTransaction();
							fts.replace(R.id.frame_container, f);
							fts.addToBackStack(null);
							fts.commit();
						}
//					}
//					else
//						dialogbox();
				}
				else
				{
					SectionTitle=all.get(selectedchapterId.get(groupPosition).sectionId-1).getTitle();
					Chapter clicked_Chapter=all.get(selectedchapterId.get(groupPosition).sectionId-1).getChapters().get(selectedchapterId.get(groupPosition).ChapterIds.get(childPosition)-1);
					ChapterTitle=clicked_Chapter.getTitle();
					mabahes=clicked_Chapter.getMabahes();
//					if(mabahes.size()>0)
//					{
						if(mabahes.size()<=1)
						{	
							principles=mabahes.get(0).getPrinciples();

							Fragment f=new Principle_Content_Fragment();
							if (f != null) {
								f.setTargetFragment(Main_Fragment.this, 1);
								FragmentTransaction fts = getFragmentManager().beginTransaction();
								fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
								fts.replace(R.id.frame_container, f);
								fts.addToBackStack(null);
								fts.commit();
							}
						}
						else
						{
							SectionId=all.get(groupPosition).getId();
							ChapterId=clicked_Chapter.getId();
							Fragment f=new Mabhas_Principle_Fragment();
							if (f != null) {
								f.setTargetFragment(Main_Fragment.this, 1);
								FragmentTransaction fts = getFragmentManager().beginTransaction();
								fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
								fts.replace(R.id.frame_container, f);
								fts.addToBackStack(null);
								fts.commit();
							}

						}
//					}
//					else
//						dialogbox();
				}
				return false;
			}
		});
		return rootView;
	}

	private void prepareListData(int type) {
		if(type==0)
		{
			all=All_info.getsections();
		}
		else
		{
			all=bf.getsections();
		}
		selectedchapterId.clear();
		listDataChild = new HashMap<String, List<String>>();
		Sections=new ArrayList<String>();
		SectionID=new ArrayList<Integer>();
		SelectedChaptersId Sid;
		// Adding child data
		for(int ii=0;ii<all.size();ii++)
		{
			Sid=new SelectedChaptersId();
			Sid.sectionId=all.get(ii).getId();
			Sections.add(all.get(ii).getTitle());
			SectionID.add(all.get(ii).getId());
			ArrayList<Chapter> chapters=all.get(ii).getChapters();
			for(int jj=0;jj<chapters.size();jj++)
				Sid.ChapterIds.add(chapters.get(jj).getId());
			listDataChild.put(Sections.get(ii), all.get(ii).getChapterTitles(chapters,getActivity())); // Header, Child data
			selectedchapterId.add(Sid);
		}
	}

	@Override
	public void setTargetFragment(Fragment fragment, int requestCode) {
		Home_Fragment hf=null;
		if(requestCode==3)
		{
			bf=(Book_Fragment) fragment;	
			type=bf.getType();
		}
		else if(requestCode==0)
		{
			hf=(Home_Fragment) fragment;	
			type=hf.getType();
		}
		super.setTargetFragment(fragment, requestCode);
	}

	@Override
	public ArrayList<Principle> getprinciplesforchapters() {
		return principles;
	}
	@Override
	public String getChapterTitle() {
		// TODO Auto-generated method stub
		return ChapterTitle;
	}

	@Override
	public String getSectionTitle() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return ChapterId;
	}

	@Override
	public String getMabhasTitle() {
		return null;
	}

	@Override
	public void RefereshData(String word) {
		//		List<String>tempSections=new ArrayList<String>();
		//		for(int ii=0;ii<Sections.size();ii++)
		//		{
		//			tempSections.add(Sections.get(ii));
		//		}
		//		
		//		HashMap<String, List<String>>templistdataChild=new HashMap<String, List<String>>();
		//		Sections.add(all.get(ii).getTitle());
		SectionID.clear();
		listDataChild.clear();
		Sections.clear();
		selectedchapterId=new ArrayList<Main_Fragment.SelectedChaptersId>();
		for(int ii=0;ii<all.size();ii++)
		{
			SelectedChaptersId Class_Id=new SelectedChaptersId();
			ArrayList<Chapter> chapters=new ArrayList<Chapter>();
			boolean flag=false;
			for(int jj=0;jj<all.get(ii).getChapters().size();jj++)
			{
				if(all.get(ii).getChapters().get(jj).getTitle().contains(word))
				{
					Class_Id.ChapterIds.add(all.get(ii).getChapters().get(jj).getId());
					chapters.add(all.get(ii).getChapters().get(jj));
					flag=true;
				}
			}
			if(flag)
			{
				Sections.add(all.get(ii).getTitle());
				SectionID.add(all.get(ii).getId());
				Class_Id.sectionId=all.get(ii).getId();
				listDataChild.put(all.get(ii).getTitle(), all.get(ii).getChapterTitles(chapters,getActivity()));
				selectedchapterId.add(Class_Id);
			}
		}

		listAdap=new SectionExpandableAdapter(getActivity(), Sections, listDataChild,SectionID,all,true);
		expListView.setAdapter(listAdap);
		for(int ii=0;ii<Sections.size();ii++)
		{
			expListView.expandGroup(ii);
		}
	}

	@Override
	public void ResetData() {
		prepareListData(type);
		listAdap=new SectionExpandableAdapter(getActivity(), Sections, listDataChild,SectionID,all,false);
		expListView.setAdapter(listAdap);

	}

	@Override
	public int getMabhasId() {
		// TODO Auto-generated method stub
		return 0;
	}
//	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//	@SuppressWarnings("deprecation")
//	private void dialogbox()
//	{
//		SharedPreferences settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname,0);
//		int themeindex=settings.getInt(Setting_Fragment.theme,0);
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//		LayoutInflater inflater =getActivity().getLayoutInflater();
//		final View rootview=inflater.inflate(R.layout.dialog_exit_confirm,null);
//		PersianButton bpositive=(PersianButton) rootview.findViewById(R.id.yes);
//		PersianButton bnegative=(PersianButton) rootview.findViewById(R.id.no);
//		int[] themeheader=getActivity().getResources().getIntArray(R.array.colorheader);
//		rootview.setBackgroundColor(themeheader[themeindex]);
//		PersianTextView Tv=(PersianTextView) rootview.findViewById(R.id.msg);
//		Tv.setVisibility(View.GONE);
//
//		int sdk=android.os.Build.VERSION.SDK_INT;
//		TypedArray buttons=getActivity().getResources().obtainTypedArray(R.array.button);
//		if(sdk<android.os.Build.VERSION_CODES.JELLY_BEAN)
//		{
//			bpositive.setBackgroundDrawable(buttons.getDrawable(themeindex));
//			bnegative.setBackgroundDrawable(buttons.getDrawable(themeindex));
//		}
//		else
//		{	
//			bpositive.setBackground(buttons.getDrawable(themeindex));
//			bnegative.setBackground(buttons.getDrawable(themeindex));
//		}
//
//		builder.setView(rootview);
//		builder.setTitle(R.string.demo);
//		final AlertDialog alert = builder.create();
//		buttons.recycle();
//		bpositive.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//
//						Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cafebazaar.ir"));
//						startActivity(i); 
//						alert.dismiss();
//						
//			}
//		});
//		bnegative.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				v.getRootView().setVisibility(View.GONE);
//				alert.dismiss();
//			}
//		});
//		alert.show();
//
//	}
}
