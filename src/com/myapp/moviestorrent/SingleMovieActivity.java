package com.myapp.moviestorrent;





import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class SingleMovieActivity  extends Activity  {
	
	// JSON node keys
	private static final String TAG_MOVIE = "MovieTitle";
	private static final String TAG_RATING = "MovieRating";
	private static final String TAG_GENRE = "Genre";
	private static final String TAG_IMG = "CoverImage";
	private static final String  TAG_TORRENT = "TorrentUrl";
	private static final String TAG_ID = "MovieID";
	private static final String TAG_DESC = "LongDescription";
	public String torrenturl,desc,aname,cname,sttr,tsttr= " CAST : ";
	
	JSONArray CastList = null;
	//public String mov = " ";
	WebView wb;
	TextView ed ,ac,ds;
	//VideoView vv;
	public String url = null;
	
	
//	static private final String VIDEO = "4SK0cUNMnMM";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        
       wb = (WebView)findViewById(R.id.webView1);
       ed = (TextView) findViewById(R.id.textView1);
       ac = (TextView) findViewById(R.id.actor);
       ds= (TextView) findViewById(R.id.desc);
       
     //  vv = (VideoView)findViewById(R.id.videoView1);
    // get action bar  
       ActionBar actionBar = getActionBar();

       // Enabling Up / Back navigation
       actionBar.setDisplayHomeAsUpEnabled(true);
       
       
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String movie = in.getStringExtra(TAG_MOVIE);
        String rating = in.getStringExtra(TAG_RATING);
        String genre = in.getStringExtra(TAG_GENRE);
        String imgurl = in.getStringExtra(TAG_IMG);
        String movieid = in.getStringExtra(TAG_ID);
              
        
        
        //String imgurl1 = in.getStringExtra(MainActivity.imgurlin);
        torrenturl = in.getStringExtra(TAG_TORRENT);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.movie_label);
        TextView lblEmail = (TextView) findViewById(R.id.quality_label);
        TextView lblMobile = (TextView) findViewById(R.id.rating_label);
        TextView lblmovieid = (TextView) findViewById(R.id.movieid_label);
        
        
        
        Typeface custom_font = Typeface.createFromAsset(getAssets(),
        		"fonts/light.ttf");
    //   ds.setTypeface(custom_font);
        
        
        
        lblName.setText(movie);
        lblEmail.setText("Rating  :"+rating+"/10");
        lblMobile.setText("Genere  :"+genre);
        lblmovieid.setText(movieid);
       // lblimg.setText(imgurl);
        
       // wb.loadUrl(MainActivity.CoverImage);
      // wb.loadUrl(imgurl);
    
    
       new GetMovieDetail().execute();
         
       
   	//String id = movieid;
        url = "http://yify-torrents.com/api/movie.json?id="+movieid;
        
      //	String furl;
      //	furl =url + id;
       
        
        
        
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.item1:
            
        //	Toast.makeText(this, "going to upcoming",  Toast.LENGTH_LONG).show();
        	Intent in = new Intent(this,GridMain.class);
        	in.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(in);
        	
            return true;
        case R.id.item2:
            
            //	Toast.makeText(this, "going to upcoming",  Toast.LENGTH_LONG).show();
            	Intent i = new Intent(this,MainActivity.class);
            	i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
            	startActivity(i);
            	
                return true;
       case R.id.item3:
            
            	Toast.makeText(this, "No Updates Available",  Toast.LENGTH_LONG).show();
            	
            	
                return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	
	/*
	public class yActivity extends YouTubeBaseActivity implements
	YouTubePlayer.OnInitializedListener {

		
		static private final String DEVELOPER_KEY = "AIzaSyB24mzTa-0WEwHbMEvyBUPwk2lHR_I3KNU";
	       //static private final String VIDEO = "4SK0cUNMnMM";
	       @Override
	       protected void onCreate(Bundle savedInstanceState) {
	              super.onCreate(savedInstanceState);
	              setContentView(R.layout.activity_main);
	              YouTubePlayerView youTubeView = (YouTubePlayerView)
	findViewById(R.id.youtube_view);
	           youTubeView.initialize(DEVELOPER_KEY, this);
		
	       }
		@Override
		public void onInitializationFailure(Provider arg0,
				YouTubeInitializationResult arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInitializationSuccess(Provider arg0, YouTubePlayer arg1,
				boolean arg2) {
			// TODO Auto-generated method stub
			 player.loadVideo();
		}}
	
	*/
	 //System.out.println(movieid);
	private class GetMovieDetail extends AsyncTask<Void, Void, Void> {
		 
		
		String yvideo;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);
             
			
			
			if (jsonStr != null) {
				try {
					JSONObject c = new JSONObject(jsonStr);
					
					 String MovieTitle = c.getString("MovieTitle");
					 String mcover = c.getString("LargeCover");
					 // desc = c.getString(TAG_DESC);
					  desc=c.get(TAG_DESC).toString();
							 
				   yvideo = c.getString("YoutubeTrailerID");
					  
					 System.out.println(MovieTitle);
					 System.out.println(mcover);
					 System.out.println(desc);
					 System.out.println(yvideo);
					
					 
					
					wb.loadUrl(mcover);
				
					// vv.setVideoPath(video);
					
					JSONObject jsonObj = new JSONObject(jsonStr);
					// Getting JSON Array node
					CastList = jsonObj.getJSONArray("CastList");

					// looping through All Contacts
					for (int i = 0; i < CastList.length(); i++) {
						JSONObject cl = CastList.getJSONObject(i);
					
						//CoverImage = c.getString("CoverImage");
				           aname = cl.getString("ActorName");
					       cname = cl.getString("CharacterName");
					       sttr =( "\n"+aname.toUpperCase() + " as "+ cname);
					       tsttr = tsttr+"  "+sttr;
					System.out.println(aname +" as "+cname);
					System.out.println(sttr);
					System.out.println(tsttr);
					}
					/*	String movietitle = c.getString(TAG_MOVIE);
						String rating = c.getString(TAG_RATING);
						String genre = c.getString(TAG_GENRE);
					    String imgurl = c.getString(TAG_IMG);
					    String torrenturl = c.getString(TAG_TORRENT);
					    String movieid = c.getString(TAG_ID);*/
					    
						//String address = c.getString(TAG_ADDRESS);
						//String gender = c.getString(TAG_GENDER);

						// Phone node is JSON Object
						//JSONObject phone = c.getJSONObject(TAG_PHONE);
					//	String mobile = phone.getString(TAG_PHONE_MOBILE);
					//	String home = phone.getString(TAG_PHONE_HOME);
					//	String office = phone.getString(TAG_PHONE_OFFICE);

						// tmp hashmap for single contact
						//HashMap<String, String> contact = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						/*contact.put(TAG_MOVIE, movietitle);
						contact.put(TAG_RATING, rating);
						contact.put(TAG_GENRE, genre);
						contact.put(TAG_IMG, imgurl);
						contact.put(TAG_TORRENT, torrenturl);
						contact.put(TAG_ID, movieid);*/
						
					  
					    
					    
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
            
			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			 System.out.println(yvideo);
			ed.setText(yvideo);
			 System.out.println("from post");
			 //ac.setText( new  StringBuilder (tsttr).reverse().toString());
			 ac.setText(tsttr);
		//	 StringBuffer(tsttr).reverse();
				ds.setText("SYNOPSIS  : \n"+desc);
			 
			 
			 
		}

	}	  
	
	
	
	 public void Down(View v)
	 {
		 try {
			 
			 
			 Intent browserIntent = 
                     new Intent(Intent.ACTION_VIEW, Uri.parse(torrenturl));
		    startActivity(browserIntent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	   	}
	 
	 public void trailer(View v)
	 {
		 try {
			 System.out.println("in trailer click");
			Intent i = new Intent(this, trailer.class);
			 i.putExtra(TAG_ID,ed.getText().toString() );
			 System.out.println("in trailer click");
			 startActivity(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 System.out.println("in trailer click");
			e.printStackTrace();
		}
		
	 }

	
}
