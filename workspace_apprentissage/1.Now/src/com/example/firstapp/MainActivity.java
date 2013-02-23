package com.example.firstapp;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
	private Button bouton;
	private TextView textView;
	private EditText editText;
	private CheckBox checkBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bouton = (Button) this.findViewById(R.id.bouton1);
		bouton.setOnClickListener(this);
		
		textView = (TextView)this.findViewById(R.id.textView1);
		editText = (EditText)this.findViewById(R.id.editText1);
		editText.setText("Licensed under the Apache License, Version 2.0 " +
				"(the \"License\"); you may not use this file " +
				"except in compliance with the License. You may " +
				"obtain a copy of the License at " +
				"http://www.apache.org/licenses/LICENSE-2.0");
		
		checkBox = (CheckBox)this.findViewById(R.id.checkBox1);
		checkBox.setOnCheckedChangeListener(this);
		
		updateTime();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Gestion du clic sur le bouton
	// Implémentation de l'interface View.OnClickListener
	@Override
	public void onClick(View v) {
		updateTime();
	}

	private void updateTime() {
		bouton.setText(new Date().toString());
	}
	
	// Cette méthode est directement lié au bouton 2
	// android:onClick="updateTime" dans le fichier de layout activity_main.xml
	public void updateTime(View view) {
		textView.setText(new Date().toString());
	}

	// Gestion du changement d'état d'une case à cocher
	// Implémentation de l'interface CompoundButton.OnCheckedChangeListener
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			checkBox.setText("Bouton coché!");
		}else{
			checkBox.setText("Bouton décoché!");
		}
		
	}

}
