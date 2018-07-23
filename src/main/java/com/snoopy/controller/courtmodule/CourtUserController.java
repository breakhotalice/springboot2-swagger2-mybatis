package com.snoopy.controller.courtmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snoopy.model.UserDomain;
import com.snoopy.result.DataResult;
import com.snoopy.service.user.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * ClassName: UserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2018年7月17日 下午4:04:33 <br/>
 * 
 * @author LiHaiqing
 */
@Controller
@RequestMapping(value = "/courtUser")
@Api(value = "user")
public class CourtUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", response = UserDomain.class)
    public @ResponseBody int addUser(UserDomain user) {
        return userService.addUser(user);
    }

    @GetMapping("/all")
    @ApiOperation(value = "分页查询所有用户", response = UserDomain.class)
    public @ResponseBody DataResult<UserDomain> findAllUser(
        @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
        @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        DataResult<UserDomain> userDomains = userService.findAllUser(pageNum, pageSize);
        return userDomains;
    }

    @GetMapping("/mybatis")
    @ApiOperation(value = "测试mybatis批量插入", response = UserDomain.class)
    public @ResponseBody Object mybatisBatchInsertTest() {
        userService.insertMybatisBatch();
        return null;
    }

}
