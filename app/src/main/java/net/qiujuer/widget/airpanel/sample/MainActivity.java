package net.qiujuer.widget.airpanel.sample;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

public class MainActivity extends AppCompatActivity {
    private EditText mContent;
    private AirPanel.Boss mPanelBoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContent = (EditText) findViewById(R.id.edit_content);

        // Open log
        Util.DEBUG = true;
        mPanelBoss = (AirPanel.Boss) findViewById(R.id.lay_container);
        // You must do this, to call Util.hideKeyboard(your input edit text view);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(mContent);
            }
        });
        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
            @Override
            public void onPanelStateChanged(boolean isOpen) {
                Log.e("TAG", "onPanelStateChanged:" + isOpen);
            }

            @Override
            public void onSoftKeyboardStateChanged(boolean isOpen) {
                Log.e("TAG", "onSoftKeyboardStateChanged:" + isOpen);
            }
        });


        findViewById(R.id.btn_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFaceClick();
            }
        });

        getNavigationBarSize(this);
    }

    private void onFaceClick() {
        if (mPanelBoss.isOpen()) {
            Util.showKeyboard(mContent);
        } else {
            mPanelBoss.openPanel();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPanelBoss.isOpen()) {
            mPanelBoss.closePanel();
            return;
        }
        super.onBackPressed();
    }


    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return size;
    }
}
