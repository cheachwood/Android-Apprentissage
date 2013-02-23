package fr.jcf.tabproject.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.jcf.tabproject.R;
import fr.jcf.tabproject.utils.UtilTabProject;

public class AlbumsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = UtilTabProject.chargerLayout(inflater, container,
				R.layout.albums_fragment);

		/**
		 * 1. Pour utiliser les Providers il faut d'abord déclarer les arguments
		 * d'un ContentProvider - la projection : qui liste les colonnes à
		 * récupérer - la clause : la clause de recherche qui restreint le
		 * résultat - les arguments de sélection : qui permet de définir des
		 * critères de recherche avec valeurs
		 */

		/*
		 * Déclaration des colonnes du provider à utiliser
		 */
		String[] mProjection = { UserDictionary.Words._ID, // Constante pour la
															// colonne _ID
				UserDictionary.Words.WORD, // Constante pour la colonne Word
				UserDictionary.Words.LOCALE // Constante pour la colonne Locale
		};

		/*
		 * Défini la chaine de caractère qui doit contenie la clause de
		 * recherche
		 */
		String mSelectionClause = null;

		/*
		 * Initialisation d'un tableau contenant des arguments de recherche
		 */
		String[] mSelectionArgs = null;

		/*
		 * Ajout d'un agrégateur de tri
		 */
		String mSortOrder = "word ASC";

		/**
		 * 2. Il faut récupérer un objet ContentResolver pour accéder à un
		 * Provider
		 */
		ContentResolver contentResolver = getActivity().getContentResolver();

		if (contentResolver != null) {
			/**
			 * 3. On instancie un curseur à partir d'un Provider en faisant appel à la méthode query qui contient 5 arguments
			 * 
			 * query() argument 		| SELECT keyword/parameter  			Notes 
			 * Uri 						| FROM  table_name 						Uri maps to the table in the provider named
			 * table_name. projection 	| col,col,col,... 						projection is an array of columns that should be included for each row retrieved.
			 * selection 				| WHERE col = value 					selection specifies the criteria for selecting rows. 
			 * selectionArgs 			| (No exact equivalent. Selection arguments replace ? placeholders in the selection * clause.)
			 * sortOrder 				| ORDER BY col,col,... 					sortOrder specifies the order in which rows appear in the returned Cursor.
			 */
			Cursor mCursor = contentResolver.query(
					UserDictionary.Words.CONTENT_URI, 	// Définit la table du Provider à interroger
					mProjection, 						// Les colonnes à retourner
					mSelectionClause, 					// Les critères de sélection
					mSelectionArgs, 					// La liste des arguments à passer
					mSortOrder); 						// Les paramètres de tri
			List<String> words = new ArrayList<String>();
			int index = mCursor.getColumnIndex(UserDictionary.Words.WORD);
			while (mCursor.moveToNext()) {
				words.add(mCursor.getString(index));
			}

			// On charge la liste des données dans la liste courante du layout "albums_fragment"
			ListView listView = (ListView) view.findViewById(R.id.mylist);
			
			// On crée un Adapter qui affichera les données dans le layout "list_black_theme"
			// Sous la forme d'un TextView "list_content" qui est un composant qui affiche du texte (possibilité d'édition)
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), R.layout.list_black_theme, R.id.list_content, words);

			// Assignation d'un adpater à la ListView
			listView.setAdapter(adapter);
		}
        return view;
	}
	
	

}
