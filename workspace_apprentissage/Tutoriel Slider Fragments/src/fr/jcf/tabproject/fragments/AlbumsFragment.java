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
		 * 1. Pour utiliser les Providers il faut d'abord d�clarer les arguments
		 * d'un ContentProvider - la projection : qui liste les colonnes �
		 * r�cup�rer - la clause : la clause de recherche qui restreint le
		 * r�sultat - les arguments de s�lection : qui permet de d�finir des
		 * crit�res de recherche avec valeurs
		 */

		/*
		 * D�claration des colonnes du provider � utiliser
		 */
		String[] mProjection = { UserDictionary.Words._ID, // Constante pour la
															// colonne _ID
				UserDictionary.Words.WORD, // Constante pour la colonne Word
				UserDictionary.Words.LOCALE // Constante pour la colonne Locale
		};

		/*
		 * D�fini la chaine de caract�re qui doit contenie la clause de
		 * recherche
		 */
		String mSelectionClause = null;

		/*
		 * Initialisation d'un tableau contenant des arguments de recherche
		 */
		String[] mSelectionArgs = null;

		/*
		 * Ajout d'un agr�gateur de tri
		 */
		String mSortOrder = "word ASC";

		/**
		 * 2. Il faut r�cup�rer un objet ContentResolver pour acc�der � un
		 * Provider
		 */
		ContentResolver contentResolver = getActivity().getContentResolver();

		if (contentResolver != null) {
			/**
			 * 3. On instancie un curseur � partir d'un Provider en faisant appel � la m�thode query qui contient 5 arguments
			 * 
			 * query() argument 		| SELECT keyword/parameter  			Notes 
			 * Uri 						| FROM  table_name 						Uri maps to the table in the provider named
			 * table_name. projection 	| col,col,col,... 						projection is an array of columns that should be included for each row retrieved.
			 * selection 				| WHERE col = value 					selection specifies the criteria for selecting rows. 
			 * selectionArgs 			| (No exact equivalent. Selection arguments replace ? placeholders in the selection * clause.)
			 * sortOrder 				| ORDER BY col,col,... 					sortOrder specifies the order in which rows appear in the returned Cursor.
			 */
			Cursor mCursor = contentResolver.query(
					UserDictionary.Words.CONTENT_URI, 	// D�finit la table du Provider � interroger
					mProjection, 						// Les colonnes � retourner
					mSelectionClause, 					// Les crit�res de s�lection
					mSelectionArgs, 					// La liste des arguments � passer
					mSortOrder); 						// Les param�tres de tri
			List<String> words = new ArrayList<String>();
			int index = mCursor.getColumnIndex(UserDictionary.Words.WORD);
			while (mCursor.moveToNext()) {
				words.add(mCursor.getString(index));
			}

			// On charge la liste des donn�es dans la liste courante du layout "albums_fragment"
			ListView listView = (ListView) view.findViewById(R.id.mylist);
			
			// On cr�e un Adapter qui affichera les donn�es dans le layout "list_black_theme"
			// Sous la forme d'un TextView "list_content" qui est un composant qui affiche du texte (possibilit� d'�dition)
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), R.layout.list_black_theme, R.id.list_content, words);

			// Assignation d'un adpater � la ListView
			listView.setAdapter(adapter);
		}
        return view;
	}
	
	

}
