package fr.jcf.tabproject.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Classe utilitaires pour le projet TabProject
 * @author jfélicité
 *
 */
public class UtilTabProject {
	
	/**
	 * Permet de charge un frament à partir d'un layout passé en paramètre
	 * @param inflater
	 * @param container
	 * @param layout Le layout correspondant au fragment à charger
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
