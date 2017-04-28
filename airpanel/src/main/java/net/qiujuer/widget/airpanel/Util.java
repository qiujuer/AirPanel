package net.qiujuer.widget.airpanel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

@SuppressWarnings("WeakerAccess")
public final class Util {
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void log(String format, Object... args) {
        if (DEBUG)
            Log.d("AirPanel", String.format(format, args));
    }

    public static void showKeyboard(final View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    public static void hideKeyboard(final View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    static int getDefaultPanelHeight(Context context, AirAttribute attribute) {
        int defaultHeight = (int) ((attribute.panelMaxHeight + attribute.panelMinHeight) / 2.0f + 0.5f);
        defaultHeight = PanelSharedPreferences.get(context, defaultHeight);
        return defaultHeight;
    }

    static void updateLocalPanelHeight(Context context, int height) {
        PanelSharedPreferences.save(context, height);
    }

    private static class PanelSharedPreferences {
        private final static String FILE_NAME = "AirPanel.sp";
        private final static String KEY_PANEL_HEIGHT = "panelHeight";
        private volatile static SharedPreferences SP;

        public static boolean save(final Context context, final int keyboardHeight) {
            return sp(context).edit()
                    .putInt(KEY_PANEL_HEIGHT, keyboardHeight)
                    .commit();
        }

        private static SharedPreferences sp(final Context context) {
            if (SP == null) {
                synchronized (PanelSharedPreferences.class) {
                    if (SP == null) {
                        SP = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                    }
                }
            }
            return SP;
        }

        public static int get(final Context context, final int defaultHeight) {
            return sp(context).getInt(KEY_PANEL_HEIGHT, defaultHeight);
        }
    }
}
