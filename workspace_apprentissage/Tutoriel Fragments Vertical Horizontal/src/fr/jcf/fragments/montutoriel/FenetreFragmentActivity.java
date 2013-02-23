package fr.jcf.fragments.montutoriel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import fr.jcf.fragments.montutoriel.fragments.FragmentBas;
import fr.jcf.fragments.montutoriel.fragments.FragmentHaut;

public class FenetreFragmentActivity extends FragmentActivity implements
		FragmentHaut.OnListItemSelectedListerner {
	private FragmentBas fragmentBas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fenetre, menu);
		return true;
	}

	@Override
	public void onListItemSelected(int position) {
		fragmentBas = (FragmentBas) this.getSupportFragmentManager()
				.findFragmentById(R.id.fragment_bas);
		
		if (fragmentBas == null || !fragmentBas.isInLayout()) {
			Intent showText = new Intent(getApplicationContext(),
					FenetreVerticalActivity.class);
			showText.putExtra("position", position);
			startActivity(showText);
		} else {
			fragmentBas.afficherDetail(position);
		}

	}

}
