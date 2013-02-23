package fr.jcf.fragments.montutoriel.fragments;

import fr.jcf.fragments.montutoriel.data.ShakeSpeare;
import fr.jcf.fragments.montutoriel.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentBas extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bas,container,false);
	}
	
	public void afficherDetail(int position) {
		TextView detailRoiTextView = (TextView) this.getActivity().findViewById(R.id.detailRoi);
		detailRoiTextView.setText(ShakeSpeare.DIALOGUE[position]);
	}
}
