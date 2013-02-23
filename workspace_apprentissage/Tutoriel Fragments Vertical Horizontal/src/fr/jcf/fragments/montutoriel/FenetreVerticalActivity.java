package fr.jcf.fragments.montutoriel;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import fr.jcf.fragments.montutoriel.fragments.FragmentBas;

public class FenetreVerticalActivity extends FragmentActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}

		setContentView(R.layout.fragment_haut);
		Intent launchingIntent = getIntent();
		int position = launchingIntent.getIntExtra("position", 0);

		FragmentBas fragmentBas = null;
	
		 if (getSupportFragmentManager().findFragmentById(R.id.fragment_bas) != null) {
			 fragmentBas = (FragmentBas) this.getSupportFragmentManager()
						.findFragmentById(R.id.fragment_bas);
			 fragmentBas.afficherDetail(position);
		 }
	}

}
