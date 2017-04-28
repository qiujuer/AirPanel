package net.qiujuer.widget.airpanel;

import android.app.Activity;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */

interface Contract extends AirPanel.Boss {
    interface Panel extends AirPanel.Boss {
        void adjustPanelHeight(int heightMeasureSpec);
    }

    interface Helper extends Panel, AirPanel.PanelListener {
        int calculateHeightMeasureSpec(int heightMeasureSpec);

        void setup(Activity activity);
    }
}
