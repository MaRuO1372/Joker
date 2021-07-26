package com.toldrentr.crazyjoker.foo;

import com.google.gson.JsonObject;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class MyJsonObject {
    private JsonObject jo;

    public MyJsonObject(JsonObject jo){
        this.jo = jo;
    }

    public boolean getBool(String key, boolean def){
        try {
            return jo.get(key).getAsBoolean();
        }catch (Exception ignored){
            return def;
        }
    }

    public String getStr(String key, String def){
        try {
            return jo.get(key).getAsString();
        }catch (Exception ignored){
            return def;
        }
    }

    public String getStr(String key){
        return getStr(key, "");
    }

    public boolean has(String key){
        return jo.has(key);
    }
}