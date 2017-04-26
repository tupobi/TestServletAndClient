package com.Jay.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Jay.entity.Manager;
import com.mysql.jdbc.PreparedStatement;

public class ManagerDao {
	private Connection connection;
	private ConnectionManager connectionManager;
	
	public ManagerDao(){
		connectionManager = new ConnectionManager();
	}
	
	public List getManagerInforFromDB(){
		List<Manager> managers = new ArrayList();
		try{
			connection = connectionManager.getConnection_jdbc();
			String sql = "SELECT * FROM manager";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				managers.add(new Manager(resultSet.getString(1), resultSet.getString(2)));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}
		return managers;
	}
	
	public boolean postManagerToDB(Manager manager){
		connection = connectionManager.getConnection_jdbc();
		String sql = "SELECT * FROM manager WHERE uname='"+manager.getUname()+"' AND upwd='"+manager.getUpwd()+"'";
		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
//			ps.setString(1, manager.getUname());
//			ps.setString(2, manager.getUpwd());
			ResultSet resultSet = ps.executeQuery(sql);
			if(resultSet.next()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}
		return false;
	}
	
	public boolean addManagerToDB(Manager manager){
		connection = connectionManager.getConnection_jdbc();
		String sql = "INSERT INTO manager VALUES (?, ?)";
		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setString(1, manager.getUname());
			ps.setString(2, manager.getUpwd());
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			connectionManager.closeConnection();
		}
		return false;
	}
}
