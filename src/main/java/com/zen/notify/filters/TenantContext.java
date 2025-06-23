package com.zen.notify.filters;

public class TenantContext {

    private static final ThreadLocal<TenantContext> context = new ThreadLocal<>();

    private String tenantId;

    // Static context access methods
    public static TenantContext get() {
        return context.get();
    }

    public static void set(TenantContext context) {
        TenantContext.context.set(context);
    }

    public static void clear() {
        TenantContext.context.remove();
    }

    // Getter and Setter
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final TenantContext tenantContext;

        public Builder() {
            this.tenantContext = new TenantContext();
        }

        public Builder tenantId(String tenantId) {
            this.tenantContext.setTenantId(tenantId);
            return this;
        }

        public TenantContext build() {
            return this.tenantContext;
        }
    }

    @Override
    public String toString() {
        return "TenantContext{tenantId='" + tenantId + "'}";
    }

}