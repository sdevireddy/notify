package com.zen.notify.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zen.notify.filters.CurrentTenantIdentifierResolverImpl;
import com.zen.notify.filters.MultiTenantConnectionProviderImpl;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	 private  JpaProperties jpaProperties;

    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Autowired
    private CurrentTenantIdentifierResolverImpl tenantIdentifierResolver;
    
    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
    
    public HibernateConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, MultiTenantConnectionProvider<String> multiTenantConnectionProviderImpl, CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolverImpl
    ) {

        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.zen.notify.entity*");
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
