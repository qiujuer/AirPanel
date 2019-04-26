package net.qiujuer.widget.airpanel;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

final class Helper implements Contract.Helper {
    private AirPanel.Listener mListener;
    private AirPanel.OnStateChangedListener mStateChangedListener;
    private final View mView;
    private View mRootView;
    private AirAttribute mAttribute;
    private int mPanelHeight;

    private final AtomicBoolean mShowPanelIntention = new AtomicBoolean(false);
    private final Rect mLastFrame = new Rect();
    private int mDisplayHeight;

    Helper(View view, AirAttribute attribute) {
        this.mView = view;
        this.mView.setVisibility(View.VISIBLE);
        this.mAttribute = attribute;
        this.mPanelHeight = Util.getDefaultPanelHeight(mView.getContext(), mAttribute);
    }

    @Override
    public void openPanel() {
        if (isOpenSoftKeyboard()) {
            mPanelHeight = 0;
            mShowPanelIntention.set(true);
            requestHideSoftKeyboard();
        }
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    public void closePanel() {
        mPanelHeight = 0;
        //mView.setVisibility(View.GONE);
    }

    @Override
    public boolean isOpen() {
        return mView.getVisibility() != View.GONE && mPanelHeight != 0;
    }

    @Override
    public void setPanelListener(AirPanel.Listener listener) {
        mListener = listener;
    }

    @Override
    public void setOnStateChangedListener(OnStateChangedListener listener) {
        mStateChangedListener = listener;
    }

    @Override
    public void adjustPanelHeight(int height) {
        height = Math.min(height, mAttribute.panelMaxHeight);
        height = Math.max(height, mAttribute.panelMinHeight);
        if (height != mPanelHeight) {
            mPanelHeight = height;
            Util.updateLocalPanelHeight(mView.getContext(), height);
        }
    }

    @Override
    public void requestHideSoftKeyboard() {
        if (mListener != null)
            mListener.requestHideSoftKeyboard();
    }

    @Override
    public int calculateHeightMeasureSpec(int heightMeasureSpec) {
        // Only update frame values
        updateFrameSize();

        // Check self open state
        if (isOpen()) {
            // Do it
            int specMode = View.MeasureSpec.getMode(heightMeasureSpec);
            int specSize = View.MeasureSpec.getSize(heightMeasureSpec);

            int newSpecMode = View.MeasureSpec.EXACTLY;
            int newSpecSize = mPanelHeight;
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(newSpecSize, newSpecMode);
            Util.log("calculateHeightMeasureSpec:oldMode:%s, oldSize:%s newMode:%s, newSize:%s",
                    specMode, specSize, newSpecMode, newSpecSize);
        }
        return heightMeasureSpec;
    }

    private void updateFrameSize() {
        if (mRootView != null) {
            Rect frame = new Rect();
            mRootView.getWindowVisibleDisplayFrame(frame);

            int bottomChangeSize = 0;
            if (mLastFrame.bottom > 0) {
                bottomChangeSize = frame.bottom - mLastFrame.bottom;
            }

            mLastFrame.set(frame);
            Util.log("updateFrameSize frame:%s bottomChangeSize:%s", mLastFrame, bottomChangeSize);

            // In the end, we should check the soft keyboard next action
            checkSoftKeyboardAction(bottomChangeSize);
        }
    }

    private void checkSoftKeyboardAction(int bottomChangeSize) {
        if (bottomChangeSize > 0 && !isOpenSoftKeyboard()) {
            // If want to show panel, we need call it
            if (mShowPanelIntention.getAndSet(false)) {
                // Adjust SubPanelHeight
                adjustPanelHeight(bottomChangeSize);
                // try open panel
                openPanel();
            }
        } else if (bottomChangeSize < 0) {
            closePanel();
        }
    }

    private boolean isOpenSoftKeyboard() {
        boolean isOpenSoftKeyboard = mLastFrame.bottom != 0 && mLastFrame.bottom != mDisplayHeight;
        Util.log("isOpenSoftKeyboard:%s", isOpenSoftKeyboard);
        return isOpenSoftKeyboard;
    }

    @Override
    public void setup(Activity activity) {
        mRootView = activity.getWindow().getDecorView();
        // Get DisplayHeight
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else {
            display.getSize(point);
        }
        mDisplayHeight = point.y;

        Util.log("setup mDisplayHeight:%s point:%s", mDisplayHeight, point.toString());
    }

}
