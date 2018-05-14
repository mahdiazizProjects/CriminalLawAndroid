package com.mahdiaziz.fragments;

import java.util.ArrayList;

import widgets.PersianButton;
import widgets.PersianTextView;
import com.mahdiaziz.creatingdata.Gozine;
import com.mahdiaziz.creatingdata.Principle;
import com.mahdiaziz.creatingdata.Provision;
import com.mahdiaziz.punishmentlaw.MainActivity.refreshdataInterface;
import com.mahdiaziz.punishmentlaw.R;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Detail_Principle extends Fragment implements refreshdataInterface{
	public interface PassingPrincipleInterface{
		public int getprincipleindex();
		public ArrayList<Principle> getprinciples();
	}
	private Change_button_state button_state;
	private final int req_code=2;
	private PassingPrincipleInterface pf=null;
	//	private Pass sf;
	private int numberrinciples;
	ArrayList<Principle> allprinciples;
	private int principleindex;
	private static String fontname="BNazanin";
	SharedPreferences settings;
	private int[]fontsizes;
	private int fontsizebodyindex;
	private int fontsizeheaderindex;
	private ArrayList<Integer>TextviewId;
	float x1=0, x2=0, y1=0, y2=0, dx=0, dy=0;
	String direction="";
	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		///////////////////////////Setting configuration
		settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname, 0);
		TextviewId=new ArrayList<Integer>();
		////////////////////////////////////////////
		rootView = inflater.inflate(R.layout.principle_detail, container, false);
		all_text(allprinciples.get(principleindex));

		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		allprinciples=pf.getprinciples();
		principleindex=pf.getprincipleindex();
		numberrinciples=pf.getprinciples().size();
		if(button_state!=null)
		button_state.changingbutton_state(false);
	}
	
	
	@Override
	public void onDestroy() {
		if(button_state!=null)
		button_state.changingbutton_state(true);
		super.onDestroy();
	}
	public void setTargetFragment(Fragment fragment, int requestCode) {
		if(requestCode==req_code)
		{
			pf=(Principle_Content_Fragment)fragment;
		}
		else if(requestCode==1)
		{
			pf=(Search_Fragment)fragment;
			button_state=(Change_button_state)fragment;
		}
		else if(requestCode==3)
		{
			pf=(Mabhas_Principle_Fragment)fragment;
		}
		super.setTargetFragment(fragment, requestCode);
	}
	private int convert_pixel(int Pixel)
	{
		float density = getActivity().getResources().getDisplayMetrics().density;
		return (int)(Pixel * density);
	}
	@Override
	public void RefereshData(String word) {

		Principle originalPrinciple=allprinciples.get(principleindex);
		Principle prin=new Principle();
		String temp=originalPrinciple.getContent();
		prin.setContent(Search_Fragment.HighlightText(reversehighliting(temp), word, 10000, 10000,1));
		prin.setId(originalPrinciple.getId());
		ArrayList<Gozine>gozi=new ArrayList<Gozine>();
		for(int gg=0;gg<originalPrinciple.getGozineha().size();gg++)
		{
			Gozine g=new Gozine();
			g.setContent(Search_Fragment.HighlightText(reversehighliting(originalPrinciple.getGozineha().get(gg).getContent()), word, 10000, 10000,1));
			g.setName(originalPrinciple.getGozineha().get(gg).getName());
			gozi.add(g);
		}
		prin.setGozineha(gozi);
		ArrayList<Provision>pro=new ArrayList<Provision>();
		for(int pp=0;pp<originalPrinciple.getProvision().size();pp++)
		{
			Provision pr=new Provision();
			pr.setContent(Search_Fragment.HighlightText(reversehighliting(originalPrinciple.getProvision().get(pp).getContent()), word, 10000, 10000,1));
			pr.setNumber(originalPrinciple.getProvision().get(pp).getNumber());
			pro.add(pr);
		}
		prin.setProvision(pro);
		TextviewSet(prin);

	}
	@Override
	public void ResetData() {

		Principle prin=allprinciples.get(principleindex);
		TextviewSet(prin);
	}
	@SuppressWarnings("deprecation")
	private void all_text(Principle principle)
	{
		int ID_counter=1;
		String font_name_body= settings.getString(Setting_Fragment.bodyfont_name, fontname);
		String font_name_head= settings.getString(Setting_Fragment.header_font_name, fontname);
		fontsizes=getActivity().getResources().getIntArray(R.array.fontsizes);
		fontsizeheaderindex=settings.getInt(Setting_Fragment.size_font_header, 2);
		fontsizebodyindex=settings.getInt(Setting_Fragment.size_font_body, 2);
		int themenimheader[]=getActivity().getResources().getIntArray(R.array.colornimheader);
		int themebody[]=getActivity().getResources().getIntArray(R.array.colorbody);
		int themeindex=settings.getInt(Setting_Fragment.theme, 0);
		TypedArray buttons=getActivity().getResources().obtainTypedArray(R.array.button);
		Principle p=principle;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout linearlayout_all = (LinearLayout)rootView.findViewById(R.id.detailprinclayout);
		linearlayout_all.setLayoutParams(params);
		linearlayout_all.setOrientation(LinearLayout.VERTICAL);
		//////scroll for all
		ScrollView svall=new ScrollView(getActivity());
		svall.setLayoutParams(params);
		////////
		LinearLayout layout_secondlayer=new LinearLayout(getActivity());
		layout_secondlayer.setLayoutParams(params);
		layout_secondlayer.setOrientation(LinearLayout.VERTICAL);
		///////////////
		ScrollView sv=new ScrollView(getActivity());
		sv.setLayoutParams(params);
		LinearLayout Lin=new LinearLayout(getActivity());
		Lin.setLayoutParams(params);
		Lin.setOrientation(LinearLayout.VERTICAL);
		PersianTextView tv =  new PersianTextView(getActivity(),font_name_head);
		tv.setId(ID_counter);
		TextviewId.add(ID_counter);
		ID_counter++;
		String partone=" ماده"+p.getId()+"- ";
		tv.setLineSpacing(1f, 1.1f);
		tv.SetText(partone+p.getContent(),partone.length(),R.color.made,R.color.madebody);
		//		tv.setBackgroundResource(R.color.made_color_back);
		tv.setBackgroundColor(themenimheader[themeindex]);
		//        tv.setTextColor(Color.parseColor("#000000"));
		int Pixel = 10;
		int paddingDp=convert_pixel(Pixel);
		//.setPadding(0,paddingDp,0,0);
		//		paramstext.(10, 10, 10, 0);
		tv.setLayoutParams(paramstext);
		tv.setPadding(paddingDp, paddingDp, paddingDp, 0);
		tv.setTextSize(fontsizes[fontsizeheaderindex]);
		Pixel = 15;
		paddingDp=convert_pixel(Pixel);
		///////set gozineha
		Lin.addView(tv);
		ArrayList<Gozine>goz=p.getGozineha();
		for(int i=0; i<goz.size(); i++)
		{
			tv = new PersianTextView(getActivity(),font_name_body);
			partone=goz.get(i).getName()+"- ";
			tv.SetText(partone+goz.get(i).getContent(),partone.length(),R.color.provision,R.color.madebody);
			tv.setLayoutParams(paramstext);
			tv.setPadding(paddingDp, paddingDp, paddingDp, 0);
			tv.setTextSize(fontsizes[fontsizebodyindex]);
			tv.setLineSpacing(1f, 1.1f);
			//			tv.setBackgroundResource(R.color.gozine_color_back);
			tv.setBackgroundColor(themebody[themeindex]);
			//            tv.setTextColor(Color.parseColor("#000000"));
			tv.setId(ID_counter);
			TextviewId.add(ID_counter);
			ID_counter++;
			Lin.addView(tv);
		}
		ArrayList<Provision>prov=p.getProvision();
		for(int i=0; i<prov.size(); i++)
		{
			tv = new PersianTextView(getActivity(),font_name_body);
			partone="تبصره "+prov.get(i).getNumber()+"- ";
			tv.SetText(partone+prov.get(i).getContent(),partone.length(),R.color.provision,R.color.madebody);
			tv.setLayoutParams(paramstext);
			tv.setTextSize(fontsizes[fontsizebodyindex]);
			tv.setLineSpacing(1f, 1.1f);
			tv.setPadding(paddingDp, paddingDp, paddingDp, 0);
			//			tv.setBackgroundResource(R.color.tabsare_color_back);
			tv.setBackgroundColor(themebody[themeindex]);
			//            tv.setTextColor(Color.parseColor("#000000"));
			tv.setId(ID_counter);
			TextviewId.add(ID_counter);
			ID_counter++;
			Lin.addView(tv);
		}
		sv.addView(Lin);
		/////////////////////////////
		layout_secondlayer.addView(sv);
		////////////////
		LinearLayout.LayoutParams paramsbutton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout butlayout = new LinearLayout(getActivity());
		LinearLayout.LayoutParams layoutparambutton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutparambutton.gravity=Gravity.CENTER_HORIZONTAL;
		butlayout.setLayoutParams(layoutparambutton);
		butlayout.setOrientation(LinearLayout.HORIZONTAL);
		//        paramsbutton.gravity=Gravity.CENTER_HORIZONTAL;

		paramsbutton.setMargins(0, convert_pixel(15), convert_pixel(5), convert_pixel(10));
		final PersianButton b1=new PersianButton(getActivity(),font_name_body);
		if(principleindex<=0)
			b1.setVisibility(View.INVISIBLE);
		b1.setLayoutParams(paramsbutton);
		//		b1.setBackgroundResource(R.drawable.buttonselector);
		b1.setBackgroundDrawable(buttons.getDrawable(themeindex));
		b1.setText(getActivity().getResources().getString(R.string.previous));
		b1.setId(11);
		final PersianButton b2=new PersianButton(getActivity());

		if((principleindex+1)>=numberrinciples)
		{
			b2.setVisibility(View.INVISIBLE);
		}
		b2.setLayoutParams(paramsbutton);
		b2.setText(getActivity().getResources().getString(R.string.next));
		b2.setId(10);
		b2.setBackgroundDrawable(buttons.getDrawable(themeindex));
		buttons.recycle();
		butlayout.addView(b1);
		butlayout.addView(b2);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				if(principleindex>0)
				{
					b2.setVisibility(View.VISIBLE);
					principleindex--;
					getFragmentManager()
					.beginTransaction()
					.detach(Detail_Principle.this)
					.attach(Detail_Principle.this)
					.commit();
				}


			}
		});
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if((principleindex+1)<numberrinciples)
				{
					b1.setVisibility(View.VISIBLE);
					principleindex++;
					getFragmentManager()
					.beginTransaction()
					.detach(Detail_Principle.this)
					.attach(Detail_Principle.this)
					.commit();
				}


			}
		});
		////////////////////////
		layout_secondlayer.addView(butlayout);
		svall.addView(layout_secondlayer);
		linearlayout_all.addView(svall);

		linearlayout_all.setBackgroundColor(themebody[themeindex]);
	}
	private void TextviewSet(Principle p)
	{
		PersianTextView tv=(PersianTextView) rootView.findViewById(TextviewId.get(0));
		String partone=" ماده"+p.getId()+"- ";
		tv.setLineSpacing(1f, 1.1f);
		tv.SetText(partone+p.getContent(),partone.length(),R.color.made,R.color.madebody);
		for(int ii=0;ii<p.getGozineha().size();ii++)
		{
			PersianTextView tv1 = (PersianTextView) rootView.findViewById(TextviewId.get(ii+1));
			partone=p.getGozineha().get(ii).getName()+"- ";
			tv1.SetText(partone+p.getGozineha().get(ii).getContent(),partone.length(),R.color.provision,R.color.madebody);
		}
		ArrayList<Provision>prov=p.getProvision();
		for(int ii=0;ii<p.getProvision().size();ii++)
		{
			PersianTextView tv2 = (PersianTextView) rootView.findViewById(TextviewId.get(ii+p.getGozineha().size()+1));
			partone="تبصره "+prov.get(ii).getNumber()+"- ";
			tv2.SetText(partone+prov.get(ii).getContent(),partone.length(),R.color.provision,R.color.madebody);
		}
	}
	private String reversehighliting(String statement)
	{
		statement=statement.replaceAll("<font color='red'>", "");
		return statement.replaceAll("</font>", "");
	}
}
