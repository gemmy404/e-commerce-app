package com.app.furniture.util;

import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

public class UpdateUtil {

    public static <T>T updateValues(T source, T target) {
        Arrays.stream(source.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> ReflectionUtils.getField(field, source) != null)
                .forEach(field -> {
                    Object value = ReflectionUtils.getField(field, source);
                    ReflectionUtils.setField(field, target, value);
                });
        return target;
    }

}
