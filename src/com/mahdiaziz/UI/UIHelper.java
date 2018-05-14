package com.mahdiaziz.UI;

import android.view.View;
import android.widget.Spinner;

public class UIHelper {
	
	public static String getText(View view, int id) {
		Spinner et = (Spinner) view.findViewById(id);
		return et.getSelectedItem().toString();
	}
	public static String getId(View view, int id) {
		Spinner et = (Spinner) view.findViewById(id);
		return String.valueOf(et.getSelectedItemId());
	}
	public static void SetText(View view, int id,String Text) {
		Spinner et = (Spinner) view.findViewById(id);
		for(int ii=0;ii<et.getAdapter().getCount();ii++)
			if(et.getItemAtPosition(ii).equals(Text))
			{
				et.setSelection(ii);
			}
	}
	

}
