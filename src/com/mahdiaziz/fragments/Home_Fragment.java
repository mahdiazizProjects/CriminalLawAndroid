package com.mahdiaziz.fragments;

import com.mahdiaziz.adapters.GridviewImageAdapter;
import com.mahdiaziz.adapters.SectionExpandableAdapter;
import com.mahdiaziz.punishmentlaw.R;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class Home_Fragment extends Fragment implements PassingTypeInterface {
	private GridView gridView;
	private int Type;
	private SharedPreferences settings;
	private int themeindex;
	private int[]themebody;
	Integer[] GridviewItems={
			R.drawable.keyfari, R.drawable.mojazat,
			R.drawable.search, R.drawable.setting,
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname, 0);
	}
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview=inflater.inflate(R.layout.main_layout,container,false);
	    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.texture);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
	    bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
//	    rootview.setBackgroundDrawable(bitmapDrawable);
	    SectionExpandableAdapter.setbackground(rootview, bitmapDrawable);
		//////////////////////////////////////////
		themeindex=settings.getInt(Setting_Fragment.theme,0);
		themebody=getResources().getIntArray(R.array.colorheader);
		ImageView iv=(ImageView)rootview.findViewById(R.id.transparentimage);
		iv.setImageDrawable(new ColorDrawable(themebody[themeindex]));
		////////////////////////////////////
		gridView = (GridView)rootview.findViewById(R.id.gridview1);
		// Instance of ImageAdapter Class
		gridView.setAdapter(new GridviewImageAdapter(getActivity(),GridviewItems));
		gridView.setOnItemClickListener(new GridViewItemClick());
		TypedArray button=getActivity().getResources().obtainTypedArray(R.array.button);
		gridView.setSelector(button.getDrawable(themeindex));
		button.recycle();
		return rootview;
	}
	private class GridViewItemClick implements GridView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			Fragment f=null;
			Type=position;
			switch (position) {
			case 0:
				f=new Main_Fragment();
				f.setTargetFragment(Home_Fragment.this, 0);
				break;
			case 1:
				
				f=new Book_Fragment();
				f.setTargetFragment(Home_Fragment.this, 0);
				break;
			case 2:
				f=new Search_Fragment();
				f.setTargetFragment(Home_Fragment.this, 0);
				break;
			case 3:
				f=new Setting_Fragment();
				f.setTargetFragment(Home_Fragment.this, 0);
				break;
			}
			if(f!=null)
			{
				FragmentTransaction fts = getFragmentManager().beginTransaction();
				fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
				fts.replace(R.id.frame_container, f);
				fts.addToBackStack(null);
				fts.commit();
			}
		}

	}
	@Override
	public int getType() {
		return Type;
	}
}
