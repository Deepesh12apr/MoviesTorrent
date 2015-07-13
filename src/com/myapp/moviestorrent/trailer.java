package com.myapp.moviestorrent;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class trailer extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {

	static private final String DEVELOPER_KEY = "AIzaSyB24mzTa-0WEwHbMEvyBUPwk2lHR_I3KNU";
	private static final String TAG_ID = "MovieID";
	public String movieid;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trailer);
	
        
     // get action bar  
        ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        
 Intent i = getIntent();
        
        // Get JSON values from previous intent
        
       movieid = i.getStringExtra(TAG_ID);
        
        
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
		arg1.loadVideo(movieid);
		}

}
