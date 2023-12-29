package com.smk627751.contactbookapplication.viewModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.smk627751.contactbookapplication.dto.Contact;
import com.smk627751.contactbookapplication.repository.ContactRepository;
import com.smk627751.contactbookapplication.view.ContactView;

public class ContactViewModel {
	private ContactView view;
	private ContactRepository contactRepo;
	private List<Contact> contacts;
	public ContactViewModel(ContactView contactView) {
		this.view = contactView;
		this.contactRepo = ContactRepository.getInstance();
	}
	public void listContacts(String search)
	{
		StringBuilder str = new StringBuilder();
		this.contacts = searchContact(search);
		if(contacts.isEmpty())
		{
			view.onPrint("Not found\n");
			return;
		}
		Collections.sort(contacts,(a,b) -> (b.getName().length() - a.getName().length()));
		int maxLen = contacts.get(0).getName().length();
		char[] hLine = new char[maxLen+26];
		Arrays.fill(hLine, '-');
		hLine[0] = '+';
		hLine[8] = '+';
		hLine[maxLen+11] = '+';
		hLine[hLine.length - 1] = '+';
		String horizontalLine = new String(hLine)+"\n";
		Collections.sort(contacts,(a,b) -> (a.getName().compareTo(b.getName())));
		int index = 1;
		str.append(horizontalLine);
		int gap = maxLen - 11;
		if(gap < 0)
		{
			gap = 1;
		}
		char hSpace[] = new char[gap];
		Arrays.fill(hSpace, ' ');
		str.append("| s.no \t| "+new String(hSpace)+"Name"+new String(hSpace)+" |   PhoneNo   |\n");
		str.append(horizontalLine);
		for(Contact contact : contacts)
		{
			char space[] = new char[maxLen - contact.getName().length() + 1];
			Arrays.fill(space, ' ');
			str.append("| "+index++ +"\t| "+contact.getName()+new String(space)+"| "+contact.getPhoneNo()+" |\n");
		}
		str.append(horizontalLine);
		view.onPrint(str.toString());
	}
	public List<Contact> searchContact(String search)
	{
		List<Contact> contacts = new ArrayList<>();
		String query = "SELECT * FROM contacts WHERE LOWER(name) LIKE '%"+search.toLowerCase()+"%' OR phoneNumber LIKE '%"+search+"%'";
		ResultSet rs = contactRepo.executeQuery(query);
		try {
			while(rs.next())
			{
				contacts.add(new Contact(rs.getString("name"),rs.getString("phonenumber")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}
	public void addContact(String name, String phonenumber)
	{
		phonenumber = phonenumber.substring(0,6) +" "+ phonenumber.substring(6);
		String query = "INSERT INTO contacts (name, phonenumber) VALUES('"+name+"','"+phonenumber+"')";
		boolean isInserted = contactRepo.insertQuery(query);
		if(isInserted)
		{
			view.onPrint("Inserted Successfully");
		}
		else view.onPrint("Failed to insert");
	}
	public void updateContact(String name, String newName, String newNumber)
	{
		newNumber = newNumber.substring(0,6) +" "+ newNumber.substring(6);
		String query = "UPDATE contacts SET name = '"+newName+"' , phonenumber = '"+newNumber+"' WHERE name = '"+name+"'";
		boolean isUpdated = contactRepo.insertQuery(query);
		if(isUpdated)
		{
			view.onPrint("Updated Successfully");
		}
		else view.onPrint("Failed to update");
	}
	public void removeContact(int index)
	{
		String name = contacts.get(index - 1).getName();
		String query = "DELETE FROM contacts WHERE name = '"+name+"'";
		boolean isDeleted = contactRepo.insertQuery(query);
		if(isDeleted)
		{
			view.onPrint("Deleted Successfully");
		}
		else view.onPrint("Failed to delete");
	}
}
