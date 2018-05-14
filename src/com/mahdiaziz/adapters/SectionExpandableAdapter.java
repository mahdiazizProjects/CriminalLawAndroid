package com.mahdiaziz.adapters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mahdiaziz.creatingdata.Section;
import com.mahdiaziz.fragments.Setting_Fragment;
import com.mahdiaziz.punishmentlaw.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
@SuppressLint("NewApi")
public class SectionExpandableAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private SharedPreferences settings;
	private int[]fontsizes;
	private int themeindex;
	private int fontsizebodyindex;
	private int fontsizeheaderindex;
	private String FontHeader;
	private String Bodyfont;
	private ArrayList<Integer>sectionID;
	private List<Section> all;
	private boolean search_state;
	private TypedArray button;
	private TypedArray button2;
	private class holdViewitem
	{
		public TextView txtListChild;
		public TextView counter;
	}
	public SectionExpandableAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData,ArrayList<Integer>sectionID,List<Section> numberPrinciples,boolean search_state) {
		this.context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.sectionID=sectionID;
		this.all=numberPrinciples;
		this.settings=context.getSharedPreferences(Setting_Fragment.Settingsname, 0);
		FontHeader=settings.getString(Setting_Fragment.header_font_name, "Mitra");
		Bodyfont=settings.getString(Setting_Fragment.bodyfont_name, "BNazanin");
		fontsizes=context.getResources().getIntArray(R.array.fontsizes);
		fontsizeheaderindex=settings.getInt(Setting_Fragment.size_font_header, 2);
		fontsizebodyindex=settings.getInt(Setting_Fragment.size_font_body, 2);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
		this.search_state=search_state;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String childText = (String) getChild(groupPosition, childPosition);
		holdViewitem items=null;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.listdatachild, null);
			items=new holdViewitem();
			items.txtListChild=(TextView) convertView
					.findViewById(R.id.listdchildtext);
			items.counter=(TextView)convertView.findViewById(R.id.counter);
			convertView.setTag(items);
		}
		else
		{
			items=(holdViewitem)convertView.getTag();
		}

//		TextView txtListChild = (TextView) convertView
//				.findViewById(R.id.listdchildtext);
		items.txtListChild.setText(childText);
//		TextView counter =(TextView)convertView.findViewById(R.id.counter);
		if(!search_state)
		{
			int count=0;
			for(int ii=0;ii<all.get(sectionID.get(groupPosition)-1).getChapters().get(childPosition).getMabahes().size();ii++)
			{
				count+=all.get(sectionID.get(groupPosition)-1).getChapters().get(childPosition).getMabahes().get(ii).getPrinciples().size();
			}
			items.counter.setText(String.valueOf(count));
			items.counter.setVisibility(View.VISIBLE);
		}
		else
		{
			items.counter.setVisibility(View.GONE);
		}
		button=context.getResources().obtainTypedArray(R.array.list1);
		button2=context.getResources().obtainTypedArray(R.array.list2);
		Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/"+Bodyfont+".ttf");
		items.txtListChild.setTypeface(typeFace);
		items.txtListChild.setTextSize(fontsizes[fontsizebodyindex]);
		if(childPosition%2!=0)
//			convertView.setBackgroundDrawable(button.getDrawable(themeindex));
			setbackground(convertView, button.getDrawable(themeindex));
		else
//			convertView.setBackgroundDrawable(button2.getDrawable(themeindex));
			setbackground(convertView, button2.getDrawable(themeindex));
		//		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
		//	    @SuppressWarnings("deprecation")
		//		BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
		//	    bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
		//		convertView.setBackground(bitmapDrawable);
		//		convertView.set
		button.recycle();
		button2.recycle();
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.header_item, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.headertext);
		Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/"+FontHeader+".ttf");
		lblListHeader.setTypeface(typeFace, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		lblListHeader.setTextSize(fontsizes[fontsizeheaderindex]);
		ImageView iv=(ImageView) convertView.findViewById(R.id.itemimage);
		
		int ID=sectionID.get(groupPosition)-1;
		if(ID==0)
		{
			iv.setImageResource(R.drawable.sectionone);
		}
		else if(ID==1)
		{
			iv.setImageResource(R.drawable.sectiontwo);
		}

		else if(ID==2)
		{
			iv.setImageResource(R.drawable.sectionthree);
		}
		else if(ID==3)
		{
			iv.setImageResource(R.drawable.sectionfour);
		}
		else if(ID==4)
		{
			iv.setImageResource(R.drawable.sectionfive);
		}
		else if(ID==5)
		{
			iv.setImageResource(R.drawable.sectionsix);
		}
		else
		{
			iv.setImageResource(R.drawable.sectionseven);
		}
		button=context.getResources().obtainTypedArray(R.array.list1);
//		convertView.setBackgroundDrawable(button.getDrawable(themeindex));
		setbackground(convertView, button.getDrawable(themeindex));
		button.recycle();
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	@SuppressWarnings("deprecation")
	public static void setbackground(View v,Drawable d)
	{
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
		    v.setBackgroundDrawable(d);
		} else {
		    v.setBackground(d);
		}
	}

}