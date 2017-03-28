package net.qiujuer.widget.airpanel;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

class AirAttribute {
    int panelMinHeight;
    int panelMaxHeight;

    static AirAttribute obtain(final View view, final AttributeSet attrs,
                               final int defStyleAttr, final int defStyleRes,
                               int[] attrValues, int attrMinIndex, int attrMaxIndex) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, attrValues, defStyleAttr, defStyleRes);
        Resources resources = view.getResources();
        int minHeight = a.getDimensionPixelOffset(attrMinIndex,
                resources.getDimensionPixelOffset(R.dimen.airPanelMinHeight));
        int maxHeight = a.getDimensionPixelOffset(attrMaxIndex,
                resources.getDimensionPixelOffset(R.dimen.airPanelMaxHeight));
        a.recycle();
        AirAttribute attribute = new AirAttribute();
        attribute.panelMinHeight = minHeight;
        attribute.panelMaxHeight = maxHeight;
        return attribute;
    }
}
