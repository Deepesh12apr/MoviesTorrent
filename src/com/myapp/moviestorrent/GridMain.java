package com.myapp.moviestorrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GridMain extends Activity {

	private static String url = "http://yify-torrents.com/api/Upcoming.json";
	private ProgressDialog pDialog;
	private static final String TAG_MOVIE = "MovieTitle";
	//private static final String TAG_RATING = "MovieRating";
	private static final String TAG_IMG = "MovieCover";
	public HashMap<String, String> contact;
	JSONArray MovieList = null;
	String[] arra;
	String[] name;
	ArrayList<HashMap<String, String>> showList;

	GridView gv;
	TextView tv,tt;
	WebView wv;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		gv = (GridView) findViewById(R.id.gridView1);
		
		showList = new ArrayList<HashMap<String, String>>();
		// ImageView iv = (ImageView) findViewById(R.id.img);
         
	//	gv.setAdapter(new ImageAdapter(GridMain.this, showList, name, array));
		
		// Calling async task to get json
		new GetMovies().execute();
             
		//gv.setAdapter(new ImageAdapter(GridMain.this, showList, name, array));
		
		/*   gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(GridMain.this, "in touch", Toast.LENGTH_LONG).show();
				}
			});*/
		/*
		 * new Thread() { public void run() { try { for (HashMap<String,String>
		 * contact : showList) { String value = contact.get(0).toString();
		 * System.out.println(value); } } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } //wv.loadUrl(x); }
		 * }.start();
		 */

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
	            	Intent i = new Intent(this,GridMain.class);
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
	
	
	
	
	
	
	
	
	
	
	
	

	 class GetMovies extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(GridMain.this);
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
						//String rating = obj.getString(TAG_RATING);
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
						//contact.put(TAG_RATING, rating);
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

			System.out.println("first img url " + arra[0]);

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

			/*
			 * ListAdapter adapt = new SimpleAdapter( GridMain.this, showList,
			 * R.layout.grid_single, new String[] { TAG_MOVIE, TAG_RATING,
			 * TAG_IMG}, new int[] { R.id.grid_title, R.id.grid_img});
			 */
		//	gv.setAdapter(new ImageAdapter(GridMain.this, showList, name, array));
			
			
			// ImageView iv = (ImageView) findViewById(R.id.img);

			/*
			 * wv = (WebView) findViewById(R.id.webView1); for(int i
			 * =0;i<MovieList.length();i++) {
			 * System.out.println("first img url "+array[i]);
			 * //Picasso.with(GridMain.this).load(array[i]).into(iv);
			 * wv.loadUrl(array[i]); }
			 */
			BaseAdapter ia = new BaseAdapter() {

				private Context context;

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					// TODO Auto-generated method stub
					LayoutInflater inflater = (LayoutInflater) GridMain.this
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

					View gridView;

					if (convertView == null) {

						gridView = new View(GridMain.this);

						// get layout from mobile.xml
						gridView = inflater.inflate(R.layout.grid_single, null);

						// set value into TextView

						// set image based on selected text
						// ImageView imageView = (ImageView) gridView
						// .findViewById(R.id.grid_img);

					} else {
						gridView = (View) convertView;
					}

					Iterator itr = showList.iterator();
					while (itr.hasNext()) {
						itr.next();
						TextView textView = (TextView) gridView
								.findViewById(R.id.grid_title);

						textView.setText(name[position]);

					}
					WebView ww = (WebView) gridView.findViewById(R.id.webView1);
					wv = (WebView) findViewById(R.id.webView1);

					for (int i = 0; i < arra.length; i++) {
						// System.out.println("first img url "+array[i]);
						// Picasso.with(GridMain.this).load(array[i]).into(iv);

						if (i == position) {
							int a = arra.length;
							ww.loadUrl(arra[position]);

							System.out.println("first img url " + arra[i]);
							System.out.println("ARRAYYYYYYYYYYYYYYYYYYYYYYYYY"
									+ a);
						}
					}
               
					return gridView;

				}

				@Override
				public long getItemId(int position) {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public Object getItem(int position) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					int nn;
					nn = arra.length;
					return nn;
				}
			};
			gv.setAdapter(ia);         
			/*gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(GridMain.this, "in touch", Toast.LENGTH_LONG).show();
				}
			});*/
			
		}

	}
	
	

}
