package fr.jcf.tabproject.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import fr.jcf.tabproject.R;
import fr.jcf.tabproject.adapters.ViewPagerAdapter;
import fr.jcf.tabproject.fragments.AlbumsFragment;
import fr.jcf.tabproject.fragments.ArtistsFragment;
import fr.jcf.tabproject.fragments.SongsFragment;
import fr.jcf.tabproject.listerners.ActionBarCustomListener;
import fr.jcf.tabproject.listerners.OnPageChangeCustomListener;

/**
 * Classe permettant de mettre en place un Pager Swipe Fragments Inspiré des
 * tutoriels : - http://www.tutos-android.com/fragment-slider-page-lautre -
 * http://android-developers.blogspot.fr/2011/08/horizontal-view-swiping-with-
 * viewpager.html
 * 
 * @author jfélicité
 * 
 */
public class ViewPagerSwipingActivity extends FragmentActivity {
	private ViewPagerAdapter mAdapter;
	private ViewPager mPager;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		construireBarAction();

	}

	/**
	 * Cette méthode construit la bar d'action pour l'activity principale
	 */
	private void construireBarAction() {
		// Création de la listes des fragments que l'on souhaite afficher dans
		// les onglets
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments
				.add(Fragment.instantiate(this, AlbumsFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				ArtistsFragment.class.getName()));
		fragments
				.add(Fragment.instantiate(this, SongsFragment.class.getName()));

		// Récupération de la barre d'action
		actionBar = getActionBar();

		// Instanciation de l'adapter du ViewPager
		mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

		// On récupère le ViewPager
		mPager = (ViewPager) findViewById(R.id.pager);

		// On associe l'adapter au pager
		mPager.setAdapter(mAdapter);

		// Lorsqu'un élément du pager change des évènements sont levé
		mPager.setOnPageChangeListener(new OnPageChangeCustomListener(actionBar));

		// La bar de navigation est en mode Onglet ou Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tabAlbums = actionBar.newTab();
		ActionBarCustomListener<AlbumsFragment> tabListenerAlbum = new ActionBarCustomListener<AlbumsFragment>(
				this, 0, mPager, AlbumsFragment.class);
		tabAlbums.setText(R.string.albums_activity);
		tabAlbums.setTabListener(tabListenerAlbum);
		actionBar.addTab(tabAlbums);
		
		// On crée chaque Onglet du menu et on l'ajoute à la barre d'action
		Tab tabArtits = actionBar.newTab();
		tabArtits.setText(R.string.artists_activity);
		ActionBarCustomListener<ArtistsFragment> tabListenerArtists = new ActionBarCustomListener<ArtistsFragment>(
				this, 1, mPager, ArtistsFragment.class);
		tabArtits.setTabListener(tabListenerArtists);
		actionBar.addTab(tabArtits);

		Tab tabSongs = actionBar.newTab();
		ActionBarCustomListener<SongsFragment> tabListenerSong = new ActionBarCustomListener<SongsFragment>(
				this, 2, mPager, SongsFragment.class);
		tabSongs.setText(R.string.songs_activity);
		tabSongs.setTabListener(tabListenerSong);
		actionBar.addTab(tabSongs);
	}
	
	/**
	 *	Cette méthode permet de charger un menu d'option dans la barre d'action
	 *  Le fichier menu_principal.xml est dans le dossier res/menu
	 *  Il contient les infos suivantes :
	 *  
	 *  <?xml version="1.0" encoding="utf-8"?>
	 *	<menu xmlns:android="http://schemas.android.com/apk/res/android">
	 *	    <item android:id="@+id/menu_creer_contact"
	 *	          android:title="@string/menu_creer_contact" 
	 *	          android:showAsAction="always"
	 * 	          android:icon="@drawable/ic_menu_add_person">
	 *	    </item>
	 *	</menu> 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal, menu);
		return true;
	}

	/**
	 * Cette méthode est levée lorqu'une option de menu est sélectionnée
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_creer_contact:
				// On affiche l'activity qui permet de créer un contact
				// lorsque l'option "créer un contact" est sélectionnée
				Intent intent = new Intent(Intent.ACTION_INSERT,
						Contacts.CONTENT_URI);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
