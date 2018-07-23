package com.snoopy.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * ClassName: DruidConfig <br/>
 * Function: druid需要的配置类. <br/>
 * date: 2018年7月16日 下午4:43:58 <br/>
 * 一种方式：采用注解的方式获取 @value("${变量的key值}") 获取application配置文件中的变量。</br>
 * 另一种方式：凡是被Spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment 可以在工程启动时， 获取到系统环境变量和application配置文件中的变量。
 * 
 * @author LiHaiqing
 */
@Configuration
public class DruidConfig {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    @Value("${spring.datasource.name}")
    private String              name;

    @Value("${spring.datasource.type}")
    private String              type;

    @Value("${spring.datasource.druid.url}")
    private String              url;

    @Value("${spring.datasource.druid.username}")
    private String              username;

    @Value("${spring.datasource.druid.password}")
    private String              password;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String              driverClass;

    @Value("${spring.datasource.druid.initialSize}")
    private int                 initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int                 minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int                 maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private long                maxWait;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private long                timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private long                minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String              validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean             testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean             testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean             testOnReturn;

    @Value("${spring.datasource.druid.poolPreparedStatements}")
    private boolean             poolPreparedStatements;

    @Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int                 maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.druid.filters}")
    private String              filters;

    @Value("${spring.datasource.druid.connectionProperties}")
    private String              connectionProperties;

    @Bean
    @Autowired
    public DataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setFilters(filters);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setConnectionProperties(connectionProperties);
        return druidDataSource;
    }

    // 增加sqlSessionFactory
    @Primary
    @Bean(name = "sqlSessionFactory")
    @Autowired
    public SqlSessionFactory sqlSessionFactoryBean() throws SQLException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(druidDataSource());
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            // sessionFactoryBean.setConfigLocation(resolver.getResource("classpath:/conf/mybatis-config.xml"));
            sessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*Mapper*.xml"));
            sessionFactoryBean.setTypeAliasesPackage("com.snoopy.model");
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Autowired
    public PlatformTransactionManager annotationDrivenTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(druidDataSource());
    }

    @Primary
    @Bean
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate() throws SQLException {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }

    /**
     * 注册DruidServlet
     * 
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServletRegistrationBean() {
        logger.info("init Druid Servlet Configuration ");
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
        servletRegistrationBean.addInitParameter("allow", "192.168.1.20,127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.1.20");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "snoopy");
        // 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;

    }

    /**
     * 注册DruidFilter拦截
     * 
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> duridFilterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        // 设置忽略请求
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
