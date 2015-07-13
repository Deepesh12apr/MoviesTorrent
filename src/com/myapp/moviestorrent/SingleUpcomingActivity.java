package com.myapp.moviestorrent;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.myapp.moviestorrent.R;

public class SingleUpcomingActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_MOVIE = "MovieTitle";
	private static final String TAG_RATING = "MovieRating";
	//private static final String TAG_GENRE = "Genre";
	private static final String TAG_IMG = "MovieCover";
	private static final String  TAG_TORRENT = "TorrentUrl";
	public String torrenturl;
	WebView wb;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_upcoming);
        
       wb = (WebView)findViewById(R.id.webView1);
       
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String movie = in.getStringExtra(TAG_MOVIE);
        String rating = in.getStringExtra(TAG_RATING);
        //String genre = in.getStringExtra(TAG_GENRE);
        String imgurl = in.getStringExtra(TAG_IMG);
       
        //String imgurl1 = in.getStringExtra(MainActivity.imgurlin);
        torrenturl = in.getStringExtra(TAG_TORRENT);
        
        // Displaying all values on the screen
       TextView lblName = (TextView) findViewById(R.id.movie_label);
      //  TextView lblEmail = (TextView) findViewById(R.id.quality_label);
        //TextView lblMobile = (TextView) findViewById(R.id.rating_label);
        //TextView lblimg = (TextView) findViewById(R.id.img_label);
        
        lblName.setText(movie);
       // lblEmail.setText(rating);
        //lblMobile.setText(genre);
       // lblimg.setText(imgurl);
        
       // wb.loadUrl(MainActivity.CoverImage);
       wb.loadUrl(imgurl);
       
       
      
       
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
	
}
