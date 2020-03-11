package com.lk.netty.netty.demo.study.util;

import com.google.gson.Gson;

public final class JsonUtil {

    private static final Gson GSON = new Gson();

    private JsonUtil() {
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz){
        return GSON.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object){
        return GSON.toJson(object);
    }

}
