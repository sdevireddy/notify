package com.zen.notify.filters;


import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    private static final String DEFAULT_TENANT = "tenant"; // fallback if needed

    @Override
    public String resolveCurrentTenantIdentifier() {
        TenantContext ctx = TenantContext.get();
        String tenantId = (ctx != null && ctx.getTenantId() != null) ? ctx.getTenantId() : DEFAULT_TENANT;
        return tenantId;
    }
    
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }


    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
