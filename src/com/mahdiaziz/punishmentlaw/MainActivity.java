package com.mahdiaziz.punishmentlaw;
import java.util.ArrayList;
import java.util.List;

import widgets.PersianButton;
import widgets.PersianEditView;
import com.mahdiaziz.appearance.NavDrawerItem;
import com.mahdiaziz.appearance.NavDrawerListAdapter;
import com.mahdiaziz.creatingdata.All_info;
import com.mahdiaziz.creatingdata.Search_class;
import com.mahdiaziz.fragments.Detail_Principle;
import com.mahdiaziz.fragments.Getsuggestions;
import com.mahdiaziz.fragments.Home_Fragment;
import com.mahdiaziz.fragments.Mabhas_Principle_Fragment;
import com.mahdiaziz.fragments.Main_Fragment;
import com.mahdiaziz.fragments.Principle_Content_Fragment;
import com.mahdiaziz.fragments.QA;
import com.mahdiaziz.fragments.Search_Fragment;
import com.mahdiaziz.fragments.Setting_Fragment;
import com.mahdiaziz.fragments.Setting_Fragment.changeactionbarInterface;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends ActionBarActivity implements changeactionbarInterface,Search_Interface,Getsuggestions{


	public interface refreshdataInterface{
		public void RefereshData(String word);
		public void ResetData();
	}
	public interface disablingsearchcontent
	{
		public void disabling_search_content();
		public boolean get_button_state();
	}
	private disablingsearchcontent disable_search=null;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private SharedPreferences settings;
	// nav drawer title
	private CharSequence mDrawerTitle;
	private static final int ANIMATION_DURATION=100;
	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public static boolean focus_explistchid;
	///////////////////////////////////////
	private View customActionBarView;
	private PersianEditView SearchTextview;
	private ImageView Search_button; 
	//////////////////////////////////////////////
	private boolean is_search_button_showing;
	private String searchingWord;
	private refreshdataInterface RefereshingData;
	private ImageView backbut;
	private ImageView Prog_ico;
	private final int sdk=android.os.Build.VERSION.SDK_INT;
	/////////////////////////////
	TypedArray buttons;
	private int themeindex;
	int[]themeheader;
	private ArrayList<String>Suggestions;


	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		////////////////////////

		new GetsuggestionAsync().execute();
		//		Suggestions=getIntent().getStringArrayListExtra("Suggestion");
		settings=getSharedPreferences(Setting_Fragment.Settingsname, 0);
//		boolean first_time_gift=settings.getBoolean("first_time", true);
//		if(first_time_gift)
//		{
//			Show_greeting_page();
//		}
		buttons=getResources().obtainTypedArray(R.array.button);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
		////////////////////////////////// 
		//		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		mTitle = mDrawerTitle = getTitle();
		focus_explistchid=false;
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Main
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Contact Us
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Question
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		//	Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		mDrawerList.setSelector(buttons.getDrawable(themeindex));
		if(sdk<android.os.Build.VERSION_CODES.JELLY_BEAN)
			mDrawerList.setBackgroundDrawable(buttons.getDrawable(themeindex));
		else
			mDrawerList.setBackground(buttons.getDrawable(themeindex));
		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		// enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		//		getSupportActionBar().setBackgroundDrawable(buttons.getDrawable(themeindex));
		setupActionBar();
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
			}
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
			}
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if (item != null && item.getItemId() == android.R.id.home) {
					if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
						mDrawerLayout.closeDrawer(Gravity.RIGHT);
					} else {
						mDrawerLayout.openDrawer(Gravity.RIGHT);
					}
				}
				return false;
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		//		case R.id.menu_exit:
		//			finish();
		//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//		menu.findItem(R.id.menu_exit).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing com.mahdiaziz.fragments

		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = null;
		switch (position) {
		case 0:
			//			List<Fragment>f=fragmentManager.getFragments();
			//			if (f != null) {
			//				for(int ii=0;ii<f.size();ii++)
			//				{
			//					if(f.get(ii) instanceof Home_Fragment)
			//					{
			//						fragment=f.get(ii);
			//						break;
			//					}
			//
			//				}

			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			//			}
			//			if (fragment == null) {
			fragment = new Home_Fragment();
			//			}
			break;
		case 1:
			Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
					"mailto","mahdixdear@gmail.com", null));
			i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.suggestionorcomplain));
			startActivity(Intent.createChooser(i, "ارسال ایمیل با..."));
			break;
		case 2:
			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment=new QA();
			break;
		case 3:
			finish();
			System.exit(0);
		default:
			break;
		}
		if(fragment!=null)
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		setTitle(navMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() > 0) {
			if(disable_search!=null)
				if (getResources().getBoolean(R.bool.is_small_layout)&&disable_search.get_button_state()) {
					disable_search.disabling_search_content();
					return;
				}
			if(is_search_button_showing)
			{
				SearchTextview.setText(null);
				SearchTextview.setVisibility(View.INVISIBLE);
				Prog_ico.setVisibility(View.VISIBLE);
				SearchTextview.postDelayed(new Runnable() {
					@Override
					public void run() {
						fadeInToLeft(Search_button, true);
					}
				}, ANIMATION_DURATION-5);
				is_search_button_showing=false;
				RefereshingData.ResetData();
			}
			else
			{
				List<Fragment>f=fragmentManager.getFragments();
				/////////////removing the last and null fragments
				//				f.remove(f.size()-1);
				int indexfragment=f.size()-2;
				if(f.size()>1)
				{
					for(int ii=0;ii<f.size();ii++)
					{
						if(f.get(ii)==null)
							indexfragment--;
					}
					Fragment fragment=f.get(indexfragment);

					if(fragment instanceof Main_Fragment || fragment instanceof Principle_Content_Fragment||fragment instanceof Detail_Principle||fragment instanceof Mabhas_Principle_Fragment)
					{
						RefereshingData=(refreshdataInterface)fragment;
						RefereshingData.ResetData();
					}
				}
				fragmentManager.popBackStack();
			}
			////////// refereshing data when the back button is clicked and 
		}
		else
		{
			boolean flag=false;
			List<Fragment>f=fragmentManager.getFragments();
			for(int ii=0;ii<f.size();ii++)
				if(f.get(ii)!=null && f.get(ii) instanceof QA)
				{
					getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
					Fragment fragment = new Home_Fragment();
					FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
					fts.setCustomAnimations(R.anim.slide_right_to_left, R.anim.slide_left_to_right);
					fts.replace(R.id.frame_container, fragment).commit();
					flag=true;
					break;

				}
			if(!flag)
			{	
				themeindex=settings.getInt(Setting_Fragment.theme,0);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				LayoutInflater inflater =getLayoutInflater();
				final View rootview=inflater.inflate(R.layout.dialog_exit_confirm,null);
				PersianButton bpositive=(PersianButton) rootview.findViewById(R.id.yes);
				PersianButton bnegative=(PersianButton) rootview.findViewById(R.id.no);
				themeheader=getResources().getIntArray(R.array.colorheader);
				rootview.setBackgroundColor(themeheader[themeindex]);

				if(sdk<android.os.Build.VERSION_CODES.JELLY_BEAN)
				{
					bpositive.setBackgroundDrawable(buttons.getDrawable(themeindex));
					bnegative.setBackgroundDrawable(buttons.getDrawable(themeindex));
				}
				else
				{	
					bpositive.setBackground(buttons.getDrawable(themeindex));
					bnegative.setBackground(buttons.getDrawable(themeindex));
				}

				builder.setView(rootview);
				final AlertDialog alert = builder.create();
				alert.show();
				bpositive.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						alert.dismiss();
						finish();

					}
				});
				bnegative.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						alert.dismiss();
						v.getRootView().setVisibility(View.INVISIBLE);
					}
				});
			}
		}
	}
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void changeActionbar() {
		TypedArray buttons=getResources().obtainTypedArray(R.array.button);
		themeindex=settings.getInt(Setting_Fragment.theme,0);
		getSupportActionBar().hide();

		customActionBarView.setBackgroundDrawable(buttons.getDrawable(themeindex));
		buttons.recycle();
		getSupportActionBar().show();
		getSupportActionBar().setCustomView(customActionBarView, 
				new ActionBar.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 
						ViewGroup.LayoutParams.MATCH_PARENT
						)
				);
	}
	@SuppressWarnings("deprecation")
	private void setupActionBar() {

		// Inflate a "Done/Cancel" custom action bar view.
		LayoutInflater inflater = (LayoutInflater) this
				.getSupportActionBar().getThemedContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		customActionBarView = inflater.inflate(
				R.layout.custom_actionbar, null);
		ImageView DrawerButton=(ImageView)customActionBarView.findViewById(R.id.drawer_ico);
		DrawerButton.setOnClickListener(new DrawerClick());
		if(sdk<android.os.Build.VERSION_CODES.JELLY_BEAN)
			DrawerButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonselectortype2));
		else
			DrawerButton.setBackground(getResources().getDrawable(R.drawable.buttonselectortype2));
		Search_button=(ImageView) customActionBarView.findViewById(R.id.search_image);
		backbut=(ImageView) customActionBarView.findViewById(R.id.back_but);
		if(sdk<android.os.Build.VERSION_CODES.JELLY_BEAN)
			backbut.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonselectortype2));
		else
			backbut.setBackground(getResources().getDrawable(R.drawable.buttonselectortype2));
		backbut.setOnClickListener(new BackClicklistener());
		SearchTextview=(PersianEditView)customActionBarView.findViewById(R.id.search_actionbar);
		Prog_ico=(ImageView) customActionBarView.findViewById(R.id.program_ico);
		if(sdk<android.os.Build.VERSION_CODES.JELLY_BEAN)
			customActionBarView.setBackgroundDrawable(buttons.getDrawable(themeindex));
		else
			customActionBarView.setBackground(buttons.getDrawable(themeindex));
		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | 
				ActionBar.DISPLAY_SHOW_HOME | 
				ActionBar.DISPLAY_SHOW_TITLE);

		getSupportActionBar().setCustomView(customActionBarView, 
				new ActionBar.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 
						ViewGroup.LayoutParams.MATCH_PARENT
						)
				);
		SearchTextview.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				searchingWord=s.toString();
				RefereshingData.RefereshData(searchingWord);

			}
		});
		Search_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!is_search_button_showing)
				{
					fadeInToRight(v,true);
					v.postDelayed(new Runnable() {
						@Override
						public void run() {
							SearchTextview.setVisibility(View.VISIBLE);
							Prog_ico.setVisibility(View.INVISIBLE);
						}
					}, ANIMATION_DURATION-50);
					is_search_button_showing=true;
				}
			}
		});
	}
	private void fadeInToRight(View v, boolean animated) {
		//		final float Dppixel=getResources().getDisplayMetrics().density;
		SearchTextview.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		if(sdk>=android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
			v.animate().
			translationXBy(SearchTextview.getMeasuredWidth()/2).
			alpha(100).setDuration(animated ? ANIMATION_DURATION : 0).
			setInterpolator(new LinearInterpolator()).
			start();
		else
			com.nineoldandroids.view.ViewPropertyAnimator.animate(v).
			translationXBy(SearchTextview.getMeasuredWidth()/2).
			alpha(100).setDuration(animated ? ANIMATION_DURATION : 0).
			setInterpolator(new LinearInterpolator()).
			start();


	}
	private void fadeInToLeft(View v, boolean animated) {
		if(sdk>=android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
			v.animate().
			translationXBy(-SearchTextview.getMeasuredWidth()/2).
			alpha(100).setDuration(ANIMATION_DURATION).
			setInterpolator(new LinearInterpolator()).
			start();
		else
			com.nineoldandroids.view.ViewPropertyAnimator.animate(v).
			translationXBy(-SearchTextview.getMeasuredWidth()/2).
			alpha(100).setDuration(ANIMATION_DURATION).
			setInterpolator(new LinearInterpolator()).
			start();


	}
	@Override
	public void Button_search_enabler(boolean is_active) {
		if(is_active)
		{
			Search_button.setVisibility(View.VISIBLE);
		}
		else
		{
			Search_button.setVisibility(View.INVISIBLE);
		}

	}
	@Override
	public void onAttachFragment(Fragment fragment) {
		//		this.current_fragment=fragment;
		super.onAttachFragment(fragment);
		if(fragment instanceof Main_Fragment || fragment instanceof Principle_Content_Fragment||fragment instanceof Detail_Principle||fragment instanceof Mabhas_Principle_Fragment)
			RefereshingData=(refreshdataInterface)fragment;
		if(fragment instanceof Search_Fragment)
			disable_search=(disablingsearchcontent)fragment;
	}

	private class DrawerClick implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {

			if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
			} else {
				mDrawerLayout.openDrawer(Gravity.RIGHT);
			}
		}

	}
	private class BackClicklistener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			MainActivity.this.onBackPressed();
		}

	}

	@Override
	public ArrayList<String> getSuggestions() {

		return Suggestions;
	}
	private class GetsuggestionAsync extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			Suggestions=Search_class.FillingwithSuggestions(All_info.getsections(), new ArrayList<String>());
			return null;
		}

	}
	private void Show_greeting_page()
	{
		final Dialog dialog=new Dialog(this,R.style.Transparent);
		dialog.setContentView(R.layout.whatsnew_title);
		dialog.setCanceledOnTouchOutside(true);
		final CheckBox c=(CheckBox) dialog.findViewById(R.id.notshowagain);
		WindowManager manager = (WindowManager)getSystemService(Activity.WINDOW_SERVICE);
		int width, height;
		Display display =  manager.getDefaultDisplay();
		width = getDisplaySize(display).x;
		height = getDisplaySize(display).y;
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = width;
		lp.height = height;
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(c.isChecked())
					settings.edit().putBoolean("first_time", false).commit();
				else
					settings.edit().putBoolean("first_time", true).commit();
				
			}
		});
		PersianButton but=(PersianButton) dialog.findViewById(R.id.closebut);
		but.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			dialog.dismiss();
				
			}
		});
	}
	@SuppressWarnings("deprecation")
	private static Point getDisplaySize(final Display display) {
		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		return point;
	}
}
