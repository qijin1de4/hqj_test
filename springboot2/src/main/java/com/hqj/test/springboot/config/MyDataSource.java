package com.hqj.test.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * 手动配置druid数据源
 */
@Deprecated
//@Configuration
public class MyDataSource {

    @ConfigurationProperties("spring.datasource")
    @Bean
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        //添加SQL监控
        //dataSource.addFilters("stat");
        //添加防火情监控
        //dataSource.addFilters("wall");
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidMonitorServlet() {
        StatViewServlet servlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(servlet, "/druid/*");
        return servletServletRegistrationBean;
    }

    /**
     * 添加durid对web应用的监控
     * 某个url的请求时间，SQL执行次数等
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> druidFilter() {
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> filters = new FilterRegistrationBean<>(webStatFilter);
        filters.setUrlPatterns(Arrays.asList("/*"));
        filters.addInitParameter("exclusion", "*.js,*.jpg,*.png,*.css,*.ico,*/durid/*");
        return filters;
    }
}
