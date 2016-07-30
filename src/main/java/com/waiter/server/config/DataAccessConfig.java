package com.waiter.server.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * @author shahenpoghosyan
 */
@Configuration
@EnableJpaRepositories("com.waiter.server.persistence.*")
@EnableTransactionManagement
@DependsOn("appProperties")
public class DataAccessConfig implements TransactionManagementConfigurer {

    @Autowired
    private Properties appProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JpaVendorAdapter vendorAdapter;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public DataSource createJDBCDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl(appProperties.getProperty("db.url"));
        dataSource.setUser(appProperties.getProperty("db.username"));
        dataSource.setPassword(appProperties.getProperty("db.password"));
        dataSource.setAcquireIncrement(Integer.parseInt(appProperties.getProperty("database.c3p0.acquire_increment")));
        dataSource.setInitialPoolSize(Integer.parseInt(appProperties.getProperty("database.c3p0.initialSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(appProperties.getProperty("database.c3p0.maxActive")));
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
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", appProperties.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("javax.persistence.lock.timeout", appProperties.getProperty("javax.persistence.lock.timeout"));
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.id.new_generator_mappings", "false");
        return properties;
    }

    @Bean
    public JpaVendorAdapter vendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(appProperties.getProperty("database.hibernate.show_sql")));
        hibernateJpaVendorAdapter.setGenerateDdl(Boolean.valueOf(appProperties.getProperty("database.hibernate.generateDdl")));
        hibernateJpaVendorAdapter.setDatabasePlatform(appProperties.getProperty("database.hibernate.dialect"));
        return hibernateJpaVendorAdapter;
    }

}
