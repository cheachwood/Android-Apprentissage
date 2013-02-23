package fr.jcf.tabproject.listerners;

import android.app.ActionBar;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * Permet de gérer les évènements lorsque le ViewPager change de position
 * @author jfélicité
 *
 */
public class OnPageChangeCustomListener implements OnPageChangeListener {
	private ActionBar actionBar;	
	
	public OnPageChangeCustomListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnPageChangeCustomListener(ActionBar actionBar) {
		super();
		this.actionBar = actionBar;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// On ne fait rien pour l'instant
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// On ne fait rien pour l'instant
	}

	/**
	 * Lorsque la position du pager change, un nouvel onglet est affiché
	 */
	@Override
	public void onPageSelected(int position) {
		this.actionBar.getTabAt(position).select();
	}

}
