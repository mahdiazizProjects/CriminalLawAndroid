package com.mahdiaziz.adapters;
import java.util.HashMap;
import java.util.List;

import com.mahdiaziz.fragments.Setting_Fragment;
import com.mahdiaziz.punishmentlaw.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MabhasExpandableAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private SharedPreferences settings;
	private int[]fontsizes;
//	private int[]themebody;
//	private int[]nimbody;
	private int themeindex;
	private int fontsizebodyindex;
	private int fontsizeheaderindex;
	private String FontHeader;
	private String Bodyfont;
	private TypedArray button;
	private TypedArray button2;
	static class ViewHolderItem
	{
		TextView Text;
	}
	public MabhasExpandableAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this.context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.settings=context.getSharedPreferences(Setting_Fragment.Settingsname, 0);
		FontHeader=settings.getString(Setting_Fragment.header_font_name, "Mitra");
		Bodyfont=settings.getString(Setting_Fragment.bodyfont_name, "BNazanin");
		fontsizes=context.getResources().getIntArray(R.array.fontsizes);
		fontsizeheaderindex=settings.getInt(Setting_Fragment.size_font_header, 2);
		fontsizebodyindex=settings.getInt(Setting_Fragment.size_font_body, 2);
//		themebody=context.getResources().getIntArray(R.array.colorbody);
//		nimbody=context.getResources().getIntArray(R.array.colornimheader);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
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

	@SuppressWarnings("deprecation")
	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String childText = (String) getChild(groupPosition, childPosition);
		ViewHolderItem ViewHolder;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.mabhasdatachild, null);
			ViewHolder = new ViewHolderItem();

			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			int Dppixel=convert_pixel(5, context);
			param.setMargins(Dppixel,Dppixel,convert_pixel(10, context),Dppixel);
			ViewHolder.Text = (TextView) convertView.findViewById(R.id.mabhaschildtext);
			ViewHolder.Text.setLayoutParams(param);
			ViewHolder.Text.setMaxLines(1);
			ViewHolder.Text.setEllipsize(TruncateAt.END);
			Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/"+Bodyfont+".ttf");
			ViewHolder.Text.setTypeface(typeFace);
			ViewHolder.Text.setTextSize(fontsizes[fontsizebodyindex]);
			convertView.setTag(ViewHolder);
		}
		else
		{
			ViewHolder=(ViewHolderItem) convertView.getTag();
		}
		button=context.getResources().obtainTypedArray(R.array.list1);
		button2=context.getResources().obtainTypedArray(R.array.list2);
		ViewHolder.Text.setText(Html.fromHtml(childText));
		if(childPosition%2!=0)
			convertView.setBackgroundDrawable(button.getDrawable(themeindex));
		else
			convertView.setBackgroundDrawable(button2.getDrawable(themeindex));
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

	@SuppressWarnings("deprecation")
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		ViewHolderItem viewHolder=null;
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.qa_header, null);
			viewHolder=new ViewHolderItem();
			viewHolder.Text=(TextView) convertView.findViewById(R.id.qa_headertext);
			Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/"+FontHeader+".ttf");
			viewHolder.Text.setTypeface(typeFace, Typeface.BOLD);
			viewHolder.Text.setTextColor(context.getResources().getColor(R.color.mabhas));
			viewHolder.Text.setTextSize(fontsizes[fontsizeheaderindex]);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder= (ViewHolderItem) convertView.getTag();
		}
		 
		button=context.getResources().obtainTypedArray(R.array.list1);
		convertView.setBackgroundDrawable(button.getDrawable(themeindex));
		button.recycle();
		viewHolder.Text.setText(headerTitle);
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
	private int convert_pixel(int Pixel,Context mcontext)
	{
		float density = mcontext.getResources().getDisplayMetrics().density;
		return (int)(Pixel * density);
	}

}