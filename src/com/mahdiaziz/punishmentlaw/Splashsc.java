package com.mahdiaziz.punishmentlaw;

//import java.util.ArrayList;
import java.util.List;
import com.mahdiaziz.creatingdata.All_info;
import com.mahdiaziz.creatingdata.Book;
//import com.mahdiaziz.creatingdata.Search_class;
import com.mahdiaziz.creatingdata.Section;
import com.mahdiaziz.xmlparser.RulesJDOMParser;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
public class Splashsc extends ActionBarActivity{
//	private ArrayList<String>Suggestions;
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onResume()
	{
	    super.onResume();

	    if (Build.VERSION.SDK_INT < 16)
	    {
	        // Hide the status bar
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        // Hide the action bar
	        getSupportActionBar().hide();
	    }
	    else
	    {
	        // Hide the status bar
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	        // Hide the action bar
	        getActionBar().hide();
	    }
	}
//	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setContentView(R.layout.splash_layout);
//		LinearLayout splashlayout=(LinearLayout) findViewById(R.id.splash_lay);
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.texture);
//		BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
//		bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
//		splashlayout.setBackgroundDrawable(bitmapDrawable);
		// METHOD 1     

		/****** Create Thread that will sleep for 2 seconds *************/        
		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 1 seconds
					//					sleep(100);
					RulesJDOMParser ruleparser=new RulesJDOMParser();
					List<Book> books=ruleparser.parseMojazat(getBaseContext());
					List<Section>sections=ruleparser.parseXML(getBaseContext());
					All_info.setall(books, sections);
//					Suggestions=new ArrayList<String>();
//					Suggestions=Search_class.FillingwithSuggestions(sections, Suggestions);
					Intent i=new Intent(Splashsc.this,MainActivity.class);
//					i.putStringArrayListExtra("Suggestion", Suggestions);
					startActivity(i);
					finish();

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}
	public void startActivity(Intent intent)
	{
		super.startActivity(intent);
		overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
	}
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}