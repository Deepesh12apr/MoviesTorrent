package com.myapp.moviestorrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.myapp.moviestorrent.GridMain;


public class ImageAdapter  extends BaseAdapter{

	
	GridMain gm = new GridMain();
	
	private Context context;
	public ArrayList<HashMap<String, String>> showList;
    public	String [] name;
	public String [] array;
	
	
	public ImageAdapter(Context c, ArrayList<HashMap<String, String>> al,String[] n,String[]a)
	{
		System.out.println("In xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx IMAGEADAPTER" );
		context=c;
		this.showList=al;
		this.name=n;
		this.array = a;
	  showList=al;   
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("In nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn IMAGEADAPTER" );
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View gridView;
	 
			if (convertView == null) {
	 
				gridView = new View(context);
	 
				// get layout from mobile.xml
				//gridView = inflater.inflate(R.layout.grid_single2, null);
	 
				

			} else {
				gridView = (View) convertView;
			}
			 String url = "http://yify-torrents.com/api/list.json";
			 JSONArray MovieList = null;
			  String TAG_MOVIE= "MovieTitle";
	          String TAG_RATING = "MovieRating";
			 String TAG_GENRE = "Genre";
			 String TAG_IMG = "CoverImage";
			 String TAG_TORRENT = "TorrentUrl";
			 String TAG_ID = "MovieID";
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					
					// Getting JSON Array node
					MovieList = jsonObj.getJSONArray("MovieList");
                     array = new String[MovieList.length()];
                     name = new String[MovieList.length()];
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
					    array[i] = imgurl;
						name[i] = movietitle;
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
			
			Iterator itr = showList.iterator();
			while (itr.hasNext()) {
				itr.next();
				TextView textView = (TextView) gridView
						.findViewById(R.id.grid_title);

				textView.setText(name[position]);

			}
			WebView ww = (WebView) gridView.findViewById(R.id.webView1);
			
		//WebView	wv = (WebView) gridView.findViewById(R.id.webView1);

			for (int i = 0; i < array.length; i++) {
				// System.out.println("first img url "+array[i]);
				// Picasso.with(GridMain.this).load(array[i]).into(iv);

				if (i == position) {
					int a = array.length;
					ww.loadUrl(array[position]);

					System.out.println("first img url " + array[i]);
					System.out.println("ARRAYYYYYYYYYYYYYYYYYYYYYYYYY"
							+ a);
				}
			}
			
			return gridView;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
