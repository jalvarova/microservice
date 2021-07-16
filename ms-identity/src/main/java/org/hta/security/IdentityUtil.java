package org.hta.security;

public final class IdentityUtil {

    public static final String pattern = "*";
    public static final String patternPath = "/**";

    public final static String[] arrayHeaders = new String[]{
            "Access-Control-Allow-Origin", "Origin",
            "Content-Type", "Accept", "responseType", "Authorization"
    };

    public static final String[] permitPath = new String[]{
            "/authentication",
            "/ms-identity/health",
            "/ms-identity/info",
            "/ms-identity/metrics",
            "/prometheus",
            "/actuator/**"};

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

}
