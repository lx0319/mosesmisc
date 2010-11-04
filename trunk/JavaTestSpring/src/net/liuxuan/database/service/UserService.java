package net.liuxuan.database.service;

import java.util.List;

import net.liuxuan.database.mapbean.User;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

public interface UserService {

    public User getUser(final String name);

    public void updateUser(final User user);

    public List selectUser();

    public void insertUser(final User user);
    
    public void tryTrans();

}



   
