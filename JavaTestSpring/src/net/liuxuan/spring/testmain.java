package net.liuxuan.spring;

import net.liuxuan.database.dao.UserDAO;
import net.liuxuan.database.mapbean.User;
import net.liuxuan.database.service.UserService;

import org.apache.log4j.Logger;


public class testmain {

    static Logger logger = Logger.getLogger(testmain.class);

    UserDAO userDao;
    UserService userService;
    
    public void testtest(){
	//User u = userDao.getUser("2");
	//System.out.println(u.getName());
//	User u = userService.getUser("1");
//	System.out.println(u.getName());
	userService.tryTrans();
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
	BasicModule.loadLog4j();
	BasicModule.loadSpringBeanFactory();
	
	testmain tm = (testmain) BasicModule.getBeanFactory().getBean("testmain");
	tm.testtest();
	//testjdbc.tryJDBC();
    }


    public UserDAO getUserDao() {
        return userDao;
    }


    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }


    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
