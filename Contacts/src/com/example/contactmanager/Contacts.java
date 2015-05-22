package com.example.contactmanager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.os.Environment;

//Class for file operations and contact operations
//Author: Gaurav Gandhi

public class Contacts {
               
	/**
	 * @param args
	 */
	String firstName,lastName,phoneNumber,emailAddress;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	static String FILENAME="contactdb.txt";
	
	public Contacts(String firstName,String lastName,String phoneNumber,String emailAddress)
	{
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber=phoneNumber;
		this.emailAddress=emailAddress;
	}
	public Contacts()
	{
		
	}
	// Method saves contact
	
	public void saveContact()
	{
		BufferedWriter writer=null; 
		File sdCard = Environment.getExternalStorageDirectory();
		 File file=new File(sdCard, FILENAME);
		 try {
			 
			 if (!file.exists()) {
	                file.createNewFile();
	                System.out.println("File Created");
	            }
			 System.out.println(file.getAbsolutePath());
			 writer=new BufferedWriter(new FileWriter(file.getAbsoluteFile(),true));
			 writer.write(this.getWritableString());
			 writer.newLine();
			 writer.close();
			 sortContacts();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    //This will return string to be written to the file based on  the Contact fields
    public String getWritableString() {
        String temp = "";
        temp += this.getFirstName() + "\t";
        temp += this.getLastName() +"\t";
        temp += this.getPhoneNumber() +"\t";
        temp += this.getEmailAddress()+"";
        return temp;
    }
    
    //This will return Contact class object from contact string
    public static Contacts getContactObject(String contactInString) {
        Contacts c = new Contacts();
        String[] stringList = contactInString.split("\t");
        try{
        c.firstName = stringList[0];
        c.lastName = stringList[1];
        c.phoneNumber = stringList[2];
        c.emailAddress = stringList[3];
        }catch(Exception e){
        	
        	System.out.println("@@@@EXCEPTION"+stringList.length);
        }
        return c;
    }

    // This function will delete a contact from file
    public static boolean deleteContact(int index) throws IOException {
        
    	BufferedWriter writer=null; 
		File sdCard = Environment.getExternalStorageDirectory();
		File file=new File(sdCard, FILENAME);
		
    	try {
            ArrayList<Contacts> contactBuffer = new ArrayList<Contacts>();
            contactBuffer = getContacts();
            writer=new BufferedWriter(new FileWriter(file.getAbsoluteFile(),false));
       	 	
            int i=0;
            for (Contacts contact : contactBuffer) {
                if (i<index || i>index) {
               	 	writer.write(contact.getWritableString());
               	 	
               	 	writer.newLine();
               	 	i++;
                    continue;
                } 
                else if(i==index) {
                	i++;
                	continue;
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method will modify the contact
    public boolean modifyContact(int index) throws IOException {

    		deleteContact(index);
    		this.saveContact();
    		return true;
     }

    
    // This function will return contacts in an ArrayList
    public static ArrayList<Contacts> getContacts() { 
        ArrayList<Contacts> contactBuffer = new ArrayList<Contacts>();
        File sdCard = Environment.getExternalStorageDirectory();
		File file=new File(sdCard, FILENAME);
		BufferedReader reader=null;
		
        try {
                reader= new BufferedReader(new FileReader(file.getAbsoluteFile()));
                String contactInString;
                contactInString = reader.readLine();
                while (contactInString != null) {
                    contactBuffer.add(getContactObject(contactInString));
                    contactInString = reader.readLine();
                }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactBuffer;
    }

    public static void sortContacts() {
        ArrayList<Contacts> contactBuffer = new ArrayList<Contacts>();
        contactBuffer = getContacts();

        ArrayList<String> stringList = new ArrayList<String>();
        for (Contacts contact : contactBuffer) {
            stringList.add(contact.getWritableString());
        }
        
        Collections.sort(stringList,String.CASE_INSENSITIVE_ORDER);

        BufferedWriter writer=null; 
		File sdCard = Environment.getExternalStorageDirectory();
		File file=new File(sdCard, FILENAME);
		try {
			writer=new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			for (String s : stringList) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        }
		catch (IOException e) {
            e.printStackTrace();
        }
    }

}