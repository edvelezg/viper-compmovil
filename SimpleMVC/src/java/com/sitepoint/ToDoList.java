package com.sitepoint;

import java.util.*;
import java.sql.*;

public class ToDoList {
	private String jdbcConnectionString;
	private ArrayList list = new ArrayList();
	private boolean staleList = true;
	private Connection conn;

	public ToDoList(String jdbcDriver, String jdbcConnectionString) {
		this.jdbcConnectionString = jdbcConnectionString;

		// Load the driver
		try {
			Class.forName(jdbcDriver).newInstance();
		}
		catch (Exception ex) {
			System.err.println("Error loading database driver " + jdbcDriver +
												 ":\n" + ex.getMessage());
		}
	}

	public List getToDoItems() {
		refreshList();
		return (List)list.clone();
	}

	public int getItemCount() {
		refreshList();
		return list.size();
	}

	public void addItem(String item) {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection(jdbcConnectionString);
			}
                        
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO encuestas (nombre, telefono) VALUES (?, ?)");
			stmt.setString(1, item);
                        stmt.setInt(2, 595516);
			stmt.executeUpdate();
		}
		catch (SQLException ex) {
			System.err.println(
					"Error adding a to-do list item to the database:\n" +
					ex.getMessage());
		}
		staleList = true;
	}

	public void deleteItem(int id) {
//		try {
//			if (conn == null) {
//				conn = DriverManager.getConnection(jdbcConnectionString);
//			}
//			PreparedStatement stmt = conn.prepareStatement(
//					"DELETE FROM todo WHERE todoid=?");
//			stmt.setInt(1, id);
//			stmt.executeUpdate();
//		}
//		catch (SQLException ex) {
//			System.err.println(
//					"Error deleting a to-do list item from the database:\n" +
//					ex.getMessage());
//		}
//		staleList = true;
	}

	private void refreshList() {
		if (staleList) {
			try {
				if (conn == null) {
					conn = DriverManager.getConnection(jdbcConnectionString);
				}
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT nombre, telefono FROM encuestas");

				list = new ArrayList();
				while (rs.next()) {
					list.add(new Datos(rs.getString(1), rs.getInt(2)));
				}
			}
			catch (SQLException ex) {
				System.err.println(
						"Error retrieving to-do list items from the database:\n" +
						ex.getMessage());
			}
			staleList = false;
		}
	}
}
