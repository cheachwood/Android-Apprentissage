package com.example.tutorielgoogle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	static final String STATE_SCORE = "playerScore";
	static final String STATE_LEVEL = "playerLevel";
	int mCurrentLevel;
	int mCurrentScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mCurrentLevel = 2;
		mCurrentScore = 1200;
		
		// Save the user's current game state
		outState.putInt(STATE_SCORE, mCurrentScore);
		outState.putInt(STATE_LEVEL, mCurrentLevel);
	    
		System.out.println("Sauvegarde de mCurrentLevel et mCurrentScore.");
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		System.out.println("Dans onRestoreInstanceState()");		
		// Restore state members from saved instance
	    mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
	    mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		System.out.println("Dans onStart()");
		System.out.println("mCurrentLevel : " +  mCurrentLevel);
		System.out.println("mCurrentScore : " + mCurrentScore);
	}
}
