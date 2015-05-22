package com.example.contactmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

// Launcher Activity, Contains contact list fragment
// Author: Gaurav Gandhi

public class ContactList extends ActionBarActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// Opens edit activity
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra("mode","new");
		Vibrator v = (Vibrator)this.getSystemService(Service.VIBRATOR_SERVICE);
		v.vibrate(15);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
}