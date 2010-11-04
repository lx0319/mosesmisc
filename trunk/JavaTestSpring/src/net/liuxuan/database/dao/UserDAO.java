package net.liuxuan.database.dao;

import java.util.List;

import net.liuxuan.database.mapbean.User;

public interface UserDAO {

    public User getUser(String name);

    public void updateUser(User user);

    public List selectUser();

    public void insertUser(User user);
    
    public Integer getCount();
    
    public Integer getMaxId();
}
