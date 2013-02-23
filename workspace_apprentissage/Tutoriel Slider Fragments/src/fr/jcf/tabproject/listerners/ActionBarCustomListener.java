package fr.jcf.tabproject.listerners;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * Permet de d�finir les �v�nements lorsqu'un �l�ment de la barre d'action change
 * @author jf�licit�
 *
 * @param <T>
 */
public class ActionBarCustomListener<T extends Fragment> implements android.app.ActionBar.TabListener {
	private final int mTag;
	private ViewPager mPager;

	/**
	 * Constructeur utilis� � chaque cr�ation de Tab
	 * 
	 * @param activity   L'Activity h�te utilis� pour instancier un fragment
	 * @param tag        Identifiant tag du fragment
	 * @param clz        La classe Fragment utilis�s pour instancier un fragment
	 */
	public ActionBarCustomListener(Activity activity, int tag, ViewPager mPager, Class<T> clz) {
		this.mTag = tag;
		this.mPager = mPager;
	}

	/**
	 * M�thode utilis�e lorsqu'un Tab est s�lectionn� un fragment est alors affich�
	 */
	public void onTabSelected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		mPager.setCurrentItem(mTag);
	}

	/**
	 * M�thode utilis�e lorsqu'un Tab est d�selectionn� Le fragment n'est
	 * plus affich�
	 */
	public void onTabUnselected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		// On ne fait rien	
	}

	public void onTabReselected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		// Un utilisateur a d�j� s�lectionn� le tab en cours. Actuellement
		// il ne se passe rien.
	}

}
