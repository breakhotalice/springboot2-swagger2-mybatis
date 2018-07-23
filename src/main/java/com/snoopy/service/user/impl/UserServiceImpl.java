package com.snoopy.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.snoopy.dao.UserDao;
import com.snoopy.model.UserDomain;
import com.snoopy.result.DataResult;
import com.snoopy.service.user.UserService;

/**
 * 
 * ClassName: UserServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2018年7月17日 下午4:04:41 <br/>
 * 
 * @author LiHaiqing
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;// 这里会报错，但是并不会影响

    @Override
    public int addUser(UserDomain user) {
        return userDao.insert(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public DataResult<UserDomain> findAllUser(int pageNum, int pageSize) {
        DataResult<UserDomain> result = new DataResult<>(true, "100", "成功", -1, null);
        // 将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.selectUsers();
        result.setData(userDomains);
        result.setCount(userDomains.size());
        return result;
    }

    @Override
    public void insertMybatisBatch() {
        List<UserDomain> userDomains = new ArrayList<>(15);
        for (int i = 0; i < 10; i++) {
            UserDomain userDomain = new UserDomain();
            userDomain.setUserName("username-" + i);
            userDomain.setPassword("123-" + i);
            userDomain.setPhone("18820276003-" + i);
            userDomains.add(userDomain);
        }
        userDao.insertMybatisBatch(userDomains);
        for (UserDomain userDomain : userDomains) {
            System.out.println(userDomain.getUserId() + "-------------------" + userDomain.getUserName());
        }
    }
}
