package fr.jcf.tabproject.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Permet l'affichage du fragments s�lectionn� par un ViewPager
 * @author jf�licit�
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	// Liste des fragments � afficher
	private List<Fragment> fragments;
	
	/**
	 * On r�cup�re la liste des fragments via le constructeur
	 * @param fm
	 * @param fragments : la liste des fragments
	 */
	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	/**
	 * Permet de r�cup�rer le fragments � afficher en fonction de la position
	 * du ViewPager
	 */
	@Override
	public Fragment getItem(int position) {
		return  fragments.get(position);
	}

}
