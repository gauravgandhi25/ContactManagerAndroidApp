package com.example.contactmanager;

//List fragment showing contact list and providing an option for searching a contact
//Author: Gaurav Gandhi

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
 
public class MyListFragment extends ListFragment{

	Vibrator v; 
	EditText search_name;
	ArrayList<Contacts> contactBuffer,tempcontactBuffer;
	int isNormalList=1,new_elements_index;
	int[] originalid=new int[100];
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v= inflater.inflate(R.layout.mylist_fragment,container,false);
		search_name=(EditText)v.findViewById(R.id.search_name);
		return v;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		contactBuffer=new ArrayList<Contacts>(Contacts.getContacts());
		tempcontactBuffer=new ArrayList<Contacts>(Contacts.getContacts());
		
		updateContactList();
		v = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
		
		/* If text is changed in the search text box, 
		 * then filtered contact list is shown as per the search term  */
		search_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				//If search term is null show normal contact list else show filtered contact list
				if(s.length()==0)
					{contactBuffer=Contacts.getContacts(); isNormalList=1;}
				else
				{
					new_elements_index=0; 
					int i=0;
					isNormalList=0;
				contactBuffer=new ArrayList<Contacts>();
				for (Contacts contact : tempcontactBuffer) {
					
					String temp=contact.getFirstName()+" "+contact.getLastName();
					if(temp.toLowerCase().contains(s.toString().toLowerCase()))
						{
						contactBuffer.add(contact);
						originalid[new_elements_index]=i;	
						new_elements_index++;
						}
					i++;
				}}
				updateContactList();
				
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		contactBuffer=Contacts.getContacts();
		tempcontactBuffer=contactBuffer;
		isNormalList=1;
		updateContactList();
		search_name.setText(""); 
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		int index;
		Intent intent = new Intent(getActivity(), ViewContact.class);
		if(isNormalList==1)
			intent.putExtra("selected_row", id);
		else
		{
			index=(int)id;
			intent.putExtra("selected_row", (long)originalid[index]);
		}
		this.v.vibrate(15);
		startActivity(intent);
	}
	
	public void updateContactList()
	{
		setListAdapter(new ArrayAdapter<Contacts>(getActivity(),R.layout.list_item,R.id.contact_name,contactBuffer){
		
			public View getView(int position, View convertView, ViewGroup parent) {
				
				View view = super.getView(position, convertView, parent);
				TextView text1 = (TextView) view
						.findViewById(R.id.contact_name);
				TextView text2 = (TextView) view
						.findViewById(R.id.contact_phoneNumber);

				String lname=contactBuffer.get(position).getLastName();
				if(lname.equalsIgnoreCase("(empty)"))
						lname="";
				text1.setText(contactBuffer.get(position).getFirstName()+" "+lname);
				text2.setText(contactBuffer.get(position).phoneNumber);
				return view;
			}
		});
	}
}