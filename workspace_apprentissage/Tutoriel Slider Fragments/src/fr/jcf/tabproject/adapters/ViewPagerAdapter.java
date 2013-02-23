package fr.jcf.tabproject.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Permet l'affichage du fragments sélectionné par un ViewPager
 * @author jfélicité
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	// Liste des fragments à afficher
	private List<Fragment> fragments;
	
	/**
	 * On récupère la liste des fragments via le constructeur
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
	 * Permet de récupérer le fragments à afficher en fonction de la position
	 * du ViewPager
	 */
	@Override
	public Fragment getItem(int position) {
		return  fragments.get(position);
	}

}
