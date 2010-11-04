package com.javaeye.liulu.test;

import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.ibatis.common.resources.Resources;
import com.javaeye.liulu.domain.User;

public class UserTest extends BaseSqlMapTest {

	protected IDataSet getDataSet() throws Exception {
		Reader reader = Resources.getResourceAsReader("com/javaeye/liulu/test/user_seed.xml");
		return new FlatXmlDataSet(reader);
	}
	
	public void testGetByPK() throws Exception {
		User user = new User();
		user.setId(1);
		user = (User) sqlMap.queryForObject("User.getByPK", user);
		assertNotNull(user);
		assertEquals(user.getId(), 1);
		assertEquals(user.getName(), "liulu");
		assertEquals(user.getDate().getDay(), 1);
	}
	
	public void testGetUser() throws Exception {
		List users = null;
		HashMap params = new HashMap();
		params.put("name", "%liulu%");
		users = (List) sqlMap.queryForList("User.getUser", params);
		assertEquals(users.size(),3);
	}
	
	public void testInsertUser() throws Exception {
		User user = new User();
		user.setId(4);
		user.setName("insert1");
		user.setDate(new Date());
		sqlMap.insert("User.insertUserTest", user);
		
		User user2 = new User();
		user2.setId(4);
		user2 = (User) sqlMap.queryForObject("User.getById", "4");
		assertEquals(user.getId(),user2.getId());
		assertEquals(user.getName(),user2.getName());
		
	}
	
	public void testUpdateUser() throws Exception {
		User user = (User)sqlMap.queryForObject("User.getById", "1");
		user.setName("liulu7");
		sqlMap.update("User.updateUser", user);
		User user2 = (User)sqlMap.queryForObject("User.getById", "1");
		assertEquals(user2.getName(),"liulu7");
	}
	
	public void testDeleteUser() throws Exception {
		int num = sqlMap.delete("User.deleteUser", "1");
		assertEquals(num,1);
	}
	
	public void testGetMaxId() throws Exception {
		int i =  (Integer)sqlMap.queryForObject("User.getMaxId", null);
		assertEquals(3,i);
	}
	
}