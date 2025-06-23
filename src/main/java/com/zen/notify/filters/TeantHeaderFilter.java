package com.zen.notify.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TeantHeaderFilter  extends OncePerRequestFilter {

 @Override
 protected void doFilterInternal(HttpServletRequest request,
         HttpServletResponse response,
         FilterChain filterChain)
throws ServletException, IOException {
  HttpServletRequest httpRequest = (HttpServletRequest) request;
  System.out.println("Request URL: "+ httpRequest.getRequestURI());
  String tenantId = httpRequest.getHeader("X-Tenant-Id");

  TenantContext.set(TenantContext.builder().tenantId(tenantId).build());
  filterChain.doFilter(request, response); // Continue the filter chain
  System.out.println("Request URL: "+ TenantContext.get().getTenantId());
  TenantContext.clear();
 }
}
