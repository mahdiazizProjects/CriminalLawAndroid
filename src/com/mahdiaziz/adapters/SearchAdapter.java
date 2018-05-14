package com.mahdiaziz.adapters;

import java.util.ArrayList;

import com.mahdiaziz.fragments.Setting_Fragment;
import com.mahdiaziz.punishmentlaw.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {

	private Context mcontext;
	private ArrayList<String>listdataitems;
	SharedPreferences settings;
	private int[]fontsizes;
	private int[]themebody;
	private int[]themenimbody;
	private int fontsizeindex;
	private Typeface typeFace;
	private int themeindex;
	public SearchAdapter(Context mcontext,ArrayList<String>listdataitems) {
		this.mcontext=mcontext;
		this.listdataitems=listdataitems;
		settings=mcontext.getSharedPreferences(Setting_Fragment.Settingsname, 0);
		String font_name= settings.getString(Setting_Fragment.bodyfont_name, "BNazanin");
		fontsizes=mcontext.getResources().getIntArray(R.array.fontsizes);
		fontsizeindex=settings.getInt(Setting_Fragment.size_font_body, 2);
		typeFace=Typeface.createFromAsset(mcontext.getAssets(),"fonts/"+font_name+".ttf");
		themebody=this.mcontext.getResources().getIntArray(R.array.colorbody);
		themenimbody=this.mcontext.getResources().getIntArray(R.array.colornimheader);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdataitems.size();
	}

	@Override
	public Object getItem(int position) {
		return listdataitems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		String temp=listdataitems.get(position);
		TextView texHolder=null;

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)
					mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.search_item, null);
			texHolder=(TextView) convertView.findViewById(R.id.searchit);
			convertView.setTag(texHolder);
			texHolder.setTextSize(fontsizes[fontsizeindex]);
		}
		else
		{
			texHolder=(TextView) convertView.getTag();
		}
		texHolder.setTypeface(typeFace);
//		temp = temp.replaceAll(word,"<font color='red'>"+word+"</font>");
		texHolder.setText(Html.fromHtml(temp));
		
		if(position%2!=0)
			convertView.setBackgroundColor(themebody[themeindex]);
		else
			convertView.setBackgroundColor(themenimbody[themeindex]);
		return convertView;
	}

}
