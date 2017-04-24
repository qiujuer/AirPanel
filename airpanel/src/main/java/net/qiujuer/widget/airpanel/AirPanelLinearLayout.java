package net.qiujuer.widget.airpanel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

public class AirPanelLinearLayout extends LinearLayout implements Contract.Panel {
    private Contract.Helper mDelegate;

    public AirPanelLinearLayout(Context context) {
        this(context, null);
    }

    public AirPanelLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirPanelLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AirPanelLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        AirAttribute attribute = AirAttribute.obtain(this, attrs, defStyleAttr, defStyleRes,
                R.styleable.AirPanelLinearLayout,
                R.styleable.AirPanelLinearLayout_airPanelMinHeight,
                R.styleable.AirPanelLinearLayout_airPanelMaxHeight);

        mDelegate = new Helper(this, attribute);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode())
            mDelegate.setup((Activity) getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = mDelegate.calculateHeightMeasureSpec(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void openPanel() {
        mDelegate.openPanel();
    }

    @Override
    public void closePanel() {
        mDelegate.closePanel();
    }

    @Override
    public boolean isOpen() {
        return mDelegate.isOpen();
    }

    @Override
    public void setPanelListener(AirPanel.Listener listener) {
        mDelegate.setPanelListener(listener);
    }

    @Override
    public void setOnStateChangedListener(OnStateChangedListener listener) {
        mDelegate.setOnStateChangedListener(listener);
    }

    @Override
    public void adjustPanelHeight(int heightMeasureSpec) {
        mDelegate.adjustPanelHeight(heightMeasureSpec);
    }
}
