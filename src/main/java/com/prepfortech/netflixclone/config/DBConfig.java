package com.prepfortech.netflixclone.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;



@Configuration
public class DBConfig {

    @Value("${jdbcUrl}") //this value jdbcurl is to link this with Application properties specified with DATABASE
    private String jdbcUrl;

    @Value("${dbUsername}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean(destroyMethod = "close") //this destroy method is to END the excess resources consumption after closing the SERVER
    @Primary
    DataSource getDataSource() { //creating datasource is the way to link  it with database
        BasicDataSource dataSource = new BasicDataSource();//created once and later only AUTOWIRED for different uses
        dataSource.setUrl(jdbcUrl); //here we used the above "value" links from the application properties.
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}


