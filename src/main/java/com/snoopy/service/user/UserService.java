package com.snoopy.service.user;

import com.snoopy.model.UserDomain;
import com.snoopy.result.DataResult;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    int addUser(UserDomain user);

    DataResult<UserDomain> findAllUser(int pageNum, int pageSize);

    void insertMybatisBatch();
}
