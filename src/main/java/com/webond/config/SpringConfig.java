package com.webond.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {
        "com.service.model",
        "com.servicetype.model",
        "com.serviceslot.model"
})
@EnableTransactionManagement
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String jdbcUrl = System.getenv().getOrDefault(
                "JDBC_URL",
                "jdbc:mysql://localhost:3306/webond_project?serverTimezone=Asia/Taipei&characterEncoding=UTF-8"
        );

        String jdbcUsername = System.getenv().getOrDefault("JDBC_USERNAME", "root");
        String jdbcPassword = System.getenv().getOrDefault("JDBC_PASSWORD", "123456");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        factoryBean.setDataSource(dataSource);

        //→ 給 Hibernate 掃 @Entity
        factoryBean.setPackagesToScan(
                "com.service.model",
                "com.servicetype.model",
                "com.serviceslot.model"
                
        );

        Properties props = new Properties();
        //只檢查 Entity 和資料表是否對得上，不會幫你新增或修改資料表
        props.setProperty("hibernate.hbm2ddl.auto", "validate");
        props.setProperty("hibernate.show_sql", "true");
        //讓 SQL 排版比較好看
        props.setProperty("hibernate.format_sql", "true");
        // 明確告訴 Hibernate 你用 MySQL
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        factoryBean.setHibernateProperties(props);

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}