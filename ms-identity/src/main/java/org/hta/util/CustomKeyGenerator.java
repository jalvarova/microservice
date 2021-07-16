package org.hta.util;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.StringJoiner;

public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return keyGenerator(target, method, params);
    }

    public static String keyGenerator(Object target, Method method, Object... params) {
        StringJoiner join = new StringJoiner("_");
        join.add(target.getClass().getSimpleName());
        join.add(StringUtils.arrayToDelimitedString(params, "_"));
        return join.toString().toLowerCase();
    }
}
