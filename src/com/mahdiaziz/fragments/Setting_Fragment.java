package com.mahdiaziz.fragments;
import java.util.ArrayList;

import widgets.PersianButton;
import widgets.PersianTextView;

import com.mahdiaziz.UI.UIHelper;
import com.mahdiaziz.punishmentlaw.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Setting_Fragment extends Fragment {
	ArrayList<String>Fontname;
	public interface changeactionbarInterface{
		public void changeActionbar();
	}
	public static final String Settingsname="Setting";
	public static final String bodyfont_name="bodyfontname";
	public static final String header_font_name="headerfontname";
	public static final String size_font_header="headerfontsize";
	public static final String size_font_body="bodyfontsize";
	public static final String theme="theme_type";
	private SharedPreferences settings;
	private PersianButton pb;
	private changeactionbarInterface chi=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings=this.getActivity().getSharedPreferences(Settingsname,0);

	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		chi=(changeactionbarInterface)activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.setting_layout, container, false);
		changetheme(rootView);
		String font_name_header=settings.getString(header_font_name, "Mitra");
		String font_name_body=settings.getString(bodyfont_name, "BNazanin");
		int Themetype=settings.getInt(theme, 0);
		int fontsizebodyindex=settings.getInt(size_font_body, 2);
		int fontsizeheaderindex=settings.getInt(size_font_header, 2);
		String[]fontsizenames=getActivity().getResources().getStringArray(R.array.fontsizenames);
		String[]themetypes=getActivity().getResources().getStringArray(R.array.themes);
		UIHelper.SetText(rootView,  R.id.spinner1,  font_name_header);
		UIHelper.SetText(rootView,  R.id.spinner2,  font_name_body);
		UIHelper.SetText(rootView, R.id.spinner3,fontsizenames[fontsizeheaderindex] );
		UIHelper.SetText(rootView, R.id.spinner4,fontsizenames[fontsizebodyindex] );
		UIHelper.SetText(rootView, R.id.spinner5,themetypes[Themetype]);
		pb=(PersianButton) rootView.findViewById(R.id.Ok_Setting_button);

		pb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Setsetting(rootView);
			}
		});
		return rootView;
	}
	private void Setsetting(View rootView)
	{
		SharedPreferences.Editor editor =settings.edit();
		editor.putString(header_font_name, UIHelper.getText(rootView, R.id.spinner1));
		editor.putString(bodyfont_name, UIHelper.getText(rootView, R.id.spinner2));
		editor.putInt(size_font_header,Integer.parseInt(UIHelper.getId(rootView, R.id.spinner3)));
		editor.putInt(size_font_body,Integer.parseInt(UIHelper.getId(rootView, R.id.spinner4)));
		editor.putInt(theme,Integer.parseInt(UIHelper.getId(rootView, R.id.spinner5)));
		editor.commit(); 
		Toast.makeText(getActivity(), "تغییرات با موفقیت ثبت شد.", Toast.LENGTH_LONG).show();
		chi.changeActionbar();
		changetheme(rootView);
	}

	@SuppressWarnings("deprecation")
	private void changetheme(View rootview)
	{
		int themeheader[]=getActivity().getResources().getIntArray(R.array.colorheader);
		int themebody[]=getActivity().getResources().getIntArray(R.array.colorbody);
		TypedArray buttons=getActivity().getResources().obtainTypedArray(R.array.button);
		int themeindex=settings.getInt(theme, 0);
		PersianTextView tv1=(PersianTextView) rootview.findViewById(R.id.head);
		tv1.setBackgroundColor(themeheader[themeindex]);
		PersianTextView tv2=(PersianTextView) rootview.findViewById(R.id.head2);
		tv2.setBackgroundColor(themeheader[themeindex]);
		RelativeLayout rl1=(RelativeLayout) rootview.findViewById(R.id.r1);
		rl1.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl2=(RelativeLayout) rootview.findViewById(R.id.r2);
		rl2.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl3=(RelativeLayout) rootview.findViewById(R.id.r3);
		rl3.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl4=(RelativeLayout) rootview.findViewById(R.id.r4);
		rl4.setBackgroundColor(themebody[themeindex]);
		RelativeLayout rl5=(RelativeLayout) rootview.findViewById(R.id.r5);
		rl5.setBackgroundColor(themebody[themeindex]);
		PersianButton pb=(PersianButton) rootview.findViewById(R.id.Ok_Setting_button);
		pb.setBackgroundDrawable(buttons.getDrawable(themeindex));
		buttons.recycle();
	}
}
