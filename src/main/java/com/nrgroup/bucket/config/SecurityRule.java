package com.nrgroup.bucket.config;

public abstract class SecurityRule {
    public static final String IS_ADMIN = "hasAuthority('ADMIN')";
    public static final String IS_MANAGER = "hasAuthority('MANAGER')";
    public static final String IS_USER = "hasAuthority('USER')";
    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    public static final String IS_ANONYMOUS = "isAnonymous()";
    public static final String PERMIT_ALL = "permitAll()";
}
