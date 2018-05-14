package com.mahdiaziz.adapters;
import java.util.List;

import com.mahdiaziz.fragments.Setting_Fragment;
import com.mahdiaziz.punishmentlaw.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PrincipleAdapter extends BaseAdapter {
	private List<String>PrincipleTitle;
	SharedPreferences settings;
	private Context context;
	private int[]fontsizes;
	private int fontsizeindex;
	private int themeindex;
//	private int[]themebody;
//	private int[]themenimbody;
	private TypedArray button;
	private TypedArray button2;
	private Typeface typeFace;
	public PrincipleAdapter(Context context,List<String>PrincipleTitle) {
		super();
		this.PrincipleTitle=PrincipleTitle;
		this.context=context;
		settings=context.getSharedPreferences(Setting_Fragment.Settingsname, 0);
		String font_name= settings.getString(Setting_Fragment.bodyfont_name, "BNazanin");
		fontsizes=context.getResources().getIntArray(R.array.fontsizes);
		fontsizeindex=settings.getInt(Setting_Fragment.size_font_body, 2);
		typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/"+font_name+".ttf");
//		themebody=this.context.getResources().getIntArray(R.array.colorbody);
//		themenimbody=this.context.getResources().getIntArray(R.array.colornimheader);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
	}
	@Override
	public int getCount() {
		return PrincipleTitle.size();
	}

	@Override
	public Object getItem(int position) {
		return PrincipleTitle.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String temp=PrincipleTitle.get(position);
		TextView texHolder=null;

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)
					context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.principle_row, null);
			texHolder=(TextView) convertView.findViewById(R.id.principletextrow);
			convertView.setTag(texHolder);
			
		}
		else
		{
			texHolder=(TextView) convertView.getTag();
		}
		button=context.getResources().obtainTypedArray(R.array.list1);
		button2=context.getResources().obtainTypedArray(R.array.list2);
		texHolder.setTypeface(typeFace);
		texHolder.setText(Html.fromHtml(temp));
		texHolder.setTextSize(fontsizes[fontsizeindex]);
		if(position%2!=0)
			convertView.setBackgroundDrawable(button.getDrawable(themeindex));
		else
			convertView.setBackgroundDrawable(button2.getDrawable(themeindex));
		button.recycle();
		button2.recycle();
		return convertView;
	}
}