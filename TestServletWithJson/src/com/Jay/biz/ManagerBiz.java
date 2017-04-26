package com.Jay.biz;

import java.util.List;

import com.Jay.dao.ManagerDao;
import com.Jay.entity.Manager;
import com.Jay.mobileClient.GetDataFromClient;

public class ManagerBiz {
	private ManagerDao managerDao;
	
	public ManagerBiz(){
		managerDao = new ManagerDao();
	}
	
	public List getManagerInforFromDB(){
		return managerDao.getManagerInforFromDB();
	}
	
	public boolean postManagerToDB(Manager manager){
		return managerDao.postManagerToDB(manager);
	}
	
	public boolean addManagerToDB(Manager manager){
		return managerDao.addManagerToDB(manager);
	}
}
