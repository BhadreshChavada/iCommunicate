package com.icommunicate.common;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.icommunicate.R;

public class IntentUtils {

    public static void redirectTo(Context mContext, Class<?> cls) {
        Intent i = new Intent(mContext, cls);
        mContext.startActivity(i);
        ((Activity) mContext).overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
    public static void redirectToWithBundle(Context mContext, Class<?> cls, Bundle bundle) {
        Intent i = new Intent(mContext, cls);
        i.putExtras(bundle);
        mContext.startActivity(i);
        ((Activity) mContext).overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
    public static void redirectWithFinishTo(Context mContext, Class<?> cls) {
        Intent i = new Intent(mContext, cls);
        mContext.startActivity(i);
        ((Activity) mContext).finish();
        ((Activity) mContext).overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public static void finishActivity(Context mContext) {
        ((Activity) mContext).finish();
        ((Activity) mContext).overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public static void launchUrl(Context mContext, String url) {
        Uri uri = Uri.parse(url);
        if (uri.getScheme() == null || uri.getScheme().isEmpty()) {
            uri = Uri.parse("http://" + url);
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        if (browserIntent.resolveActivity(mContext.getPackageManager()) != null) {
            ((Activity) mContext).startActivity(browserIntent);
        }
    }
}
