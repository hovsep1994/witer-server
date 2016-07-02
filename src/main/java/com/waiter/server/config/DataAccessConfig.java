package com.waiter.server.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author shahenpoghosyan
 */
@Configuration
@EnableJpaRepositories("com.waiter.server.persistence.*")
@EnableTransactionManagement
@DependsOn("appProperties")
public class DataAccessConfig {

    @Value("#{appProperties['db.url']}")
    private String dbUrl;

    @Value("#{appProperties['db.username']}")
    private String username;

    @Value("#{appProperties['db.password']}")
    private String password;

    @Value("#{appProperties['hibernate.hbm2ddl.auto']}")
    private String auto;

    @Value("#{appProperties['javax.persistence.lock.timeout']}")
    private String timeout;

    @Autowired
    private DataSource dataSource;

//    private String timeout;


    @Value("#{appProperties['javax.persistence.lock.timeout']}")

    @Bean
    @DependsOn(value = "appProperties")
    public DataSource createJDBCDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(dbUrl);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplateObject() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(
                "com.waiter.server.persistence.core.repository.*",
                "com.waiter.server.services.*");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        return em;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", auto);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("javax.persistence.lock.timeout", timeout);
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.id.new_generator_mappings", "false");
        return properties;
    }

}
