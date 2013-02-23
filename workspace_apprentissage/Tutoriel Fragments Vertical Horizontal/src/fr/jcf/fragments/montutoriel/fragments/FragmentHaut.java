package fr.jcf.fragments.montutoriel.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import fr.jcf.fragments.montutoriel.data.ShakeSpeare;

public class FragmentHaut extends ListFragment {
	private OnListItemSelectedListerner callback;	
	private int position = 0;
	
	public interface OnListItemSelectedListerner{
		public abstract void onListItemSelected(int position);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListAdapter listDesRois = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ShakeSpeare.TITLES);
		setListAdapter(listDesRois);
		if (savedInstanceState != null) {
			position = savedInstanceState.getInt("position", 0);
			callback.onListItemSelected(position);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			callback = (OnListItemSelectedListerner)activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		this.position = position;
		callback.onListItemSelected(position);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", position);
	}
}
