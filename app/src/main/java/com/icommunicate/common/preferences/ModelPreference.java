package com.icommunicate.common.preferences;


import com.bluelinelabs.logansquare.LoganSquare;

public class ModelPreference<T> extends BaseModelPreference<T> {

    public ModelPreference(Class<T> clazz) {
        super(clazz);
    }

    public ModelPreference(Class<T> clazz, String key) {
        super(clazz, key);
    }

    @Override
    public String serialize(T t) throws Exception {
        return LoganSquare.serialize(t);
    }

    @Override
    public T parse(String jsonString) throws Exception {
        return LoganSquare.parse(jsonString, clazz);
    }

}