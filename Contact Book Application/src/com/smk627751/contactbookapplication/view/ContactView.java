package com.smk627751.contactbookapplication.view;

import java.util.Scanner;

import com.smk627751.contactbookapplication.viewModel.ContactViewModel;

public class ContactView {
	private ContactViewModel viewModel;
	private final String BOLD = "\033[1m";
	private final String RESET = "\033[0m";
	private final String BG = "\033[45m";
	public ContactView()
	{
		this.viewModel = new ContactViewModel(this);
	}
	
	public void onPrint(String s)
	{
		System.out.println(BOLD + s + RESET);
	}
	
	public void onError(Exception e)
	{
		System.out.println(e.getMessage());
	}
	
	public void init()
	{
		viewModel.listContacts("");
		do
		{
			onPrint("+--------------------+");
			onPrint("|    Contact Book    |");
			onPrint("+---+----------------+");
			onPrint("| 1.| Search contact |\n| 2.| Add contact    |\n| 3.| Update contact |\n| 4.| Delete contact |\n| 0.| Exit           |");
			onPrint("+---+----------------+\n");
			Scanner sc = new Scanner(System.in);
			onPrint(BG + " Enter your choice: " + RESET);
			int choice = sc.nextInt();
			sc.nextLine();
			switch(choice)
			{
				case 1 :{
					onPrint(BG + " Enter the prompt to search:" + RESET);
					String searchParam = sc.nextLine();
					onPrint(" Showing results for '"+searchParam+"'\n");
					viewModel.listContacts(searchParam);
					break;
				}
				case 2 :{
					onPrint(BG + " Enter name: " + RESET);
					String name = sc.nextLine();
					onPrint(BG + " Enter number: " + RESET);
					String number = sc.nextLine();
					viewModel.addContact(name, number);
					break;
				}
				case 3 :{
					onPrint(BG + " which contact you want to update ?:" + RESET);
					String name = sc.nextLine();
					onPrint(BG + " Enter the new name: " + RESET);
					String newName = sc.nextLine();
					onPrint(BG + " Enter the new phonenumber: " + RESET);
					String phoneNo = sc.next();
					viewModel.updateContact(name, newName, phoneNo);
					break;
				}
				case 4 :{
					onPrint(BG + " Which contact you want to delete? :" + RESET);
					int index = sc.nextInt();
					onPrint(BG + "Are you sure want delete ? Y/N" + RESET);
					char yesOrNo = sc.next().charAt(0);
					if(Character.toLowerCase(yesOrNo) == 'y')
					{
						viewModel.removeContact(index);
					}
					break;
				}
				case 0 :{
					onPrint("Exited...");
					sc.close();
					System.exit(0);
					break;
				}
				default:{
					onPrint("Invalid choice");
					break;
				}
			}
		}while(true);
	}
}
