package com.snoopy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.snoopy.util.log.LogUtils;

/**
 * ClassName: Springboot2MybatisDemoApplicationTests <br/>
 * Function: log4j2测试. <br/>
 * date: 2018年7月23日 上午10:08:02 <br/>
 * 
 * @author LiHaiqing
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2MybatisDemoApplicationTests {

    @Test
    public void contextLoads() {

        Logger log = LogUtils.getExceptionLogger();
        Logger log1 = LogUtils.getBussinessLogger();
        Logger log2 = LogUtils.getDBLogger();
        log.error("getExceptionLogger===日志测试");
        log1.info("getBussinessLogger===日志测试");
        log2.debug("getDBLogger===日志测试");

    }

}
