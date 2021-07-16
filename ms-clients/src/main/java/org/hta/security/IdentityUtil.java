package org.hta.security;

public final class IdentityUtil {

    public static final String pattern = "*";
    public static final String patternPath = "/**";

    public final static String[] arrayHeaders = new String[]{
            "Access-Control-Allow-Origin", "Origin",
            "Content-Type", "Accept", "responseType", "Authorization"
    };

    public static final String[] permitPath = new String[]{
            "/ms-clients/health",
            "/ms-clients/info",
            "/ms-clients/metrics",
            "/ms-clients/**",
            "/prometheus",
            "/actuator/**"};

}
