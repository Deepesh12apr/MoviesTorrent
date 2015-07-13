package com.myapp.moviestorrent;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myapp.screenlocktest.AdView;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private ProgressDialog pDialog;
      GridView gv;
	// URL to get contacts JSON
	private static String url = "http://yify-torrents.com/api/list.json";
	//public static String CoverImage;
	// JSON Node names
	private static final String TAG_MOVIE= "MovieTitle";
	private static final String TAG_RATING = "MovieRating";
	private static final String TAG_GENRE = "Genre";
	private static final String TAG_IMG = "CoverImage";
	private static final String TAG_TORRENT = "TorrentUrl";
	private static final String TAG_ID = "MovieID";
	/*private static final String TAG_CONTACTS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_PHONE_MOBILE = "mobile";
	private static final String TAG_PHONE_HOME = "home";
	private static final String TAG_PHONE_OFFICE = "office";*/
    //public static String imgurlin;
	// contacts JSONArray
	JSONArray MovieList = null;
	private AdView adView;
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> showList;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
       
	GridView gv	= (GridView)findViewById(R.id.gridView1);
		
		showList = new ArrayList<HashMap<String, String>>();
       
		ListView lv = getListView();
        
		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String movie = ((TextView) view.findViewById(R.id.movie))
						.getText().toString();
				String rating = ((TextView) view.findViewById(R.id.quality))
						.getText().toString();
				String genre = ((TextView) view.findViewById(R.id.rating))
						.getText().toString();
				String imgurl = ((TextView) view.findViewById(R.id.img))
						.getText().toString();
				String torrenturl = ((TextView) view.findViewById(R.id.torrent))
						.getText().toString();
				String movieid = ((TextView) view.findViewById(R.id.movieid))
						.getText().toString();
				
				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						SingleMovieActivity.class);
				in.putExtra(TAG_MOVIE, movie);
				in.putExtra(TAG_RATING, rating);
				in.putExtra(TAG_GENRE, genre);
				in.putExtra(TAG_IMG, imgurl);
				in.putExtra(TAG_TORRENT, torrenturl);
				in.putExtra(TAG_ID, movieid);
				//in.getBundleExtra(imgurlin);
				startActivity(in);
                     
			}
		});

		// Calling async task to get json
		new GetMovies().execute();
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
	            
	        	//Toast.makeText(this, "going to upcoming",  Toast.LENGTH_LONG).show();
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
	
	
	private class GetMovies extends AsyncTask<Void, Void, Void> {
 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

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
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					
					// Getting JSON Array node
					MovieList = jsonObj.getJSONArray("MovieList");

					// looping through All Contacts
					for (int i = 0; i < MovieList.length(); i++) {
						JSONObject c = MovieList.getJSONObject(i);
					
						//CoverImage = c.getString("CoverImage");
						
						String movietitle = c.getString(TAG_MOVIE);
						String rating = c.getString(TAG_RATING);
						String genre = c.getString(TAG_GENRE);
					    String imgurl = c.getString(TAG_IMG);
					    String torrenturl = c.getString(TAG_TORRENT);
					    String movieid = c.getString(TAG_ID);
						//String address = c.getString(TAG_ADDRESS);
						//String gender = c.getString(TAG_GENDER);

						// Phone node is JSON Object
						//JSONObject phone = c.getJSONObject(TAG_PHONE);
					//	String mobile = phone.getString(TAG_PHONE_MOBILE);
					//	String home = phone.getString(TAG_PHONE_HOME);
					//	String office = phone.getString(TAG_PHONE_OFFICE);

						// tmp hashmap for single contact
						HashMap<String, String> contact = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						contact.put(TAG_MOVIE, movietitle);
						contact.put(TAG_RATING, rating);
						contact.put(TAG_GENRE, genre);
						contact.put(TAG_IMG, imgurl);
						contact.put(TAG_TORRENT, torrenturl);
						contact.put(TAG_ID, movieid);
						
						//contact.put(TAG_PHONE_MOBILE, mobile);

						// adding contact to contact list
						showList.add(contact);
					}
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
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					MainActivity.this, showList,
					R.layout.list_item, new String[] { TAG_MOVIE, TAG_RATING,
							TAG_GENRE , TAG_IMG,TAG_TORRENT,TAG_ID}, new int[] { R.id.movie,
							R.id.quality, R.id.rating , R.id.img,R.id.torrent,R.id.movieid});

			setListAdapter(adapter);
			/*ListAdapter adapt = new SimpleAdapter(
					MainActivity.this, showList,
					R.layout.grid_single, new String[] { TAG_MOVIE, TAG_RATING,
						  TAG_IMG}, new int[] { R.id.grid_title,
							 R.id.grid_img});
			gv.setAdapter(adapt);*/
			
			
		}

	}

    
}
