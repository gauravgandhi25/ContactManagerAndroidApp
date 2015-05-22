package com.example.contactmanager;

//Activity for editing a contact
//Author: Gaurav Gandhi

import java.io.IOException;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends ActionBarActivity {

	Button button;
	OnClickListener ol;
	int index;
	String mode;
	Contacts c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		EditText fnameText = (EditText) findViewById(R.id.firstName_text);
		button = (Button) findViewById(R.id.saveButton);
		button.setEnabled(false);

		Intent intent = this.getIntent();
		mode = intent.getExtras().getString("mode");

		if (mode.equalsIgnoreCase("new")) {
			// New Mode
		} else {
			// Modify Mode
			index = (int) intent.getExtras().getLong("selected_row");
			ArrayList<Contacts> contactBuffer = new ArrayList<Contacts>(
					Contacts.getContacts());
			c = new Contacts();
			c = contactBuffer.get(index);
			button.setEnabled(true);
			fnameText = (EditText) findViewById(R.id.firstName_text);
			EditText lnameText = (EditText) findViewById(R.id.lastName_text);
			EditText phoneNumberText = (EditText) findViewById(R.id.phoneNumber_text);
			EditText emailIdText = (EditText) findViewById(R.id.emailid_text);

			fnameText.setText(c.getFirstName());
			String temp;
			temp = c.getLastName();
			if (!temp.equalsIgnoreCase("(empty)"))
				lnameText.setText(temp);
			temp = c.getPhoneNumber();
			if (!temp.equalsIgnoreCase("(empty)"))
				phoneNumberText.setText(temp);
			temp = c.getEmailAddress();
			if (!temp.equalsIgnoreCase("(empty)"))
				emailIdText.setText(temp);
		}

		fnameText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() == 0) {
					button.setEnabled(false);
				} else {
					button.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		try {
			saveChanges(getCurrentFocus());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onOptionsItemSelected(item);
	}
	//Called when save button is pressed
	public void saveChanges(View view) throws IOException {
		int id=view.getId();
		if(id!=R.id.saveButton)
			return;
		if (mode.equals("new"))
			saveContact(view);
		else
			modifyContact(view);

		Vibrator v = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
		v.vibrate(15);
	}
	//Save New Contact
	public void saveContact(View view) throws IOException {
		EditText fnameText = (EditText) findViewById(R.id.firstName_text);
		EditText lnameText = (EditText) findViewById(R.id.lastName_text);
		EditText phoneNumberText = (EditText) findViewById(R.id.phoneNumber_text);
		EditText emailIdText = (EditText) findViewById(R.id.emailid_text);

		String fname = fnameText.getText().toString().trim();
		String lname = lnameText.getText().toString().trim();
		String phoneNo = phoneNumberText.getText().toString().trim();
		String emailId = emailIdText.getText().toString().trim();

		if (lname.length() == 0)
			lname = "(empty)";

		if (emailId.length() == 0)
			emailId = "(empty)";

		if (phoneNo.length() == 0)
			phoneNo = "(empty)";

		Contacts c = new Contacts(fname, lname, phoneNo, emailId);
		c.saveContact();
		finish();
		Toast.makeText(getApplicationContext(), "Contact Saved",
				Toast.LENGTH_LONG).show();

	}

	//Modify Existing Contacts
	public void modifyContact(View view) throws IOException {
		EditText fnameText = (EditText) findViewById(R.id.firstName_text);
		EditText lnameText = (EditText) findViewById(R.id.lastName_text);
		EditText phoneNumberText = (EditText) findViewById(R.id.phoneNumber_text);
		EditText emailIdText = (EditText) findViewById(R.id.emailid_text);

		String fname = fnameText.getText().toString().trim();
		String lname = lnameText.getText().toString().trim();
		String phoneNo = phoneNumberText.getText().toString().trim();
		String emailId = emailIdText.getText().toString().trim();

		if (lname.length() == 0)
			lname = "(empty)";

		if (emailId.length() == 0)
			emailId = "(empty)";

		if (phoneNo.length() == 0)
			phoneNo = "(empty)";

		Contacts c = new Contacts(fname, lname, phoneNo, emailId);
		c.modifyContact(index);
		finish();
		Toast.makeText(getApplicationContext(), "Contact Modified",
				Toast.LENGTH_LONG).show();
	}
}
