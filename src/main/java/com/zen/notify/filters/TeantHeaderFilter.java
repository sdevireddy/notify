package com.zen.notify.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TeantHeaderFilter extends OncePerRequestFilter {
	
	 private static final Logger log = LoggerFactory.getLogger(TeantHeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	


        String requestURI = request.getRequestURI();
        String tenantId = request.getHeader("X-Tenant-Id");

        log.debug("Incoming request: {}", requestURI);
        log.info("X-Tenant-Id header received: {}", tenantId);

        TenantContext.set(TenantContext.builder().tenantId(tenantId).build());

        try {
            filterChain.doFilter(request, response); // Proceed with filter chain
        } finally {
            log.debug("Clearing tenant context for request: {}, tenantId: {}", requestURI);
            TenantContext.clear();
        }
    }
}
