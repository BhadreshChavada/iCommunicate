package com.icommunicate.common.preferences;

import com.bluelinelabs.logansquare.LoganSquare;

import java.util.List;

/**
 * Created by Ivan on 3/15/2016.
 */
public class ListPreference<T> extends BaseListPreference<T> {

    public ListPreference(Class<T> clazz) {
        super(clazz);
    }

    public ListPreference(Class<T> clazz, String key) {
        super(clazz, key);
    }

    @Override
    public String serialize(List<T> list) throws Exception {
        return LoganSquare.serialize(list, clazz);
    }

    @Override
    public List<T> parse(String jsonString) throws Exception {
        return LoganSquare.parseList(jsonString, clazz);
    }
}
