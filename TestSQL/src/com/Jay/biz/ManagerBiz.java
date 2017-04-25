package com.Jay.biz;

import com.Jay.dao.ManagerDao;
import com.Jay.entity.Manager;

public class ManagerBiz {
	private ManagerDao managerDao;
	
	public ManagerBiz(){
		managerDao = new ManagerDao();
	}
	
	public Manager login(String uname, String upwd){
		return managerDao.login(uname, upwd);
	}
}
