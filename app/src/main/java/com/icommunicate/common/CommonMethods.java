package com.icommunicate.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CommonMethods {


    private static boolean validMobile;


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void ErrorDialog(final Context c, int error_msg) {
        switch (error_msg) {
            case 500:
                new SweetAlertDialog(c, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("INTERNAL_SERVER_ERROR !").show();
                break;
            case 401:
                try {
                 } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 400:
                new SweetAlertDialog(c, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("BAD_REQUEST !").show();
                break;
            case 404:
                new SweetAlertDialog(c, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("NOT_FOUND !").show();
                break;
            default:
                break;
        }


    }


    public static void missingField(final Context c, String error_msg) {
        new SweetAlertDialog(c, SweetAlertDialog.WARNING_TYPE).setTitleText("Your answer is missing").setContentText(error_msg).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getUtcTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        return utcTime;

    }

    public static String getValue(String text) {
        return (text != null && !text.isEmpty() && !text.equalsIgnoreCase("null")) ? text : "";
    }

    public static Integer getValue(Integer text) {
        return (text != null && text != 0) ? text : 0;
    }

    public static Float getValue(Float text) {
        return (text != null && text != 0) ? text : Float.valueOf(0);
    }

    public static int dpToPx(Context cntx, int dp) {
        DisplayMetrics displayMetrics = cntx.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }





    public static String getCurrentTimezoneTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getDefault());
        String utcTime = sdf.format(new Date());
        return utcTime;

    }

    public static boolean isNumeric(String str) {
        return str.matches("^[0-9]{10}$");
    }

    public static boolean isValidMobile(String number) {
        return number.length() == 10;
    }



    public static ProgressDialog showProgressBar(Context mContext) {

         ProgressDialog of = ProgressDialog.show(mContext, "", "Please wait..");
        of.setCanceledOnTouchOutside(false);
        return of;
    }

    public static void closeDialog(ProgressDialog dialog) {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String[] getDefaultValue() {
        String[] strings = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        return strings;
    }

}
