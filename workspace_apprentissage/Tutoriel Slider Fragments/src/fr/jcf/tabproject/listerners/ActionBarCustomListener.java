package fr.jcf.tabproject.listerners;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * Permet de définir les évènements lorsqu'un élément de la barre d'action change
 * @author jfélicité
 *
 * @param <T>
 */
public class ActionBarCustomListener<T extends Fragment> implements android.app.ActionBar.TabListener {
	private final int mTag;
	private ViewPager mPager;

	/**
	 * Constructeur utilisé à chaque création de Tab
	 * 
	 * @param activity   L'Activity hôte utilisé pour instancier un fragment
	 * @param tag        Identifiant tag du fragment
	 * @param clz        La classe Fragment utilisés pour instancier un fragment
	 */
	public ActionBarCustomListener(Activity activity, int tag, ViewPager mPager, Class<T> clz) {
		this.mTag = tag;
		this.mPager = mPager;
	}

	/**
	 * Méthode utilisée lorsqu'un Tab est sélectionné un fragment est alors affiché
	 */
	public void onTabSelected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		mPager.setCurrentItem(mTag);
	}

	/**
	 * Méthode utilisée lorsqu'un Tab est déselectionné Le fragment n'est
	 * plus affiché
	 */
	public void onTabUnselected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		// On ne fait rien	
	}

	public void onTabReselected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		// Un utilisateur a déjà sélectionné le tab en cours. Actuellement
		// il ne se passe rien.
	}

}
