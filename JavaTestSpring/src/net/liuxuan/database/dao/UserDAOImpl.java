package net.liuxuan.database.dao;

import java.util.HashMap;
import java.util.List;

import net.liuxuan.database.common.BaseSqlMapDao;
import net.liuxuan.database.mapbean.User;

import org.apache.log4j.Logger;

public class UserDAOImpl extends BaseSqlMapDao implements UserDAO {

    private static Logger log = Logger.getLogger(UserDAOImpl.class);

    public User getUser(String id) {
	
	
	
	return (User) this.getSqlMapClientTemplate().queryForObject("User.getById", id);
    }

    public void updateUser(User user) {
	smcTemplate.update("User.updateUser", user);
    }

    public List selectUser() {
	List<User> user = null;
	HashMap params = new HashMap();
	params.put("name", "");
	user = smcTemplate.queryForList("User.getUser", params);
	return user; 
    }

    public void insertUser(User user) {
	smcTemplate.insert("User.insertUser", user);
    }
    
    public Integer getCount() {
	Integer count = (Integer) smcTemplate.queryForObject("User.getCount", null);
	return count;
    }
    
    public Integer getMaxId() {
	Integer maxid = (Integer)smcTemplate.queryForObject("User.getMaxId", null);
	return maxid;
    }
    
}
