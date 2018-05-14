package com.mahdiaziz.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.mahdiaziz.adapters.QA_Adapter;
import com.mahdiaziz.punishmentlaw.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class QA extends Fragment {

	private HashMap<String, String[]> listDataChild;
	private ExpandableListView expListview;
	private QA_Adapter adapter;
	private ArrayList<String>Questions;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prepareListData();
		adapter=new QA_Adapter(getActivity(), Questions, listDataChild);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.qa_layout, container, false);
		expListview =  (ExpandableListView) rootView.findViewById(R.id.expLV_qa);
		expListview.setAdapter(adapter);

		return rootView;			
	}
	private void prepareListData() {
		Questions=new ArrayList<String>();
		Questions.add(getActivity().getResources().getString(R.string.q1));
		Questions.add(getActivity().getResources().getString(R.string.q2));
		Questions.add(getActivity().getResources().getString(R.string.q3));
		Questions.add(getActivity().getResources().getString(R.string.q4));
		Questions.add(getActivity().getResources().getString(R.string.q5));
		Questions.add(getActivity().getResources().getString(R.string.q6));
		Questions.add(getActivity().getResources().getString(R.string.q7));
		Questions.add(getActivity().getResources().getString(R.string.q8));
		Questions.add(getActivity().getResources().getString(R.string.q9));
		Questions.add(getActivity().getResources().getString(R.string.q10));
		Questions.add(getActivity().getResources().getString(R.string.q11));
		Questions.add(getActivity().getResources().getString(R.string.q12));
		Questions.add(getActivity().getResources().getString(R.string.q13));
		listDataChild = new HashMap<String, String[]>();
		int ii=0;
		listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r1) ); // Header, Child data
		 ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r2) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r3) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r4) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r5) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r6) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r7) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r8) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r9) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r10) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r11) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r12) ); // Header, Child data
	     ii++;
	     listDataChild.put(Questions.get(ii),getActivity().getResources().getStringArray(R.array.r13) ); // Header, Child data
	}


}
