package net.qiujuer.widget.airpanel.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

public class MainActivity extends AppCompatActivity {
    private EditText mEditContent;
    private TextView mTextTips;
    private AirPanel.Boss mPanelBoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextTips = findViewById(R.id.txt_tips);
        mEditContent = findViewById(R.id.edit_content);

        // Open log
        Util.DEBUG = true;
        mPanelBoss = findViewById(R.id.lay_container);
        // You must do this, to call Util.hideKeyboard(your input edit text view);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(mEditContent);
            }
        });
        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
            @Override
            public void onPanelStateChanged(boolean isOpen) {
                Log.e("TAG", "onPanelStateChanged:" + isOpen);
                showTips("PanelState", isOpen);
            }

            @Override
            public void onSoftKeyboardStateChanged(boolean isOpen) {
                Log.e("TAG", "onSoftKeyboardStateChanged:" + isOpen);
                showTips("KeyboardState", isOpen);
            }
        });


        findViewById(R.id.btn_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFaceClick();
            }
        });

        showTips("init", false);
    }

    private void onFaceClick() {
        if (mPanelBoss.isOpen()) {
            Util.showKeyboard(mEditContent);
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

    @SuppressLint("DefaultLocale")
    private void showTips(String from, boolean isOpen) {
        Point realScreenSize = getRealScreenSize(this);
        Point appUsableSize = getAppUsableScreenSize(this);
        Point diffSize = getNavigationBarSize(this);

        View decorView = getWindow().getDecorView();
        Rect frame = new Rect();
        decorView.getWindowVisibleDisplayFrame(frame);

        mTextTips.setText(String.format("%s:[%s]\n" +
                        "PanelStatus:[%s]\n" +
                        "ScreenSize:[%d, %d]\n" +
                        "UnableSize:[%d, %d]\n" +
                        "DiffSize:[%d, %d]\n" +
                        "Visible:[%s, %s]\n",
                from, isOpen,
                mPanelBoss.isOpen(),
                realScreenSize.x, realScreenSize.y,
                appUsableSize.x, appUsableSize.y,
                diffSize.x, diffSize.y,
                frame.left + "-" + frame.right, frame.top + "-" + frame.bottom));
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
                //noinspection JavaReflectionMemberAccess
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                //noinspection JavaReflectionMemberAccess
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return size;
    }
}
