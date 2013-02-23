package fr.jcf.tabproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.jcf.tabproject.R;
import fr.jcf.tabproject.utils.UtilTabProject;

public class ArtistsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return UtilTabProject.chargerLayout(inflater, container, R.layout.artists_fragment);
	}
}