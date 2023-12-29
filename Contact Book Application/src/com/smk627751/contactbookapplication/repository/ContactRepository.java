package com.smk627751.contactbookapplication.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactRepository {
	private static ContactRepository contactRepo;
	Connection con;
	private ContactRepository()
	{
		try {
			Class.forName("org.postgresql.Driver");
			String dbURL = "jdbc:postgresql://localhost:5432/smk627751";
			String user = "postgres";
			String pass = "627751";
			this.con = DriverManager.getConnection(dbURL, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static ContactRepository getInstance()
	{
		if(contactRepo == null)
		{
			contactRepo = new ContactRepository();
		}
		return contactRepo;
	}
	
	public ResultSet executeQuery(String query)
	{
		ResultSet rs = null;
		try {
			Statement s = con.createStatement();
			rs = s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean insertQuery(String query)
	{
		int rowsAffected = 0;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			rowsAffected = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected > 0 ? true : false;
	}
}
