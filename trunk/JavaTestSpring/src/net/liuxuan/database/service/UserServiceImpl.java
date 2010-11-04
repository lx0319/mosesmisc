//UserServiceImpl.java 用户服务实现类

package net.liuxuan.database.service;

import java.util.Date;
import java.util.List;

import net.liuxuan.database.dao.UserDAO;
import net.liuxuan.database.mapbean.User;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserDAO getUserDAO() {
	return userDAO;
    }

    public void setUserDAO(UserDAO UserDAO) {
	this.userDAO = UserDAO;
    }

    public List selectUser() {
	return userDAO.selectUser();
    }

    public User getUser(final String name) {
	return (User) userDAO.getUser(name);
    }

    public void insertUser(final User user) {
	userDAO.insertUser(user);
    }

    public void updateUser(final User user) {
	userDAO.updateUser(user);
    }

    public void tryTrans() {
	System.out.println("total:" + userDAO.getCount());
	int maxid = userDAO.getMaxId().intValue();
	System.out.println("maxid:" + maxid);
	maxid++;
	User u = new User();
	u.setId(maxid);
	u.setName("Test" + maxid);
	u.setDate(new Date());
	userDAO.insertUser(u);
	System.out.println("total:" + userDAO.getCount());
	maxid = userDAO.getMaxId().intValue();
	System.out.println("maxid:" + maxid);
	    //System.out.println(1 / 0);
//	     throw new RuntimeException();

//	System.out.println("after exception");
//	System.out.println("total:" + userDAO.getCount());
//	maxid = userDAO.getMaxId().intValue();
//	System.out.println("maxid:" + maxid);

    }

}