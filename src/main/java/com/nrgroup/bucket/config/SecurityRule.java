package com.nrgroup.bucket.config;

public abstract class SecurityRule {
    public static final String ROLE_ADMIN = "hasAuthority('ADMIN')";
    public static final String ROLE_MANAGER = "hasAuthority('MANAGER')";
    public static final String ROLE_USER = "hasAuthority('USER')";
    public static final String AUTHENTICATED = "isAuthenticated()";
    public static final String ANONYMOUS = "isAnonymous()";
    public static final String PERMIT_ALL = "permitAll()";
}
