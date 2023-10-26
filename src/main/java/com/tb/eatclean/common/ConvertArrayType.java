package com.tb.eatclean.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConvertArrayType {
    public static List<String> convertToList(String data) {
        List<String> list = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        list = gson.fromJson(new Gson().toJson(list), type);

        return list;
    }

    public static String convertToString(List<String> data) {


        return new Gson().toJson(data);
    }
}
