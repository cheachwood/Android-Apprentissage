package fr.jcf.tabproject.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Classe utilitaires pour le projet TabProject
 * @author jf�licit�
 *
 */
public class UtilTabProject {
	
	/**
	 * Permet de charge un frament � partir d'un layout pass� en param�tre
	 * @param inflater
	 * @param container
	 * @param layout Le layout correspondant au fragment � charger
	 * @return Une vue
	 */
	public static View chargerLayout(LayoutInflater inflater, ViewGroup container, int layout) {
		if (container == null) {
			return null;
		}

		LinearLayout linearLayout = (LinearLayout) inflater.inflate (
				layout, container, false);

		return linearLayout;
	}
}
