package com.Jay.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.Jay.entity.Manager;

public class ManagerDao {
	private ConnectionManager connectionManager;
	private Connection connection;
	
	public Manager login(String uname, String upwd){
		connectionManager = new ConnectionManager();
		Manager manager = null;
		try{
			connection = connectionManager.getConnection_jdbc();
			String sql = "SELECT * FROM manager WHERE uname = '"+uname+"' AND upwd = '"+upwd+"'";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				manager = new Manager();
				manager.setUname(uname);
				manager.setUpwd(upwd);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}
		return manager;
		
	}
}
