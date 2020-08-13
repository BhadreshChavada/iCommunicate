package com.icommunicate.common.preferences;

import com.icommunicate.apiCall.responseModels.LoginData;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberData;

import info.metadude.android.typedpreferences.BooleanPreference;
import info.metadude.android.typedpreferences.StringPreference;

public class PreferenceUtil extends BasePreferenceUtil {

    public static BooleanPreference isUserLoggedIn() {
        return booleanPreference("is_user_logged_in", false);
    }

    public static void deleteAllSharePrefs() {
        preferences.edit().clear().commit();
    }

    public static StringPreference loginPassword() {
        return stringPreference("loginpassword", null);
    }

    public static ModelPreference<LoginData> loginUserData() {
        return  new ModelPreference<>(LoginData.class, "loginuserData");
    }

    public static StringPreference defultDailNumber() {
        return stringPreference("defulatDailNumber", null);
    }

    public static StringPreference byDefultDailNumber() {
        return stringPreference("byDefultDailNumber", null);
    }
}