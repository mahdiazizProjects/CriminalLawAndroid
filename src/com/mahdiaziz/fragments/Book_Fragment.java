package com.mahdiaziz.fragments;
import java.util.List;

import com.mahdiaziz.adapters.GridviewImageAdapter;
import com.mahdiaziz.creatingdata.All_info;
import com.mahdiaziz.creatingdata.Book;
import com.mahdiaziz.creatingdata.Section;
import com.mahdiaziz.fragments.Main_Fragment.PassingSectionsInterface;
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

public class Book_Fragment extends Fragment implements PassingSectionsInterface,PassingTypeInterface{

	private int type;
	private int themeindex;
	private int[]themebody;
	private SharedPreferences settings;
	@Override
	public void setTargetFragment(Fragment fragment, int requestCode) {
		if(requestCode==0)
		{
			Home_Fragment hf=(Home_Fragment) fragment;
			type=hf.getType();
		}
		super.setTargetFragment(fragment, requestCode);
	}

	private GridView gridView;
	private int pos;
	private List<Book>books;
	Integer[] GridviewItems={
			R.drawable.bookone, R.drawable.booktwo,
			R.drawable.bookthree, R.drawable.bookfour,R.drawable.bookfive
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings=getActivity().getSharedPreferences(Setting_Fragment.Settingsname, 0);
		books=All_info.getbooks();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview=inflater.inflate(R.layout.main_layout,container,false);
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
	    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.texture);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
	    bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
	    rootview.setBackgroundDrawable(bitmapDrawable);
	    button.recycle();
		return rootview;
	}
	private class GridViewItemClick implements GridView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			pos=position;
			Fragment f=new Main_Fragment();
			if(f!=null)
			{
				f.setTargetFragment(Book_Fragment.this, 3);
				FragmentTransaction fts = getFragmentManager().beginTransaction();
				fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
				fts.replace(R.id.frame_container, f);
				fts.addToBackStack(null);
				fts.commit();
			}

		}
	}
	@Override
	public List<Section> getsections() {
		return books.get(pos).getSections();
	}

	@Override
	public int getType() {
		return type;
	}
}