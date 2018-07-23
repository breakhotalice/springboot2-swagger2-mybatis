package com.snoopy.dao;

import java.util.List;

import com.snoopy.model.UserDomain;

public interface UserDao {

    int insert(UserDomain record);

    List<UserDomain> selectUsers();

    void insertMybatisBatch(List<UserDomain> userDomains);
}