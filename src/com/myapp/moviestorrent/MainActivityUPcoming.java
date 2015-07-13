package com.myapp.moviestorrent;


import java.util.ArrayList;
import java.util.HashMap;
import android.app.ActionBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityUPcoming extends ListActivity {

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "http://yify-torrents.com/api/Upcoming.json";
	//public static String CoverImage;
	// JSON Node names
	private static final String TAG_MOVIE= "MovieTitle";
	private static final String TAG_RATING = "ImdbCode";
	//private static final String TAG_GENRE = "Genre";
	private static final String TAG_IMG = "MovieCover";
	String[] arra;
	String[] name;
	//private static final String TAG_TORRENT = "TorrentUrl";
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
	//JSONArray MovieList = null;
     
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> showList;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
				
				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						SingleUpcomingActivity.class);
				in.putExtra(TAG_MOVIE, movie);
				in.putExtra(TAG_RATING, rating);
				//in.putExtra(TAG_GENRE, genre);
				in.putExtra(TAG_IMG, imgurl);
			//	in.putExtra(TAG_TORRENT, torrenturl);
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
	        	Intent in = new Intent(this,MainActivityUPcoming.class);
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
			pDialog = new ProgressDialog(MainActivityUPcoming.this);
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

				    JSONArray array = new JSONArray(jsonStr);
				    arra = new String[array.length()];
					name = new String[array.length()];
				    for (int i = 0; i < array.length(); i++)
				    {
				        JSONObject obj= array.getJSONObject(i);
				        String MovieTitle = obj.getString("MovieTitle");
				        System.out.println(MovieTitle);
						
						String movietitle = obj.getString(TAG_MOVIE);
						String rating = obj.getString(TAG_RATING);
						//String genre = obj.getString(TAG_GENRE);
					    String imgurl = obj.getString(TAG_IMG);
					    
					    arra[i] = imgurl;
						name[i] = movietitle;
					  //  String torrenturl = obj.getString(TAG_TORRENT);
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
						//contact.put(TAG_GENRE, genre);
						contact.put(TAG_IMG, imgurl);
						//contact.put(TAG_TORRENT, torrenturl);
						
						//contact.put(TAG_PHONE_MOBILE, mobile);

						// adding contact to contact list
						showList.add(contact);
						
					//	System.out.println("first " + arra[0]+name[0]);
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
					MainActivityUPcoming.this, showList,
					R.layout.list_item, new String[] { TAG_MOVIE, TAG_RATING,
							 TAG_IMG}, new int[] { R.id.movie,
							R.id.quality , R.id.img});

			setListAdapter(adapter);
		}

	}

}