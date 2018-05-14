package com.mahdiaziz.adapters;
import com.mahdiaziz.punishmentlaw.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridviewImageAdapter extends BaseAdapter {
	private Context mContext;
	public Integer[] mThumbIds;
    public GridviewImageAdapter(Context c,Integer[]mThumbIds){
        mContext = c;
        this.mThumbIds=mThumbIds;
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.gridview_item), mContext.getResources().getDimensionPixelSize(R.dimen.gridview_itemh)));
        return imageView;
    }
}
