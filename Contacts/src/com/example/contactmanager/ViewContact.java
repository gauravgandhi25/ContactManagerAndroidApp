package com.example.contactmanager;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//Activity for displaying contact details
//author: Gaurav Gandhi

public class ViewContact extends ActionBarActivity {

	int index;
	Contacts c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contact);

		Intent intent = this.getIntent();
		index = (int) intent.getExtras().getLong("selected_row");

		ArrayList<Contacts> contactBuffer = new ArrayList<Contacts>(
				Contacts.getContacts());
		c = new Contacts();
		c = contactBuffer.get(index);
		if (!c.getLastName().equalsIgnoreCase("(empty)"))
			setTitle(c.getFirstName() + " " + c.getLastName());
		else
			setTitle(c.getFirstName());
		ArrayList<String> events = new ArrayList<String>();
		events.add(c.getPhoneNumber());
		events.add(c.getEmailAddress());

		ListView lv = (ListView) findViewById(R.id.listViewContactDetails);
		lv.setAdapter(new ArrayAdapter<String>(this,
				R.layout.view_contact_list_item, R.id.details_textview, events) {
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);

				TextView textview = (TextView) view
						.findViewById(R.id.separator_textview);
				if (position == 0)
					textview.setText("Phone Number");
				else if (position == 1)
					textview.setText("Email id");

				return view;
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (id == 0) {
					// If contact no is pressed new intent to dial a call will
					// be opened
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + c.getPhoneNumber()));
					startActivity(callIntent);
				} else if (id == 1) {
					// If email id is pressed new intent to email will be
					// started
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
							.fromParts("mailto", c.getEmailAddress(), null));
					Intent mailer = Intent.createChooser(emailIntent,
							"Send Email Using..");
					startActivity(mailer);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Vibrator v = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
		v.vibrate(15);

		int id = item.getItemId();
		if (id == R.id.action_delete) {
			try {
				Contacts.deleteContact(index);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
			Toast.makeText(getApplicationContext(), "Contact Deleted",
					Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.action_edit) {
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra("selected_row", (long) index);
			intent.putExtra("mode", "edit");
			finish();
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
